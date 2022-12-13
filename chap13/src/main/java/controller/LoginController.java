package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.AuthService;
import spring.WrongIdPasswordException;

@Controller
@RequestMapping("/login")
public class LoginController {
    
    /*
     * 로그인 상태 유지 방법
     * 1. HttpSession
     *    a. HttpSession : 컨트롤러 메서드를 호출할 때 HttpSession 객체를 파라미터로 전달 => 항상 HttpSession 생성
     *                     (생성 전 -> 새로운 HttpSession 생성, 생성 후 -> 기존에 존재하는 HttpSession 전달)
     *    b. HttpServletResponse : getSession()을 이용해 HttpSession 구함 => 필요한 시점에만 HttpSession 생성
     * 2. 쿠키
     *    - @CookieValue : 요청 매핑 애노테이션 적용 메서드의 Cookie 파라미터에 적용
     *    - 쿠키 생성 시 HttpServletResponse 객체 필요
     */
    
    private AuthService authService;
    
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
    
    @GetMapping
    public String form(LoginCommand loginCommand, @CookieValue(value = "REMEMBER", required = false) Cookie rCookie) {
        // value : 쿠키 이름 지정, required : 지정한 이름을 가진 쿠키 필수 여부 (기본값 : true)
        if (rCookie != null) { // 쿠키가 존재할 경우
            loginCommand.setEmail(rCookie.getValue()); // 폼에 전달할 커맨드 객체의 email 프로퍼티를 쿠키의 값으로 설정
            loginCommand.setRememberEmail(true);
        }
        return "login/loginForm";
    }
    
    @PostMapping
    public String submit(LoginCommand loginCommand, Errors errors, HttpSession session, HttpServletResponse response) {
        new LoginCommandValidator().validate(loginCommand, errors);
        if (errors.hasErrors()) {
            return "login/loginForm"; // 로그인 폼
        }
        try {
            AuthInfo authInfo = authService.authenticate(loginCommand.getEmail(), loginCommand.getPassword());
            
            session.setAttribute("authInfo", authInfo); // HttpSession의 "authInfo" 속성에 AuthInfo 객체 저장
            
            Cookie rememberCookie = new Cookie("REMEMBER", loginCommand.getEmail());
            // 로그인 성공 후 이메일을 담고 있는 쿠키 생성
            rememberCookie.setPath("/");
            if (loginCommand.isRememberEmail()) { // 이메일 기억하기 옵션을 선택한 경우
                rememberCookie.setMaxAge(60 * 60 * 24 * 30); // 30일 동안 유지되는 쿠키 생성
            } else {
                rememberCookie.setMaxAge(0); // 바로 삭제되는 쿠키 생성
            }
            response.addCookie(rememberCookie);
            
            return "login/loginSuccess"; // 로그인 성공 결과
        } catch (WrongIdPasswordException e) {
            errors.reject("idPasswordNotMatching");
            return "login/loginForm";
        }
    }
}
