package chap07;

public class ExeTimeCalculator implements Calculator {
    
    /*
     * 프록시(proxy) : 핵심 기능의 실행은 다른 객체에 위임하고 부가적인 기능을 제공하는 객체
     * 여러 객체에 공통으로 적용할 수 있는 기능 구현 (핵심 기능 구현 X)
     *
     * ex) 다른 객체에 factorial() 실행 위임하고 부가 기능실행
     *     - 기존 코드 변경 없이 실행 시간 출력 가능
     *     - 실행 시간 구하는 코드 중복 제거 (나노초를 밀리초로 변경 시 ExeTimeCalculator 클래스만 변경하면 됨)
     */
    
    private Calculator delegate;
    
    public ExeTimeCalculator(Calculator delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public long factorial(long num) {
        long start = System.nanoTime();
        long result = delegate.factorial(num);
        long end = System.nanoTime();
        System.out.printf("%s.factorial(%d) 실행 시간 = %d\n", delegate.getClass().getSimpleName(), num, (end - start));
        return result;
    }
    
}
