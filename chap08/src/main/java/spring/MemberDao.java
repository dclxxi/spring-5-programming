package spring;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MemberDao {
    
    private JdbcTemplate jdbcTemplate;
    
    public MemberDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
    public Member selectByEmail(String email) {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER where EMAIL = ?",
                new RowMapper<Member>() { // 임의 클래스 or 람다식으로 객체 전달 (인터페이스를 구현한 클래스 작성 가능)
                    @Override // ResultSet에서 데이터를 읽어와 Member 객체로 변환
                    public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Member member = new Member(
                                rs.getString("EMAIL"),
                                rs.getString("PASSWORD"),
                                rs.getString("NAME"),
                                rs.getTimestamp("REGDATE").toLocalDateTime());
                        member.setId(rs.getLong("ID"));
                        return member;
                    }
                }, email); // 인덱스 파라미터(?)에 들어갈 값
        
        return results.isEmpty() ? null : results.get(0); // 쿼리 실행 결과 존재 여부 확인
    }
    
    public void insert(Member member) {
        KeyHolder keyHolder = new GeneratedKeyHolder(); // GeneratedKeyHolder : 자동으로 생성된 키값 구할 수 있음
        jdbcTemplate.update(new PreparedStatementCreator() { // update : INSERT, UPDATE, DELETE 쿼리 (변경된 행 개수 리턴)
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement( // Connection을 이용해서 PreparedStatement 생성
                        "insert into MEMBER (EMAIL, PASSWORD, NAME, REGDATE) values (?, ?, ?, ?)",
                        new String[]{"ID"}); // 자동 생성되는 키 칼럼 목록 지정
                
                pstmt.setString(1, member.getEmail()); // 인덱스 파라미터 값 설정
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getName());
                pstmt.setTimestamp(4, Timestamp.valueOf(member.getRegisterDateTime()));
                
                return pstmt; // 생성한 PreparedStatement 객체 리턴
            }
        }, keyHolder);
        Number keyValue = keyHolder.getKey(); // KeyHolder에 보관된 키값 구함
        member.setId(keyValue.longValue()); // Number 타입을 long 타입으로 변환
    }
    
    public void update(Member member) {
        jdbcTemplate.update(
                "update MEMBER set NAME = ?, PASSWORD = ? where EMAIL = ?",
                member.getName(), member.getPassword(), member.getEmail()); // 쿼리에서 사용할 값 인자로 전달
    }
    
    public List<Member> selectAll() {
        List<Member> results = jdbcTemplate.query(
                "select * from MEMBER",
                (ResultSet rs, int rowNum) -> { // 람다식 가능
                    Member member = new Member(
                            rs.getString("EMAIL"),
                            rs.getString("PASSWORD"),
                            rs.getString("NAME"),
                            rs.getTimestamp("REGDATE").toLocalDateTime());
                    member.setId(rs.getLong("ID"));
                    return member;
                });
        return results;
    }
    
    public int count() {
        return jdbcTemplate.queryForObject("select count(*) from MEMBER", Integer.class);
        // queryForObject() : 쿼리 실행 결과 행이 1개인 경우 사용 가능 <-> query()
    }
    
}
