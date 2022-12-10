package survey;

import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/survey") // 각 메서드에 공통되는 경로
public class SurveyController {
    
    @GetMapping
    public String form(Model model) { // 요청 매핑 애노테이션이 적용된 메서드의 파라미터에 Model 추가
        List<Question> questions = createQuestions();
        model.addAttribute("questions", questions); // 뷰가 응답 화면을 구성하는데 필요한 데이터를 생성해서 전달
        // ModelAndView 클래스의 addObject()로 모델 데이터 추가 가능
        return "survey/surveyForm";
        // ModelAndView 클래스의 setViewNAme()를 이용해서 뷰 이름 지정 가능
    }
    
    private List<Question> createQuestions() {
        Question q1 = new Question("당신의 역할은 무엇입니까?", Arrays.asList("서버", "프론트", "풀스택"));
        Question q2 = new Question("많이 사용하는 개발도구는 무엇입니까?", Arrays.asList("이클립스", "인텔리J", "서브라임"));
        Question q3 = new Question("하고 싶은 말을 적어주세요.");
        return Arrays.asList(q1, q2, q3);
    }
    
    @PostMapping
    public String submit(@ModelAttribute("ansData") AnsweredData data) { // Model 대신 커맨드 객체 사용 가능
        // @ModelAttribute : 입력 폼과 폼 전송 처리에서 사용할 커맨드 객체의 속성 이름 명시적 지정
        return "survey/submitted";
    }
    
}
