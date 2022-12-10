package config;

import form.FormController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {
    
    @Bean
    public FormController loginController() {
        return new FormController();
    }
}
