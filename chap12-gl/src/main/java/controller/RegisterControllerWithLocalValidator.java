package controller;

import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import spring.DuplicateMemberException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@Controller
@RequestMapping("/local")
public class RegisterControllerWithLocalValidator {
    
    /*
    * WebDataBinder : 내부적으로 Validator 목록 가짐 (글로벌 범위 Validator 기본으로 포함)
    * - setValidator() : 컨트롤러 범위 Validator 사용 (글로벌 범위 Validator X)
    *                    WebDataBinder가 갖고 있는 Validator를 목록에서 삭제하고 파라미터로 전달받은 Validator를 목록에 추가
    * - addValidators() : 글로벌 범위 Validator 뒤에 새로 추가할 컨트롤러 범위 Validator 추가
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
        // initBinder() -> 검증 시 RegisterRequestValidator 사용
        if (errors.hasErrors()) {
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
    
    @InitBinder // 컨트롤러 범위 Validator 설정 가능 (어떤 Validator가 커맨드 객체를 검증할지)
    protected void initBinder(WebDataBinder binder) { // 컨트롤러 요청 처리 메서드 실행 전 매번 실행 (WebDataBinder 초기화)
        binder.addValidators(new RegisterRequestValidator());
    }
    
}
