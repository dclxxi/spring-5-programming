package spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MemberInfoPrinter {
    
    private MemberDao memDao;
    private MemberPrinter printer;
    
    public void printMemberInfo(String email) {
        Member member = memDao.selectByEmail(email);
        if (member == null) {
            System.out.println("데이터 없음\n");
            return;
        }
        printer.print(member);
        System.out.println();
    }
    
    @Autowired // 빈 객체의 메서드 -> 해당 메서드 호출 후 파라미터 타입에 해당하는 빈 객체를 찾아 인자로 주입
    public void setMemberDao(MemberDao memberDao) {
        this.memDao = memberDao;
    }
    
    @Autowired // MemberPrinter 타입의 빈을 자동 주입
    @Qualifier("printer") // 한정 값이 printer인 빈을 의존 주입 후보로 사용
    public void setPrinter(MemberPrinter printer) {
        this.printer = printer;
    }
    
}
