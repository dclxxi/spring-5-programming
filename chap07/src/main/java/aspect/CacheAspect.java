package aspect;

import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
//@Order(2) // 적용 순서 변경 -> 2번째
public class CacheAspect {
    
    private Map<Long, Object> cache = new HashMap<>();
    
    @Pointcut("execution(public * chap07..*(long))") // 첫 번째 인자가 long인 메서드 대상 ex) Calculator factorial(long)
    public void cacheTarget() {
    }
    
    @Around("cacheTarget()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Long num = (Long) joinPoint.getArgs()[0]; // 첫 번째 인자를 Long 타입으로 구함
        if (cache.containsKey(num)) {
            System.out.printf("CacheAspect: Cache에서 구함[%d]\n", num);
            return cache.get(num); // 키에 해당하는 값 리턴
        }
        
        Object result = joinPoint.proceed();  // 프록시 대상 객체 실행 -> ExeTimeAspect measure() 실행
        cache.put(num, result); // 프록시 대상 객체 실행 결과 cache에 추가
        System.out.printf("CacheAspect: Cache에 추가[%d]\n", num);
        return result; // 프록시 대상 객체 실행 결과 리턴
    }
    
}
