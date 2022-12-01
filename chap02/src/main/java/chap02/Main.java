package chap02;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
// AnnotationConfigApplicationContext 클래스 : 자바 설정에서 정보를 읽어와 빈 객체 생성 및 관리

public class Main {
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppContext.class);
        // 생성자 파리미터 : AppContext 클래스 -> AppContext에 정의한 @Bean 설정 정보를 읽어와 Greeter 객체 생성 및 초기화
        Greeter g = ctx.getBean("greeter", Greeter.class); // 빈 객체 검색 (빈 객체 이름, 타입)
        String msg = g.greet("스프링");
        System.out.println(msg);
        ctx.close();
    }
}
