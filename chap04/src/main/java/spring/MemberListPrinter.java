package spring;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberListPrinter {
    
    private MemberDao memberDao;
    private MemberPrinter printer;
    
    public MemberListPrinter() { // AppCtx 클래스에서 기본 생성자를 이용해 객체를 생성하기 위해
    }
    
    public MemberListPrinter(MemberDao memberDao, MemberPrinter printer) {
        this.memberDao = memberDao;
        this.printer = printer;
    }
    
    public void printAll() {
        Collection<Member> members = memberDao.selectAll();
        members.forEach(m -> printer.print(m));
    }
    
    @Autowired
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    @Autowired // MemberSummaryPrinter 타입 빈은 1개만 존재 -> MemberSummaryPrinter 빈 자동 주입
    public void setMemberPrinter(MemberSummaryPrinter printer) {
        this.printer = printer;
    }
    
}
