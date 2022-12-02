package main;

import config.AppConf1;
import config.AppConf2;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class MainForConfigurationType {
    
    public static void main(String[] args) {
        AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(AppConf1.class, AppConf2.class);
        
        AppConf1 appConf1 = ctx.getBean(AppConf1.class); // @Configuration 설정 클래스도 빈으로 등록
        System.out.println(appConf1 != null);
        
        ctx.close();
    }
    
}