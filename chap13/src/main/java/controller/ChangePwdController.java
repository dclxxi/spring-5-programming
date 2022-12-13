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
public class ChangePwdController { // 비밀번호 변경 요청 처리
    
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
        // 현재 로그인한 사용자 정보 (서버 재시작 시 세션 정보 유지 X -> null 리턴)
        try {
            changePasswordService.changePassword(authInfo.getEmail(), pwdCmd.getCurrentPassword(),
                    pwdCmd.getNewPassword());
            return "edit/changedPwd";
        } catch (WrongIdPasswordException e) {
            errors.rejectValue("currentPassword", "notMatching");
            return "edit/changePwdForm";
        }
    }
}
