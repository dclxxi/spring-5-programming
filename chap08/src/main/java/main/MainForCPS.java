package main;

import config.AppCtx;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.ChangePasswordService;
import spring.MemberNotFoundException;
import spring.WrongIdPasswordException;


public class MainForCPS {
    
    /*
     * JdbcTemplate : DB 연동 과정에 문제 -> DtaAccessException 발생 (RuntimeException 상속) => 트랜잭션 롤백
     *
     * @Transaction(rollbackFor = SQLException.class) // SQLException : RuntimeException 상속 X
     * (<-> noRollbackFor : 익셉션이 발생해도 커밋할 익셉션 타입 지정)
     */
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
        
        ChangePasswordService cps = ctx.getBean("changePwdSvc", ChangePasswordService.class);
        // 트랜잭션 처리를 위해 생성한 프록시 객체 리턴 (ChangePasswordService 객체 X)
        // @Transaction 붙은 메서드 호출 -> PlatformTransactionManager 사용해서 트랜잭션 시작 -> 실제 객체의 메서드 호출 -> 커밋
        try {
            cps.changePassword("madvirus@madvirus.net", "1234", "1111");
            System.out.println("암호를 변경했습니다.");
        } catch (MemberNotFoundException e) {
            System.out.println("회원 데이터가 존재하지 않습니다.");
        } catch (WrongIdPasswordException e) { // 프록시 객체 : RuntimeException -> 트랜잭션 롤백
            System.out.println("암호가 올바르지 않습니다.");
        }
        
        ctx.close();
        
    }
}
