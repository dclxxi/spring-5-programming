package spring;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class RegisterRequest {
    
    /*
     * Bean Validation : @Valid, @NotNull, @Digits, @Size 등 애노테이션 정의 (Validator 작성 없이 커맨드 객체 값 검증 처리 가능)
     *
     * <1.1v>
     * @AssertTrue, @AssertFalse : 값이 ture/flase인지 검사 (null : 유효)
     * @DecimalMax, @DecimalMin : 지정한 값보다 작거나/크거나 같은지 검사
     * @Max, @Min : 지정한 값보다 작거나/크거나 같은지 검사 (null : 유효)
     * @Digits : 자릿수가 지정한 크기를 넘지 않는지 검사 (null : 유효)
     * @Size : 길이나 크기가 지정한 값 범위에 있는지 검사 (null : 유효)
     * @Null, @NotNull : 값이 null인지/아닌지 검사
     * @Pattern : 값이 정규표현식에 익치하는지 검사 (null : 유효)
     *
     * <2.0v>
     * @NotEmpty : 문자열/배열 - null이 아니고 길이가 0이 아닌지 검사
     *             콜렉션 - null이 아니고 크기가 0이 아닌지 검사
     * @NotBlank : null이 아니고 최소 1개 이상의 공백아닌 문자를 포함하는지 검사
     * @Positive, @PositiveOrZero : 양수(또는 0)인지 검사 (null : 유효)
     * @Negative, @NegativeOrZero : 음수(또는 0)인지 검사 (null : 유효)
     * @Email : 이메일 주소가 유효한지 검사 (null : 유효)
     * @Future, @FutureOrPresent : 해당 시간이 미래 시간(또는 현재)인지 검사 (null : 유효)
     * @Past, @PastOrPresent : 해당 시간이 과거 시간(또는 현재)인지 검사 (null : 유효)
     */
    
    @NotBlank // 기본 에러 메시지 : 반드시 값이 존재하고 공백 문자를 제외한 길이가 0보다 커야 합니다.
    @Email
    private String email;
    @Size(min = 6) // 기본 에러 메시지 : 반드시 최소값 6과(와) 최대값 2147483647 사이의 크기이어야 합니다.
    private String password;
    @NotEmpty // 기본 에러 메시지 : 반드시 값이 존재하고 길이 혹은 크기가 0보다 커야 합니다.
    private String confirmPassword;
    @NotEmpty
    private String name;
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isPasswordEqualToConfirmPassword() {
        return password.equals(confirmPassword);
    }
}
