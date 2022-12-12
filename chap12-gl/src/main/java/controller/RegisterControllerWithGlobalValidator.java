package controller;

import javax.validation.Valid;
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
@RequestMapping("/global")
public class RegisterControllerWithGlobalValidator {
    
    /*
     * @Valid : 글로벌 범위 Validator가 해당 타입을 검증할 수 있는지 확인
     *          검증 가능하면 실제 검증을 수행하고 그 결과를 Errors에 저장 (요청 처리 메서드 실행 전에 적용)
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
    public String handleStep3(@Valid RegisterRequest regReq, Errors errors) {
        // @Valid : Errors 없으면 검증 실패 시 400 에러 응답
        if (errors.hasErrors()) { // 파라미터로 전달받은 Errors를 이용해 검증 에러가 존재하는지 확인
            return "register/step2";
        }
        
        try {
            memberRegisterService.regist(regReq);
            return "register/step3";
        } catch (DuplicateMemberException ex) {
            errors.rejectValue("email", "duplicate");
            return "register/step2";
        }
    }
    
}
