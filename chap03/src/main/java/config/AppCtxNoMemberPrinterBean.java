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

@Configuration
public class AppCtxNoMemberPrinterBean {
    
    /*
     * 스프링 컨테이너 : 단순 객체 생성 (자동 주입, 라이프사이클 관리), 객체 관리 기능 제공 -> 빈으로 등록만 객체에만 기능 적용
     * 관리 기능 & getBean() 필요 X -> 빈 객체 등록 필수 X
     */
    
    private MemberPrinter printer = new MemberPrinter(); // 주입 객체 -> 일반 객체도 가능 (스프링 빈 등록 X)
    
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
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
    public MemberListPrinter listPrinter() {
        return new MemberListPrinter(memberDao(), printer);
    }
    
    @Bean
    public MemberInfoPrinter infoPrinter() {
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao());
        infoPrinter.setPrinter(printer);
        return infoPrinter;
    }
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
