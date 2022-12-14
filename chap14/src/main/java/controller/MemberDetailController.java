package controller;

import org.springframework.beans.TypeMismatchException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;

@Controller
public class MemberDetailController {
    
    private MemberDao memberDao;
    
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    @GetMapping("/members/{id}") // {id} : 경로 변수 -> @PathVariable 파라미터에 전달 (자동 타입 변환)
    public String detail(@PathVariable("id") Long memId, Model model) {
        Member member = memberDao.selectById(memId);
        if (member == null) {
            throw new MemberNotFoundException();
        }
        model.addAttribute("member", member);
        return "member/memberDetail";
    }
    
    @ExceptionHandler(TypeMismatchException.class) // 해당 컨트롤러에서 발생한 익셉션만 직접 처리
    public String handleTypeMismatchException() { // 에러 응답을 보내는 대신 메서드 실행
        return "member/invalidId"; // 뷰 이름 리턴 가능
    }
    
    @ExceptionHandler(MemberNotFoundException.class)
    public String handleNotFoundException() {
        return "member/noMember";
    }
    
/*
    익셉션 객체에 대한 정보 알고 싶을 때 -> 메서드 파라미터로 익셉션 객체를 전달받아 사용
    
    @ExceptionHandler(TypeMismatchException.class)
    public String handleTypeMismatchException(TypeMismatchException ex) {
        return "member/invalidId";
    }
*/
    
}
