package interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthCheckInterceptor implements HandlerInterceptor {
    
    /*
     * HandlerInterceptor : 필요한 메서드만 재정의하면 됨
     * - preHandle() : 컨트롤러(핸들러) 실행 전
     *                 false 리턴 -> 컨트롤러 또는 다음 HandlerInterceptor 실행 X
     * - postHandle() : 컨트롤러(핸들러) 실행 후, 아직 뷰 실행 전 (추가 기능 구현)
     *                  컨트롤러가 익셉션 발생 -> 실행 X
     * - afterCompletion() : 뷰 실행 후 (뷰가 클라이언트에 응답 전송한 뒤 후처리 구현)
     *                       ex) 컨트롤러 실행 후 예기치 않게 발생한 익셉션을 로그로 남기거나 실행 시간 기록
     *                       익셉션 발생 -> 네 번째 파라미터로 전달, 익셉션 발생 X -> null
     */
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        // handler : 웹 요청을 처리할 컨트롤러(핸들러) 객체 ()
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object authInfo = session.getAttribute("authInfo");
            if (authInfo != null) {
                return true; // 컨트롤러 실행
            }
        }
        response.sendRedirect(request.getContextPath() + "/login"); // getContextPath() : 현재 컨텍스트 경로
        // HttpSession에 "authInfo" 속성이 존재하지 않으면 지정한 경로로 리다이렉트
        // (로그인하지 않은 상태에서 비밀번호 변경 폼 요청 시 로그인 화면으로 이동)
        return false;
    }
    
}
