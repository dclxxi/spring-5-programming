package spring;

import java.time.LocalDateTime;

public class MemberRegisterService {
    
    /*
     * DI(Dependency Injection) : 의존 주입
     * 의존 (객체 간 의존) : 한 클래스가 다른 클래스의 메서드 실행 (변경에 의해 영향 받음)
     * ex) MemberRegisterService 클래스가 MemberDao 클래스에 의존
     *
     * 의존 객체를 직접 생성 -> MemberDao 객체가 필요한 클래스 모두 변경 필요 (유지보수 관점에서 문제 유발)
     * 의존 객체를 주입하는 방식 사용 -> 객체를 생성할 때 사용할 클래스를 한 곳만 변경해도 됨
     */
    
    private MemberDao memberDao;
    
    public MemberRegisterService(MemberDao memberDao) { // DI : 의존 객체를 전달 받음 (직접 생성 X)
        this.memberDao = memberDao;
    }
    
    public Long regist(RegisterRequest req) {
        Member member = memberDao.selectByEmail(req.getEmail()); // DB 처리를 위해 MemberDao 클래스의 메서드 사용
        if (member != null) {
            throw new DuplicateMemberException("dup email " + req.getEmail());
        }
        Member newMember = new Member(req.getEmail(), req.getPassword(), req.getName(), LocalDateTime.now());
        memberDao.insert(newMember);
        return newMember.getId();
    }
}
