package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main2 {
    
    /*
     * 스프링 : 빈 객체 -> 싱글톤 범위를 가짐 (단일 객체)
     */
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        Greeter g1 = ctx.getBean("greeter", Greeter.class);
        Greeter g2 = ctx.getBean("greeter", Greeter.class);
        System.out.println("(g1 == g2) = " + (g1 == g2)); // true (같은 객체)
        ctx.close();
    }
}
