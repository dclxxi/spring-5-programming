package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

@Configuration // 스프링 컨테이너 : @Configuration이 붙은 설정 클래스를 내부적으로 스프링 빈으로 등록
public class AppConf2 {
    
    @Autowired // 스프링 자동 주입 기능 -> 해당 타입의 빈을 찾아서 필드에 할당 (MemberDao 타입의 빈을 memberDao 필드에 할당)
    private MemberDao memberDao;
    @Autowired
    private MemberPrinter memberPrinter;
    
    @Bean
    public MemberRegisterService memberRegSvc() {
        return new MemberRegisterService(memberDao);
    }
    
    @Bean
    public ChangePasswordService changePwdSvc() {
        ChangePasswordService pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao);
        return pwdSvc;
    }
    
    @Bean
    public MemberListPrinter listPrinter() { // 필드로 주입받은 빈 객체를 생성자 주입
        return new MemberListPrinter(memberDao, memberPrinter);
    }
    
    @Bean
    public MemberInfoPrinter infoPrinter() {
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setMemberDao(memberDao);
        infoPrinter.setPrinter(memberPrinter);
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
