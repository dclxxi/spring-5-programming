package aspect;

import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // Aspect로 사용할 클래스 (공통 기능 제공)
// @Order(1) // 적용 순서 변경 -> 첫 번째
public class ExeTimeAspect {
    
    /*
     * AOP(Aspect Oriented Programming) : 여러 객체에 공통으로 적용할 수 있는 기능을 분리해서 재사용성을 높여주는 프로그래밍 기법
     * 핵심 기능과 공통 기능의 구현 분리 (핵심 기능을 구현한 코드의 수정 없이 핵심 기능에 공통 기능 삽입)
     *
     * 스프링 AOP : 런타임에 프록시 객체를 생성해서 공통 기능 삽입 (상위 타입의 인터페이스를 상속받은 프록시 클래스 직접 구현 X)
     */
    
    /*
     * execution : Advice 적용할 메서드 지정
     * execution(<수식어패턴>? <리턴타입패턴> <클래스이름패턴>?<메서드이름패턴>(<파라미터패턴>))
     * - 수식어패턴 : 생략 가능 ex) public -> 스프링 AOP는 public에만 적용 가능
     * - 파라미터패턴 : 매칭될 파라미터에 대해 명시 ex) Integer
     *
     * '*' : 모든 값 표현
     * '..' : 0개 이상
     */
    
    @Pointcut("execution(public * chap07..*(..))") // 공통 기능을 적용할 대상 설정
    // (chap07 패키지와 그 하위 패키지에 위치한 타입의 public 메서드를 Pointcut으로 설정)
    private void publicTarget() {
    }
    
    @Around("publicTarget()") // 공통 기능을 구현한 메서드 (Around Advice 설정 -> 메서드 실행 전/후에 사용)
    // (publicTarget() 메서드에 정의한 Pointcut에 공통 기능 적용)
    public Object measure(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime(); // 공통 기능을 위한 코드
        try {
            Object result = joinPoint.proceed(); // ProceedingJoinPoint : 프록시 대상 객체의 메서드 호출
            return result;
        } finally {
            long finish = System.nanoTime();  // 공통 기능을 위한 코드
            Signature sig = joinPoint.getSignature();
            System.out.printf("%s.%s(%s) 실행 시간 : %d ns\n", joinPoint.getTarget().getClass().getSimpleName(),
                    sig.getName(), Arrays.toString(joinPoint.getArgs()), (finish - start));
        }
    }
    
}
