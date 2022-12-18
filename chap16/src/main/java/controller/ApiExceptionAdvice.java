package controller;

import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import spring.MemberNotFoundException;

@RestControllerAdvice("controller") // 에러 처리 코드를 별도 클래스로 분리 (JSON, XML 같은 형식으로 변환)
public class ApiExceptionAdvice {
    
    @ExceptionHandler(MemberNotFoundException.class) // 에러 응답 처리 (중복 제거)
    public ResponseEntity<ErrorResponse> handleNoData() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
        // 상태 코드가 404이고 몸체가 JSON 형식인 응답 전송
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @RequestBody를 붙인 경우 @Valid를 붙인 객체 검증이 실패했을 때 Errors 파라미터가 존재하지 않으면 익셉션 발생
    public ResponseEntity<ErrorResponse> handleBindException(MethodArgumentNotValidException ex) {
        String errorCodes = ex.getBindingResult().getAllErrors().stream()
                .map(error -> Objects.requireNonNull(error.getCodes())[0])
                .collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));
    }
    
}
