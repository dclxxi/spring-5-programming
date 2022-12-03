package spring;

import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

public class MemberPrinter {
    
    /*
     * @Autowired(required = false) : 대상 빈이 존재하지 않으면 세터 메서드 호출 X (null 값 할당 X)
     * @Nullable : 자동 주입할 빈이 존재하지 않아도 메서드 호출 O (null 값 할당 O)
     * Optional : 매칭되는 빈 없으면 값이 없는 Optional 할당
     */
    
    private DateTimeFormatter dateTimeFormatter;
    
    public MemberPrinter() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    }
    
    public void print(Member member) {
        if (dateTimeFormatter == null) { // null이어도 동작 -> 빈이 존재하지 않을 때 익셉션 발생 필요 X
            System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%tF\n", member.getId(), member.getEmail(),
                    member.getName(), member.getRegisterDateTime());
        } else {
            System.out.printf("회원 정보: 아이디=%d, 이메일=%s, 이름=%s, 등록일=%s\n", member.getId(), member.getEmail(),
                    member.getName(), dateTimeFormatter.format(member.getRegisterDateTime()));
        }
    }
    
    @Autowired(required = false) // 자동 주입할 대상이 필수가 아닌 경우 (매칭되는 빈 존재 X -> 익셉션 발생 X, 자동 주입 수행 X)
    public void setDateFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }

/*
    Optional : required 대신 의존 주입 대상에 사용 가능

	@Autowired
	public void setDateFormatter(Optional<DateTimeFormatter> formatterOpt) {
		if (formatterOpt.isPresent()) {
			this.dateTimeFormatter = formatterOpt.get();
		} else {
			this.dateTimeFormatter = null;
		}
	}
*/

/*
    @Nullable : 필수 여부 지정 (의존 주입 대상 파라미터)
    - 자동 주입할 빈 존재 -> 해당 빈을 인자로 전달
    - 자동 주입할 존재 X -> 인자로 null 전달

	@Autowired
	public void setDateFormatter(@Nullable DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
*/

}
