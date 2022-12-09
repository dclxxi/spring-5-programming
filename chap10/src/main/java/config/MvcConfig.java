package config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class MvcConfig { // 직접 설정 (@EnableConfigurationWebMvc 사용 X)
    
    /*
     * DispatcherServlet : 웹 브라우저의 요청을 받기 위한 창구 역할 (다른 주요 구성 요소들을 이용해서 요청 흐름 제어)
     */
    
    @Bean
    public HandlerMapping handlerMapping() { // 클라이언트의 요청을 처리할 핸들러 객체 찾아줌
        // 핸들러(커맨드) 객체 : 클라이언트의 요청을 실제로 처리한 뒤 뷰 정보와 모델 설정
        RequestMappingHandlerMapping hm = new RequestMappingHandlerMapping();
        hm.setOrder(0);
        return hm;
    }
    
    @Bean
    public HandlerAdapter handlerAdapter() { // DispatcherServlet과 핸들러 객체 사이의 변환을 알맞게 처리
        return new RequestMappingHandlerAdapter();
    }
    
    @Bean
    public HandlerMapping simpleHandlerMapping() { // 모든 경로("/**")를 DefaultServletHttpRequestHandler를 이용해서 처리
        SimpleUrlHandlerMapping hm = new SimpleUrlHandlerMapping();
        Map<String, Object> pathMap = new HashMap<>();
        pathMap.put("/**", defaultServletHandler());
        hm.setUrlMap(pathMap);
        return hm;
    }
    
    @Bean
    public HttpRequestHandler defaultServletHandler() { // 클라이언트의 모든 요청을 WAS가 제공하는 디폴트 서블릿에 전달
        return new DefaultServletHttpRequestHandler();
    }
    
    @Bean
    public HandlerAdapter requestHandlerAdapter() {
        return new HttpRequestHandlerAdapter();
    }
    
    @Bean
    public ViewResolver viewResolver() { // DispatcherServlet -> 뷰 이름에 해당하는 View 객체 요청
        // View : 최종적으로 클라이언트에 응답을 생성해서 전달
        InternalResourceViewResolver vr = new InternalResourceViewResolver();
        // prefix + 뷰이름 + suffix에 해당하는 경로를 뷰 코드로 사용하는 InternalResourceView 타입 객체 리턴
        vr.setPrefix("/WEB-INF/view/");
        vr.setSuffix(".jsp");
        return vr;
    }
    
}
