package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import interceptor.AuthCheckInterceptor;
import java.util.List;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc // 스프링 MVC -> 여러 형식으로 변환할 수 있는 HttpMessageConverter 미리 등록
public class MvcConfig implements WebMvcConfigurer {
    
    /*
     * 스프링 MVC : 자바 객체를 HTTP 응답으로 변환할 때 HttpMessageConverter 사용 -> 새로 등록해서 설정 가능
     * ex) Jackson으로 자바 객체를 JSON으로 변환 -> MappingJackson2HttpMessageConverter
     *     Jaxb로 XML로 변환 -> Jaxb2RootElementHttpMessageConverter
     *
     * */
    
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
    }
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/main").setViewName("main");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authCheckInterceptor()).addPathPatterns("/edit/**")
                .excludePathPatterns("/edit/help/**");
    }
    
    @Bean
    public AuthCheckInterceptor authCheckInterceptor() {
        return new AuthCheckInterceptor();
    }
    
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setBasenames("message.label");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }
    
    @Override // 모든 날짜 타입을 ISO-8601 형식으로 변환하기 위한 설정 (HttpMessageConverter 추가 설정)
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 등록된 HttpMessageConverter 목록을 파라미터로 받음
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json()
                // ObjectMapper : JSON으로 변환할 때 사용
                // Jackson2ObjectMapperBuilder : ObjectMapper를 쉽게 생성할 수 있도록 스프링에서 제공하는 클래스
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // Jackson이 날짜 형식을 출력할 때 유닉스 타임 스탬프로 출력하는 기능 비활성화
                .build();
        converters.add(0, new MappingJackson2HttpMessageConverter(objectMapper));
        // 목록의 제일 앞에 위치 (가장 먼저 적용)
    }
    
    /*
        <모든 Date 타입에 대해 원하는 패턴 설정>
        simpleDateFormat("yyyy-MM-dd HH:mm:ss") // LocalDateTime 타입 변환에는 사용 X (ISO-8601 형식으로 변환됨)
        
        <모든 LocalDateTime 타입에 대해 원하는 패턴 설정>
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        serializerByType(LocalDateTime .class, new LocalDateTimeSerializer(formatter))
        
        <JSON 데이터를 LocalDateTime 타입으로 변환할 때 사용할 패턴 지정>
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter))
    */
}
