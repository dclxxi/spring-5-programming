package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
public class RegisterController {
    
    /*
     * 여러 단계를 거쳐 하나의 기능잉 완성되는 경우 관련 요청 경로를 1개의 컨트롤러 클래스에서 처리 -> 코드 관리
     *
     * 컨트롤러 메서드에서 요청 파라미터를 사용하는 방법
     * 1. HttpServletRequest 직접 이용 (getParameter() 이용)
     * 2. @RequestParam 이용 (요청 파라미터 개수가 몇 개 안 되는 경우 간단하게 값 구할 수 있음)
     */
    
    /*
     * 스프링 MVC : 파라미터 타입에 맞게 String 값 변환
     */
    
    /*
     * "redirect:"로 시작하는 경로 리턴 -> 나머지 경로를 이용해 리다이렉트할 경로 구함
     * - "/"로 시작 O : 웹 어플리케이션을 기준으로 이동 경로 생성
     * - "/"로 시작 X : 현재 경로를 기준으로 상대 경로 사용
     * - 완전한 URL 사용 가능
     */
    
    private MemberRegisterService memberRegisterService;
    
    public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }
    
    @RequestMapping("/register/step1") // 약관 동의 화면 (별도 설정 X -> GET, POST 방식 상관없이 지정 경로와 일치하는 요청 처리)
    public String handleStep1() {
        return "register/step1"; // 별 다른 처리 없이 약관 내용 보여줌 -> 뷰 이름 리턴
    }
    
    @PostMapping("/register/step2") // 회원 정보 입력 화면
    public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, Model model) {
        // value : HTTP 요청 파라미터 이름 지정
        // required : 필수 여부 지정 (기본값 : true -> 값 없으면 익셉션 발생)
        // defaultValue : 요청 파라미터 값이 없을 때 사용할 문자열 값 지정 (기본값 없음)
        if (!agree) {
            return "register/step1";
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/step2";
    }
    
    @GetMapping("/register/step2") // 같은 경로에 대해 GET, POST 방식 각각 다른 메서드가 처리 가능
    public String handleStep2Get() {
        return "redirect:/register/step1"; // 잘못된 전송 방식으로 요청 -> 알맞은 경로로 리다이렉트
    }
    
    @PostMapping("/register/step3") // 가입 처리 결과 화면
    public String handleStep3(RegisterRequest regReq) { // 세터 메서드를 포함하는 객체를 커맨드 객체로 사용
        // @ModelAttribute("formData") : 커맨드 객체에 접근할 때 사용할 속성 이름 변경
        try {
            memberRegisterService.regist(regReq); // 요청 파라미터 값을 커맨드 객체에 복사한 뒤 파라미터로 전달
            return "register/step3";
        } catch (DuplicateMemberException ex) {
            return "register/step2";
        }
    }
    
}
