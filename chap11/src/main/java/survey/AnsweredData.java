package survey;

import java.util.List;

public class AnsweredData {
    
    /*
     * HTTP 요청 파라미터 이름에 따라 커맨드 객체에 설정
     * - 프로퍼티이름[인덱스] : List 타입 프로퍼티 값 목록으로 처리
     * - 프로퍼티이름.프로퍼티이름 : 중첩 프로퍼티 값 처리
     */
    
    private List<String> responses;
    private Respondent res; // 중첩 프로퍼티
    
    public List<String> getResponses() {
        return responses;
    }
    
    public void setResponses(List<String> responses) {
        this.responses = responses;
    }
    
    public Respondent getRes() {
        return res;
    }
    
    public void setRes(Respondent res) {
        this.res = res;
    }
    
}
