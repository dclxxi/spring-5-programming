package main;

import config.AppCtx;
import java.io.IOException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import spring.Client;

public class Main {
    
    public static void main(String[] args) throws IOException {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class); // 컨테이너 초기화
        
        Client client = ctx.getBean(Client.class); // 컨테이너에서 빈 객체를 구해서 사용
        client.send();
        
        ctx.close(); // 컨테이너 종료
    }
    
}