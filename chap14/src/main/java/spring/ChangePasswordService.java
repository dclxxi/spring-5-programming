package spring;

import org.springframework.transaction.annotation.Transactional;

public class ChangePasswordService { // 서비스 : 기능 로직 구현
    
    /*
     * 기능별로 서비스 클래스 작성 권장 (1개의 public 메서드 제공)
     * - 한 클래스 코드 길이 -> 일정 수준 안에서 유지 가능
     * - 클래스 코드 길이 길어지면 이후 수정, 확장 어려움
     */
    
    private MemberDao memberDao;
    
    @Transactional // 모든 과정을 성공적으로 진행했을 때 완료 (중간 과정에서 실패 -> 이전 작업 취소)
    public void changePassword(String email, String oldPwd, String newPwd) { // 핵심 로직 (비밀번호 변경)
        Member member = memberDao.selectByEmail(email); // DB에서 비밀번호를 변경할 회원의 데이터 구함
        if (member == null) { // 존재하지 않으면 익셉션 발생
            throw new MemberNotFoundException();
        }
        
        member.changePassword(oldPwd, newPwd); // 회원 데이터의 비밀번호 변경
        
        memberDao.update(member); // 변경 내역을 DB에 반영
    }
    
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
}
