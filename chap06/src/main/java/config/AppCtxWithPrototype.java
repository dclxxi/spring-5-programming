package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import spring.Client;
import spring.Client2;

@Configuration
public class AppCtxWithPrototype {
    
    /*
     * 프로토타입 범위 갖는 빈 -> 완전한 라이프사이클 따르지 않음
     * => 컨테이너 종료 시 빈 객체 소멸 메서드 실행 X (코드에서 직접 처리)
     */
    
    @Bean
    @Scope("prototype") // 프로토타입 범위로 지정 -> 빈 객체 구할 때마다 새로운 객체 생성
    public Client client() {
        Client client = new Client();
        client.setHost("host");
        return client;
    }
    
    @Bean(initMethod = "connect", destroyMethod = "close")
    @Scope("singleton") // 싱글톤 범위 명시적 지정
    public Client2 client2() {
        Client2 client = new Client2();
        client.setHost("host");
        return client;
    }
    
}
