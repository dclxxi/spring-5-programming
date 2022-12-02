package assembler;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberRegisterService;

public class Assembler { // 객체 생성 및 의존 객체 주입 (= 서로 다른 두 객체 조립)
    
    private MemberDao memberDao;
    private MemberRegisterService regSvc;
    private ChangePasswordService pwdSvc;
    
    public Assembler() { // 객체 의존 주입 (의존 객체 변경 시 조립기 코드만 수정하면 됨)
        memberDao = new MemberDao();
        regSvc = new MemberRegisterService(memberDao); // 생성자 주입
        pwdSvc = new ChangePasswordService();
        pwdSvc.setMemberDao(memberDao); // 세터 주입
    }
    
    public MemberDao getMemberDao() { // 특정 객체가 필요한 곳에 객체 제공
        return memberDao;
    }
    
    public MemberRegisterService getMemberRegisterService() {
        return regSvc;
    }
    
    public ChangePasswordService getChangePasswordService() {
        return pwdSvc;
    }
    
}
