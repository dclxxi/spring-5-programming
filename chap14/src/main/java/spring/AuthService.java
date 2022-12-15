package spring;

public class AuthService {
    
    private MemberDao memberDao;
    
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    public AuthInfo authenticate(String email, String password) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) { // 인증 대상 회원이 존재하지 않는 경우
            throw new WrongIdPasswordException();
        }
        if (!member.matchPassword(password)) { // 비밀번호가 일치하지 않는 경우
            throw new WrongIdPasswordException();
        }
        return new AuthInfo(member.getId(), member.getEmail(), member.getName());
        // 인증 성공 -> 인증 정보를 담고 있는 AuthInfo 객체 리턴
    }
    
}
