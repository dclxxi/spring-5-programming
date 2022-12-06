package aspect2;

import java.util.HashMap;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class CacheAspect2 {
    
    private Map<Long, Object> cache = new HashMap<>();
    
    @Around("aspect2.CommonPointcut.commonTarget()") // 다른 클래스 -> 클래스 이름 포함 (같은 패키지 -> 패키지 이름 생략 가능)
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        Long num = (Long) joinPoint.getArgs()[0];
        if (cache.containsKey(num)) {
            System.out.printf("CacheAspect2: Cache에서 구함[%d]\n", num);
            return cache.get(num);
        }
        
        Object result = joinPoint.proceed();
        cache.put(num, result);
        System.out.printf("CacheAspect2: Cache에 추가[%d]\n", num);
        return result;
    }
    
}
