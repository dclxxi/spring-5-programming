package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import spring.MemberDao;
import spring.MemberPrinter;

/*
 * @Import : 다른 설정 클래스 추가 -> 최상위 설정 클래스만 수정하면 됨 (Main 수정 X)
 */

@Configuration
@Import(AppConf2.class) // 함께 사용할 설정 클래스 지정 -> 스프링 컨테이너 생성 시 지정할 필요 X
// ex) @Import({AppConf1.class, AppConf2.class}) -> 배열 이용 가능
public class AppConfImport {
    
    @Bean
    public MemberDao memberDao() {
        return new MemberDao();
    }
    
    @Bean
    public MemberPrinter memberPrinter() {
        return new MemberPrinter();
    }
}
