package controller;

public class ErrorResponse { // 에러 상황일 때 응답으로 사용
    
    private String message;
    
    public ErrorResponse(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
}
