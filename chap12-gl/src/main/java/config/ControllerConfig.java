package config;

import controller.RegisterControllerWithGlobalValidator;
import controller.RegisterControllerWithLocalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.MemberRegisterService;

@Configuration
public class ControllerConfig {
    
    @Autowired
    private MemberRegisterService memberRegSvc;
    
    @Bean
    public RegisterControllerWithLocalValidator registerControllerLocalValidator() {
        RegisterControllerWithLocalValidator controller = new RegisterControllerWithLocalValidator();
        controller.setMemberRegisterService(memberRegSvc);
        return controller;
    }
    
    @Bean
    public RegisterControllerWithGlobalValidator registerControllerGlobalValidator() {
        RegisterControllerWithGlobalValidator controller = new RegisterControllerWithGlobalValidator();
        controller.setMemberRegisterService(memberRegSvc);
        return controller;
    }
}
