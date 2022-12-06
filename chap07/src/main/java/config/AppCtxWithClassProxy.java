package config;

import aspect.ExeTimeAspect;
import chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // 인터페이스가 아닌 자바 클래스를 상속받아 프록시 생성
public class AppCtxWithClassProxy {
    
    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }
    
    @Bean
    public RecCalculator calculator() {
        return new RecCalculator();
    }
    
}
