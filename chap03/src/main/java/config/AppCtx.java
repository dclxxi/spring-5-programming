package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

@Configuration // 스프링 설정 클래스
public class AppCtx { // 스프링이 어떤 객체를 생성하고 의존을 어떻게 주입할지 정의한 설정 정보
    
    /*
     * 생성자 주입 : 빈 객체를 생성하는 시점에 모든 의존 객체 주입
     * 세터 주입 : 세터 메서드 이름을 통해 어떤 의존 객체가 주입되는지 알 수 있음
     * (필요한 의존 객체를 전달하지 않아도 빈 객체 생성 -> 객체 사용 시점에 NullPointerException 발생 가능성)
     */
    
    /*
     * 스프링 -> 설정 클래스를 상속한 새 설정 클래스를 만들어서 사용 (그대로 사용 X)
     */
    
    @Bean // 해당 메서드가 생성한 객체를 스프링 빈으로 설정 (각 메서드마다 한 개의 빈 객체 생성)
    public MemberDao memberDao() { // 메서드 이름 == 빈 객체 이름
        return new MemberDao();
    }
    
    @Bean
    public MemberRegisterService memberRegSvc() { // 생성자 주입
        return new MemberRegisterService(memberDao());
    }
    
    @Bean
    public ChangePasswordService changePwdSvc() { // 세터 주입
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
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5); // 기본 데이터 타입 값 설정 (int, long, String ...)
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
