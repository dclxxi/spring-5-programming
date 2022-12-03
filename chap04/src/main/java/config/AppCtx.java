package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration
public class AppCtx {
    
    /*
     * 자동 주입에 실패하고 익셉션을 발생시키는 경우
     * 1. @Autowired를 적용한 대상에 일치하는 빈이 없는 경우
     * 2. @Autowired를 붙인 주입 대상에 일치하는 빈이 2개 이상인 경우 (어떤 빈인지 정확하게 한정할 수 있어야 함)
     */
    
    /*
     * @Qualifier : 자동 주입 대상 빈 한정 (자동 주입 가능한 빈이 2개 이상인 경우)
     * 1. @Bean을 붙인 빈 설정 메서드에 사용 (필드, 메서드 모두 적용 가능)
     * 2. @Autowired에서 자동 주입할 빈을 한정할 때 사용
     */
    
    /*
     * 설정 클래스에서 의존을 주입했는데 자동 주입 대상인 경우 자동 주입을 통해 일치하는 빈 주입
     */
    
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
    
    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService();
    }
    
    @Bean // @Autowired -> 스프링이 MemberDao 타입의 빈 객체 주입 (setMemberDao 메서드 호출할 필요 X)
    public ChangePasswordService changePwdSvc() {
        return new ChangePasswordService();
    }
    
    @Bean
    @Qualifier("printer") // 자동 주입 대상 빈 한정 (printer : 해당 빈의 한정 값)
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
    }
    
    @Bean
    @Qualifier("summaryPrinter") // 주입할 빈 한정 (MemberPrinter 클래스 상속 -> MemberPrinter 타입에도 할당 가능)
    public MemberSummaryPrinter memberPrinter2() {
        return new MemberSummaryPrinter();
    }
    
    @Bean
    public MemberListPrinter listPrinter() {
        return new MemberListPrinter();
    }
    
    @Bean // @Autowired -> MemberInfoPrinter 객체의 세터 메서드 호출할 필요 X
    public MemberInfoPrinter infoPrinter() {
        return new MemberInfoPrinter();
    }
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
