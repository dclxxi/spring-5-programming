package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class PropertyConfig {
    
    /*
     * Resource 인터페이스 : 스프링에서 자원 표현 시 사용
     * - ClassPathResource : 클래스 패스에 위치한 자원으로부터 데이터 읽음
     * - FileSystemResource : 파일 시스템에 위치한 자원으로부터 데이터 읽음
     */
    
    @Bean // PropertySourcesPlaceholderConfigurer -> 특수 목적 빈 (static 필수!)
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocations(new ClassPathResource("db.properties"), new ClassPathResource("info.properties"));
        // setLocations() : 프로퍼티 파일 목록을 인자로 전달받음 (스프링의 Resource 타입을 이용해서 파일 경로 전달)
        return configurer;
    }
    
}
