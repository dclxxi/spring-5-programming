package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 컨테이너에 빈으로 등록 (WebMvcConfigurer 타입 빈)
@EnableWebMvc // 스프링 MVC 설정 활성화 (내부적으로 다양한 빈 설정 추가 -> 기본적인 구성 설정)
public class MvcConfig implements WebMvcConfigurer { // WebMvcConfigurer : 스프링 MVC 개별 설정 조정
    
    /*
     * DispatcherServlet 요청 처리 방식
     * 1. RequestMappingHandlerMapping을 사용해서 요청을 처리할 핸들러 검색
     *    - 존재하면 해당 컨트롤러를 이용해서 요청 처리
     * 2. SimpleUrlHandlerMapping을 사용해서 요청을 처리할 핸들러 검색 (우선순위 가장 낮음 -> 별도 설정 없는 모든 요청 경로)
     *    - SimpleUrlHandlerMapping은 모든 경로("/**")에 대해 DefaultServletHttpRequestHandler를 리턴
     *    - DispatcherServlet은 DefaultServletHttpRequestHandler에 처리 요청
     *    - DefaultServletHttpRequestHandler는 디폴트 서블릿에 처리 위임
     */
    
    @Override // DispatcherServlet 매핑 경로를 '/'로 주었을 때, JSP, HTML, CSS 등을 올바르게 처리하기 위한 설정 추가
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable(); // DefaultServletHttpRequestHandler, SimpleUrlHandlerMapping 빈 객체 추가
    }
    
    @Override // JSP를 이용해서 컨트롤러의 실행 결과를 보여주기 위한 설정 추가
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/view/", ".jsp");
        // JSP를 뷰 구현으로 사용할 수 있도록 해주는 설정 (JSP 파일 경로 찾을 때 사용할 접두어, 접미사)
    }
    
}
