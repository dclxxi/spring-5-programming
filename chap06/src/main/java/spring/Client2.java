package spring;

public class Client2 {
    
    /*
     * 스프링 설정에서 직접 메서드 지정 가능 (InitializingBean, DisposableBean 인터페이스 사용 X)
     */
    
    private String host;
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public void connect() {
        System.out.println("Client2.connect() 실행");
    }
    
    public void send() {
        System.out.println("Client2.send() to " + host);
    }
    
    public void close() {
        System.out.println("Client2.close() 실행");
    }
    
}
