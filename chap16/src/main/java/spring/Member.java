package spring;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;

public class Member {
    
    /*
     * LocalDateTime : JSON -> 배열로 바뀜 ex) [2018, 3, 1, 11, 7, 49]
     * Date : JSON -> 유닉스 타임 스탬프 (1970년 1월 1일 이후 흘러간 시간) ex) 1519870069000
     * @JsonFormat(shape = Shape.STRING) : ISO-8601 형식으로 변환 ex) 2018-03-01 11:07:49
     * @JsonFormat(pattern = "yyyyMMddHHmmss") : 원하는 형식으로 변환 ex) 20180301020749
     */
    
    private Long id;
    private String email;
    @JsonIgnore // JSON 응답 결과에 포함 X
    private String password;
    private String name;
    // @JsonFormat(shape = Shape.STRING)
    private LocalDateTime registerDateTime;
    
    public Member(String email, String password, String name, LocalDateTime regDateTime) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.registerDateTime = regDateTime;
    }
    
    public Long getId() {
        return id;
    }
    
    void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public String getName() {
        return name;
    }
    
    public LocalDateTime getRegisterDateTime() {
        return registerDateTime;
    }
    
    public void changePassword(String oldPassword, String newPassword) {
        if (!password.equals(oldPassword)) {
            throw new WrongIdPasswordException();
        }
        this.password = newPassword;
    }
    
    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }
    
}
