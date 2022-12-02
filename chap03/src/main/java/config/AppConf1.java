package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.MemberDao;
import spring.MemberPrinter;

@Configuration
public class AppConf1 {
    
    @Bean
    public MemberDao memberDao() { // @Autowired -> 의존 주입 코드 작성 X
        return new MemberDao();
    }
    
    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }
    
}
