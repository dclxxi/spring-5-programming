package spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements InitializingBean, DisposableBean {
    
    /*
     * 초기화 : InitializingBean 인터페이스 구현 -> afterPropertiesSet() 메서드 실행
     * 소멸 : DisposableBean 인터페이스 구현 -> destroy() 메서드 실행
     * */
    
    private String host;
    
    public void setHost(String host) {
        this.host = host;
    }
    
    @Override // 가장 먼저 실행 (빈 객체 생성 마무리한 뒤 초기화 메서드 실행)
    public void afterPropertiesSet() throws Exception {
        System.out.println("Client.afterPropertiesSet() 실행");
    }
    
    public void send() {
        System.out.println("Client.send() to " + host);
    }
    
    @Override // 가장 마지막에 실행 (스프링 컨테이너 종료 시 호출)
    public void destroy() throws Exception {
        System.out.println("Client.destroy() 실행");
    }
    
}
