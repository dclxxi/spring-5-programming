package controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import spring.RegisterRequest;

public class RegisterRequestValidator implements Validator { // Validator : 객체 검증
    
    private static final String emailRegExp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private Pattern pattern;
    
    public RegisterRequestValidator() {
        pattern = Pattern.compile(emailRegExp);
        System.out.println("RegisterRequestValidator#new(): " + this);
    }
    
    @Override // Validator가 검증할 수 있는 타입인지 검사 (스프링 MVC가 자동으로 검증 기능을 수행하도록 설정하려면 올바르게 구현 필요)
    public boolean supports(Class<?> clazz) {// 파리미터로 전달받은 객체가 RegisterRequest 클래스로 타입 변환이 가능한지 확인
        return RegisterRequest.class.isAssignableFrom(clazz);
    }
    
    @Override // 첫 번째 파라미터로 전달받은 객체를 검증하고 오류 결과를 Errors에 담는 기능 정의
    public void validate(Object target, Errors errors) { // target : 검사 대상 객체, errors : 검사 결과 에러 코드 설정 객체
        System.out.println("RegisterRequestValidator#validate(): " + this);
        RegisterRequest regReq = (RegisterRequest) target; // 실제 타입으로 변환
        if (regReq.getEmail() == null || regReq.getEmail().trim().isEmpty()) { // 프로퍼티 값이 존재하지 않음
            errors.rejectValue("email", "required"); // email : 프로퍼티 이름, required : 에러 코드
        } else { // 프로퍼티 값이 존재하면
            Matcher matcher = pattern.matcher(regReq.getEmail()); // 정규 표현식을 이용해 올바른지 확인
            if (!matcher.matches()) { // 정규 표현식이 일치하지 않음
                errors.rejectValue("email", "bad"); // 에러 코드로 "bad" 추가
            }
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "required");
        // 프로퍼티가 null이거나 빈 문자열이거나 공백문자로만 되어 있는 경우 에러 코드 추가
        ValidationUtils.rejectIfEmpty(errors, "password", "required");
        // 프로퍼티가 null이거나 빈 문자열인 경우 에러 코드 추가
        ValidationUtils.rejectIfEmpty(errors, "confirmPassword", "required");
        if (!regReq.getPassword().isEmpty()) {
            if (!regReq.isPasswordEqualToConfirmPassword()) {
                errors.rejectValue("confirmPassword", "nomatch");
            }
        }
    }
    
}
