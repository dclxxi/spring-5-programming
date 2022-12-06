package aspect2;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut { // 별도 클래스 분리 -> Pointcut 재사용 가능 (빈 등록 X)
    
    @Pointcut("execution(public * chap07..*(..))")
    public void commonTarget() { // public : 다른 클래스에 위치한 @Around에서 commonTarget()의 Pointcut 사용 가능
    }
    
}
