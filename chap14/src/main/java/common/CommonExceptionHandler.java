package common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("spring") // "spring" 패키지와 그 하위 패키지에 속한 컨트롤러 클래스를 위한 공통 기능 정의
public class CommonExceptionHandler { // 지정한 범위의 컨트롤러에 공통으로 사용될 설정 지정 -> 스프링 빈으로 등록 필요
    
    /*
     * <@ExceptionHandler 메서드 우선 순위>
     * 1. 컨트롤러 클래스
     * 2. @ControllerAdvice 클래스
     *
     * <컨트롤러 메서드 실행 과정에서 익셉션 발생 시 @ExceptionHandler 메서드 찾는 순서>
     * 1. 같은 컨트롤러에 위치한 @ExceptionHandler 메서드 중 해당 익셉션 처리할 수 있는 메서드 검색
     * 2. 같은 클래스에 위치한 메서드가 익셉션을 처리할 수 없을 경우 @ControllerAdvice 클래스에 위치한 @ExceptionHandler 메서드 검색
     */
    
    /*
     * @ExceptionHandler 메서드
     * - 파라미터 종류 : HttpServletRequest, HttpServletResponse, HttpSession, Model, 익셉션
     * - 리턴 타입 : ModelAndView, String (뷰 이름), 임의 객체 (@ResponseBody), ResponseEntity
     */
    
    @ExceptionHandler(RuntimeException.class) // 여러 컨트롤러에서 동일하게 처리할 익셉션
    public String handleRuntimeException() {
        return "error/commonException";
    }
}
