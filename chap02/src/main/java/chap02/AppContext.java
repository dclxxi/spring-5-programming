package chap02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 해당 클래스를 스프링 설정 클래스로 지정
public class AppContext {
    
    /*
     * 스프링 : 객체 생성 및 초기화 기능 제공
     * 빈(Bean) 객체 : 스프링이 생성하는 객체
     */
    
    @Bean // 해당 메서드가 생성한 객체를 스프링이 관리하는 빈 객체로 등록
    public Greeter greeter() { // 빈 객체에 대한 정보를 담고 있는 메서드
        Greeter g = new Greeter();
        g.setFormat("%s, 안녕하세요!"); // Greeter 객체 초기화
        return g;
    }
    
}
