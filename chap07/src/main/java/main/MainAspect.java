package main;

import chap07.Calculator;
import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspect {
    
    /*
     * AOP를 위한 프록시 객체를 생성 -> 실제 생성할 빈 객체가 인터페이스를 상속하면 인터페이스를 이용해서 프록시 생성
     * ex) $Proxy17 클래스 : RecCalculator 클래스가 상속받은 Calculator 인터페이스 상속받음
     */
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        
        Calculator cal = ctx.getBean("calculator", Calculator.class);
        // "calculator" 빈의 실제 타입 -> Calculator를 상속한 프록시 타입 (RecCalculator로 형변환 X)
        long fiveFact = cal.factorial(5);
        System.out.println("cal.factorial(5) = " + fiveFact);
        System.out.println(cal.getClass().getName());
        // AOP 적용 O -> com.sun.proxy.$Proxy17 (스프링이 생성한 프록시 타입)
        // AOP 적용 X -> chap07.RecCalculator
        ctx.close();
    }
    
}
