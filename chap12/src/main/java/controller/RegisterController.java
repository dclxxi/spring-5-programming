package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
     * ValidationUtils : 객체의 값 검증 코드를 간결하게 작성할 수 있도록 도와줌
     *                   (Errors 객체 전달 -> 검사 대상 객체를 직접 전달하지 않아도 값 검증 가능)
     * Errors : 커맨드 객체의 특정 프로퍼티 값을 구할 수 있는 getFieldValue() 제공
     * BindingResult : Errors 인터페이스 상속
     */
    
    private MemberRegisterService memberRegisterService;
    
    public void setMemberRegisterService(MemberRegisterService memberRegisterService) {
        this.memberRegisterService = memberRegisterService;
    }
    
    @RequestMapping("/register/step1")
    public String handleStep1() {
        return "register/step1";
    }
    
    @PostMapping("/register/step2")
    public String handleStep2(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, Model model) {
        if (!agree) {
            return "register/step1";
        }
        model.addAttribute("registerRequest", new RegisterRequest());
        return "register/step2";
    }
    
    @GetMapping("/register/step2")
    public String handleStep2Get() {
        return "redirect:/register/step1";
    }
    
    @PostMapping("/register/step3")
    public String handleStep3(RegisterRequest regReq, Errors errors) { // Errors 파리미터가 커맨드 객체 앞에 위치하면 익셉션
        // 스프링 MVC -> 메서드 호출 시 커맨드 객체와 연결된 Errors 객체를 생성해서 파라미터로 전달
        new RegisterRequestValidator().validate(regReq, errors);
        // RegisterRequest 커맨드 객체의 값이 올바른지 검사하고 결과를 Errors 객체에 담음
        if (errors.hasErrors()) { // 에러가 존재하는지 검사
            return "register/step2";
        }
        
        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex) { // 유효하지 않은 값 존재
            errors.rejectValue("email", "duplicate"); // 1번 이상 호출 -> hasErrors()가 true 리턴
            // reject() : 개별 프로퍼티가 아닌 객체 자체에 에러 코드 추가 (커맨드 객체 자체가 잘못된 경우 -> 글로벌 에러)
            // ex) 아이디와 비밀번호가 불일치하는 경우
            return "register/step2";
        }
    }
    
}
