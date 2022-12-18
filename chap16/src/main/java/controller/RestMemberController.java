package controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import spring.DuplicateMemberException;
import spring.Member;
import spring.MemberDao;
import spring.MemberNotFoundException;
import spring.MemberRegisterService;
import spring.RegisterRequest;

@RestController // JSON 형식으로 데이터 응답 (= @Controller + @ResponseBody)
public class RestMemberController {
    
    /*
     * 데이터를 올바르게 처리하기 위한 요청 컨텐츠 타입
     * - JSON 형식 : application/json
     * - 쿼리 문자열 : application/x-www-form-urlencoded -> POST 방식 ex)p1=v1&p2=v2
     */
    
    private MemberDao memberDao;
    private MemberRegisterService registerService;
    
    @GetMapping("/api/members")
    public List<Member> members() {
        return memberDao.selectAll(); // 해당 List 객체를 JSON 형식의 배열로 변환해서 응답
    }
    
    @GetMapping("/api/members/{id}")
    public ResponseEntity<Object> member(@PathVariable Long id) {
        Member member = memberDao.selectById(id);
        if (member == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("no member"));
            // ResponseEntity : 정상/비정상인 경우 모두 JSON 응답 전송 (body로 지정한 객체를 JSON으로 변환 처리)
            // return ResponseEntity.notFound().build(); // 몸체 내용이 없는 경우 (상태 코드 404)
        }
        return ResponseEntity.ok(member); // 상태 코드 200 응답 + member 객체를 JSON으로 변환
    }
    
    @GetMapping("/api/members2/{id}") // 요청 매핑 애노테이션 적용 메서드의 리턴 타입으로 일반 객체 사용
    public Member member2(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Member member = memberDao.selectById(id);
        if (member == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            // HttpServletResponse로 404응답 -> JSON 형식이 아닌 서버가 기본으로 제공하는 HTML을 응답 결과로 제공
            return null;
        }
        return member;
    }
    
    @GetMapping("/api/members3/{id}")
    public Member member3(@PathVariable Long id) {
        Member member = memberDao.selectById(id);
        if (member == null) {
            throw new MemberNotFoundException(); // @ExceptionHandler 사용한 handleNoData()가 에러 처리
        }
        return member; // 회원 데이터 존재 시 JSON으로 변환한 결과 응답
    }
    
    @PostMapping("/api/members") // @RequestBody : JSON 형식으로 전송된 요청 데이터를 커맨드 객체로 전달 받음
    public ResponseEntity<Object> newMember(@RequestBody @Valid RegisterRequest regReq, Errors errors) {
        // @Valid : JSON 형식으로 전송한 데이터를 변환한 객체도 검증 가능
        //          검증 실패 시 상태 코드 400 응답 (Validator 사용 시 직접 상태 코드 처리 필요) -> HTTP 응답
        
        if (errors.hasErrors()) { // JSON 형식 응답 -> Errors 파라미터 추가 후 직접 에러 응답 생성
            String errorCodes = errors.getAllErrors().stream() // getAllErrors : 모든 에러 정보 구함
                    .map(error -> Objects.requireNonNull(error.getCodes())[0])
                    .collect(Collectors.joining(",")); // 각 애러 코드 값을 연결한 문자열 생성
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("errorCodes = " + errorCodes));
        }
        
        try {
            Long newMemberId = registerService.regist(regReq);
            URI uri = URI.create("/api/members/" + newMemberId);
            return ResponseEntity.created(uri).build(); // created : Location 헤더로 전달할 URI 전달
        } catch (DuplicateMemberException dupEx) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
    
    @PostMapping("/api/members2")
    public void newMember2(@RequestBody RegisterRequest regReq, Errors errors, HttpServletResponse response)
            throws IOException {
        try {
            new RegisterRequestValidator().validate(regReq, errors);
            if (errors.hasErrors()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Long newMemberId = registerService.regist(regReq);
            response.setHeader("Location", "/api/members/" + newMemberId); // Location 헤더가 응답 결과에 포함
            response.setStatus(HttpServletResponse.SC_CREATED); // 회원 가입 정상 처리 시 응답 코드 201 전송
        } catch (DuplicateMemberException dupEx) {
            response.sendError(HttpServletResponse.SC_CONFLICT); // 중복된 ID를 전송한 경우 응답 코드 409 리턴
        }
    }
    
    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }
    
    public void setRegisterService(MemberRegisterService registerService) {
        this.registerService = registerService;
    }
}
