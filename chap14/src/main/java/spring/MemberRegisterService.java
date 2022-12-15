package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    
    /*
     * 커맨드 클래스 : 스프링 MVC가 제공하는 폼 값 바인딩, 검증, 스프릥 폼 태그와의 연동 기능 사용 가능
     * */
    
    private MemberDao memberDao;
    
    public MemberRegisterService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    public Long regist(RegisterRequest req) { // 데이터 전달받기 위해 별도 타입 만듦 -> 스프링 MVC 커맨드 객체로 사용 가능
        Member member = memberDao.selectByEmail(req.getEmail());
        if (member != null) {
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }
        Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
