package spring;

public class AuthService { // 이메일과 비밀번호가 일치하는지 확인해서 AuthInfo 객체 생성
    
    private MemberDao memberDao;
    
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    public AuthInfo authenticate(String email, String password) {
        Member member = memberDao.selectByEmail(email);
        if (member == null) {
            throw new WrongIdPasswordException();
        }
        if (!member.matchPassword(password)) {
            throw new WrongIdPasswordException();
        }
        return new AuthInfo(member.getId(), member.getEmail(), member.getName());
    }
    
}
