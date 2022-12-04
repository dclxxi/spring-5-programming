package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import spring.MemberPrinter;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration
@ComponentScan(basePackages = {"spring"}) // @Component 클래스를 스캔 후 객체 생성해서 스프링 빈으로 등록
// basePackages : 스캔 대상 패키지 목록 지정 ex) spring 패키지와 그 하위 패키지에 속한 클래스를 스캔 대상으로 설정
public class AppCtx {
    
    /*
     * 컴포넌트 스캔 : 스프르링이 직접 클래스를 검색해서 빈으로 등록해주는 기능
     * 설정 클래스에 빈으로 등록하지 않아도 우너하는 클래스를 빈으로 등록 가능
     *
     * @Component(@Controller, @Service, @Repository, @Configuration), @Aspect
     */
    
    /*
     * 컴포넌트 스캔에 따른 충돌
     * 1. 빈 이름 충돌 : 서로 다른 타입인데 같은 빈 이름을 사용하는 경우 > 둘 중 하나에 명시적으로 빈 이름 지정
     * 2. 수동 등록에 따른 충돌 : 스컌 시 사용하는 빈 이름과 수동 등록한 빈 이름 동일 -> 수동 등록한 빈이 우선 (1개 존재)
     *                        다른 이름 사용 -> @Qualifier 사용 (모두 존재)
     */
    
    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1() {
        return new MemberPrinter();
    }
    
    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2() {
        return new MemberSummaryPrinter();
    }
    
    @Bean
    public VersionPrinter versionPrinter() {
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return versionPrinter;
    }
}
