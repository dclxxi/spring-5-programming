package controller;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

public class ListCommand { // 검색 기준 시간 표현
    
    /*
     * 스프링 MVC : 요청 매핑 애노테이션 적용 메서드 - DispatcherServlet 연결하기 위해 RequestMappingHandlerAdapter 객체 사용
     * RequestMappingHandlerAdapter : 요청 파라미터 - 커맨드 객체 사이 변환 처리 위해 WebDataBinder 이용
     *
     * WebDataBinder
     * - 커맨드 객체 생성 + 프로퍼티와 같은 이름을 갖는 요청 파라미터를 이용해서 값 생성
     * - <form:input>의 path 속성에 지정한 프로퍼티 값을 String으로 변환 (<input>의 value 속성값으로 생성)
     * => ConversionService에 역할 위임 (직접 타입 변환 X)
     *
     * DefaultFormattingConversionService : 기본 데이터 타입, 시간 관련 타입 변환 기능 제공
     */
    
    @DateTimeFormat(pattern = "yyyyMMddHH") // 문자열을 LocalDateTime 타입으로 변환 ex) 2018030115 -> 2018년 3월 1일 15시
    private LocalDateTime from;
    @DateTimeFormat(pattern = "yyyyMMddHH")
    private LocalDateTime to;
    
    public LocalDateTime getFrom() {
        return from;
    }
    
    public void setFrom(LocalDateTime from) {
        this.from = from;
    }
    
    public LocalDateTime getTo() {
        return to;
    }
    
    public void setTo(LocalDateTime to) {
        this.to = to;
    }
    
}
