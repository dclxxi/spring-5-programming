package chap09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // 스프링 MVC에서 컨트롤러로 사용
public class HelloController {
    
    /*
     * 컨트롤러(Controller) : 웹 요청을 처리하고 그 결과를 뷰에 전달하는 스프링 빈 객체
     * 요청 매핑 애노테이션 -> 처리할 경로 지정 (@GetMapping, @PostMapping)
     */
    
    @GetMapping("/hello") // 메서드가 처리할 요청 경로 지정 (서블릿 컨텍스트 경로 기준)
    public String hello(Model model, @RequestParam(value = "name", required = false) String name) {
        // Model : 컨트롤러의 처리 결과를 뷰에 전달할 때 사용
        // @RequestParam : HTTP 요청 파라미터의 값을 메서드 파라미터로 전달
        //                 (value : HTTP 요청 파라미터 이름 지정, required : 필수 여부 지정)
        model.addAttribute("greeting", "안녕하세요, " + name); // 모델 속성에 값 설정
        return "hello"; // 컨트롤러의 처리 결과를 보여줄 뷰 이름 리턴
    }
}
