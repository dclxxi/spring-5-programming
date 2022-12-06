package main;

import chap07.Calculator;
import config.AppCtxWithCache;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainAspectWithCache {
    /*
     * Advice 적용 순서 : CacheAspect 프록시 -> ExeTimeAspect 프로시 -> 실제 대상 객체
     * - CacheAspect 프록시 객체의 대상 객체 -> ExeTimeAspect 프로시 객체
     * - ExeTimeAspect 프로시 객체의 대상 객체 -> 실제 대상 객체
     *
     * (Aspect 적용 순서 -> 스프링 프레임워크나 자바 버전에 따라 달라질 수 있음)
     */
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtxWithCache.class);
        
        Calculator cal = ctx.getBean("calculator", Calculator.class); // CacheAspect 프록시 객체
        cal.factorial(7);
        // RecCalculator.factorial([7]) 실행 시간 : 22400 ns
        // CacheAspect: Cache에 추가[7]
        cal.factorial(7);
        // CacheAspect: Cache에서 구함[7] -> cache.containsKey(num)에 해당하므로 joinPoint.proceed() 실행 X
        cal.factorial(5);
        // RecCalculator.factorial([5]) 실행 시간 : 10900 ns
        // CacheAspect: Cache에 추가[5]
        cal.factorial(5);
        // CacheAspect: Cache에서 구함[5]
        ctx.close();
    }
    
}
