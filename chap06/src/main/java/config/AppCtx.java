package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.Client;
import spring.Client2;

@Configuration
public class AppCtx {
    
    /*
     * 스프링 컨테이너 : 빈 객체의 라이프사이클 관리
     * 1. 객체 생성 : 빈 객체 생성
     * 2. 의존 설정 : 의존 자동 주입을 통한 의존 설정
     * 3. 초기화 : 빈 객체 초기화 (빈 객체의 지정된 메서드 호출)
     * 4. 소멸 : 스프링 컨테이너 종료 시 빈 객체 소멸 처리 (지정된 메서드 호출)
     */
    
    @Bean
    public Client client() {
        Client client = new Client();
        client.setHost("host"); // afterPropertiesSet() 실행 X (중복 실행됨)
        return client;
    }
    
    @Bean(initMethod = "connect", destroyMethod = "close") // 초기화, 소멸 과정에서 사용할 메서드 지정 (파라미터 존재 시 익셉션)
    public Client2 client2() {
        Client2 client = new Client2();
        client.setHost("host");
        return client;
    }
    
/*
    빈 설정 메서드에서 직접 초기화 가능

    @Bean(destroyMethod = "close")
    public Client2 client2() {
        Client2 client = new Client2();
        client.setHost("host");
        client.connect();
        return client;
    }
*/

}
