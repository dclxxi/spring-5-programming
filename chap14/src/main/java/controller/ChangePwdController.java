package controller;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.AuthInfo;
import spring.ChangePasswordService;
import spring.WrongIdPasswordException;

@Controller
@RequestMapping("/edit/changePassword")
public class ChangePwdController {
    
    /*
     * 컨트롤러 : 실제 웹 브라우저 요청 처리 (클라이언트 요청 처리하기 위해 알맞은 기능 실행 -> 뷰에 결과 전달)
     * - 클라이언트가 요구한 기능 실행
     * - 응답 결과를 생성하는데 필요한 모델 생성
     * - 응답 결과를 생성할 뷰 선택
     */
    
    private ChangePasswordService changePasswordService;
    
    public void setChangePasswordService(ChangePasswordService changePasswordService) {
        this.changePasswordService = changePasswordService;
    }
    
    @GetMapping
    public String form(@ModelAttribute("command") ChangePwdCommand pwdCmd) {
        return "edit/changePwdForm";
    }
    
    @PostMapping
    public String submit(@ModelAttribute("command") ChangePwdCommand pwdCmd, Errors errors, HttpSession session) {
        new ChangePwdCommandValidator().validate(pwdCmd, errors);
        if (errors.hasErrors()) {
            return "edit/changePwdForm";
        }
        AuthInfo authInfo = (AuthInfo) session.getAttribute("authInfo");
        try {
            changePasswordService.changePassword(authInfo.getEmail(), pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword()); // 로직 실행을 서비스에 위임 (직접 수행 X)
            // 웹 요청 파라미터를 커맨드 객체로 받고 프로퍼티를 서비스 메서드에 인자로 전달 가능
            return "edit/changedPwd";
        } catch (WrongIdPasswordException e) {
            errors.rejectValue("currentPassword", "notMatching");
            return "edit/changePwdForm";
        }
    }
}
