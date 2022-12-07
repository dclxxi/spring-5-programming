package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;

@Configuration
@EnableTransactionManagement // @Transactional이 붙은 메서드를 트랜잭션 범위에서 실행하는 기능 활성화 (프록시 객체 생성)
public class AppCtx {
    
    /*
     * 커넥션 풀 : 커넥션 생성, 유지
     * - getConnection() : 커넥션 요청 -> 활성 상태 (active)
     * - close() : 커넥션 반환 -> 유휴 상태 (idle)
     *
     * 커넥션 풀 사용 이유 : 성능
     * - 미리 커넥션 생성했다가 필요할 때 커넥션 꺼내 사용 -> 커넥션 구하는 시간 단축
     * */
    
    @Bean(destroyMethod = "close") // close : 커넥션 풀에 보관된 Connection 닫음
    public DataSource dataSource() { // DataSource를 스프링 빈으로 등록 (Tomcat JDBC 모듈 -> 커넥션 풀 기능 제공)
        DataSource ds = new DataSource(); // DataSource 객체 생성
        ds.setDriverClassName("com.mysql.jdbc.Driver"); // JDBC 드라이버 클래스 (MySQL 드라이버 클래스)
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8"); // JDBC URL
        ds.setUsername("spring5"); // DB 연결 시 사용자 계정
        ds.setPassword("spring5"); // DB 연결 시 암호
        ds.setInitialSize(2); // 커넥션 풀 초기화 시 생성할 초기 커넥션 개수 (기본값 : 10)
        ds.setMaxActive(10); // 커넥션 풀에서 가져올 수 있는 최대 커넥션 개수 -> 활성 상태 (기본값 : 100)
        ds.setTestWhileIdle(true); // 커넥션 풀에 유휴 상태로 있는 동안 검사 여부 (기본값 : false)
        ds.setMinEvictableIdleTimeMillis(1000 * 60 * 3); // 커넥션 풀에 유휴 상태로 유지할 최소 시간 (기본값 : 60,000밀리초)
        ds.setTimeBetweenEvictionRunsMillis(1000 * 10); // 커넥션 풀의 유휴 커넥션 검사 주기 (기본값 : 5,000밀리초, 1초 이하 X)
        return ds;
    }
    
    @Bean // @Transactional -> 플랫폼 트랜잭션 매니저 빈 설정
    public PlatformTransactionManager transactionManager() {
        // PlatformTransactionManager : 스프링이 제공하는 트랜잭션 매니저 인터페이스(구현기술 상관없이 동일한 방식으로 트랜잭션 처리)
        DataSourceTransactionManager tm = new DataSourceTransactionManager(); // JDBC
        tm.setDataSource(dataSource()); // 트랜잭션 연동에 사용할 DataSource 지정
        return tm;
    }
    
    @Bean
    public MemberDao memberDao() {
        return new MemberDao(dataSource());
    }
    
    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDao());
    }
    
    @Bean
    public ChangePasswordService changePwdSvc() {
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao());
        return pwdSvc;
    }
    
    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }
    
    @Bean
    public MemberListPrinter listPrinter() {
        return new MemberListPrinter(memberDao(), memberPrinter());
    }
    
    @Bean
    public MemberInfoPrinter infoPrinter() {
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(memberPrinter());
        return infoPrinter;
    }
}
