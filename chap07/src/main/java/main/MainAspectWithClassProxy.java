package main;

import chap07.RecCalculator;
import config.AppCtxWithClassProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithClassProxy {
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxWithClassProxy.class);
        
        RecCalculator cal = ctx.getBean("calculator", RecCalculator.class);
        // @EnableAspectJAutoProxy(proxyTargetClass = true) -> 실제 클래스를 이용해서 빈 객체 구할 수 있음
        long fiveFact = cal.factorial(5);
        System.out.println("cal.factorial(5) = " + fiveFact);
        System.out.println(cal.getClass().getName());
        ctx.close();
    }
    
}
