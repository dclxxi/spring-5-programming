package spring;

import org.springframework.beans.factory.annotation.Value;

public class Info { // 빈으로 사용할 클래스에도 @Value 사용 가능
    
    private String version;
    
    public void printInfo() {
        System.out.println("version = " + version);
    }
    
    @Value("${info.version}") // 메서드에도 @Value 적용 가능
    public void setVersion(String version) {
        this.version = version;
    }
    
}
