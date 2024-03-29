package spring;

public class AuthInfo { // 로그인 성공 후 인증 상태 정보를 세션에 보관할 때 사용
    
    private Long id;
    private String email;
    private String name;
    
    public AuthInfo(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
}
