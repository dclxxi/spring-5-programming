package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService {
    
    /*
     * 트랜잭션(transaction) : 2개 이상의 쿼리를 한 작업으로 실행해야 할 때 사용
     * - 여러 쿼리를 논리적으로 하나의 작업으로 묶음 -> 하나라도 실패하면 이전에 실행한 쿼리 취소 (전체 쿼리를 실패로 간주)
     * - 스프링 -> 프랜잭션 처리 위해 내부적으로 AOP 사용 (프록시)
     *
     * 롤백(rollback) : 쿼리 실행 결과를 취소하고 DB를 기존 상태로 되돌림
     * 커밋(commit) : 트랜잭션으로 묶인 모든 쿼리가 성공해서 쿼리 결과를 DB에 실제로 반영
     *
     * 트랜잭션 시작 -> 트랜잭션을 커밋하거나 롤백할 때까지 실행한 쿼리들이 하나의 작업 단위
     * (JDBC : Connection의 setAutoCommit(false)를 이용해 트랜잭션 시작 -> commit(), rollback() 이용)
     */
    
    private MemberDao memberDao;
    
    @Transactional // 트랜잭션 범위 지정 (플랫폼 트랜잭션 매니저 빈 설정, @Transactional 활성화 지정 필요)
    public void changePassword(String email, String oldPwd, String newPwd) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        
        member.changePassword(oldPwd, newPwd);
        
        memberDao.update(member); // @Transactional 적용 X -> JdbcTemplate 클래스때문에 쿼리 실행 가능
        // JdbcTemplate : 진행 중인 트랜잭션 존재 -> 해당 트랜잭션 범위에서 쿼리 실행
    }
    
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
}
