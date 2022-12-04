package config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import spring.MemberDao;
import spring.MemberPrinter;
import spring.MemberSummaryPrinter;
import spring.VersionPrinter;

@Configuration
@ComponentScan(basePackages = {"spring", "spring2"},
        excludeFilters = {@Filter(type = FilterType.ANNOTATION, classes = ManualBean.class)})
public class AppCtxWithExclude {
    
    /*
     * excludeFilters : 스캔할 때 특정 대상을 자동 등록 대상에서 제외
     *
     * FilterType.REGEX : 정규표현식 ex) pattern = "spring\\..*Dao"
     * FilterType.ASPECTJ : AspectJ 패턴 ex) pattern = "spring.*Dao"
     * FilterType.ANNOTATION : 특정 애노테이션 붙인 타입 제외 ex) classes = {NoProduct.class, ManualBean.class})
     * FilterType.ASSIGNABLE_TYPE : 특정 타입이나 하위 타입 제외 ex) classes = MemberDao.class
     */
    
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
    
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
