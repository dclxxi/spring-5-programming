package config;

import aspect.ExeTimeAspect;
import chap07.Calculator;
import chap07.RecCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy // @Aspect를 붙인 클래스를 공통 기능으로 적용
// (스프링 -> @Aspect 붙은 빈 객체를 찾아서 빈 객체의 @Pointcut, @Around 설정 사용)
public class AppCtx {
    
    @Bean
    public ExeTimeAspect exeTimeAspect() {
        return new ExeTimeAspect();
    }
    
    @Bean
    public Calculator calculator() { // ExeTimeAspect 클래스에 정의한 measure() 적용 (@Pointcut에 해당)
        return new RecCalculator();
    }
    
}
