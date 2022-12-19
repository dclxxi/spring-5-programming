package main;

import config.DsDevConfig;
import config.DsRealConfig;
import config.MemberConfig;
import java.util.List;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.Member;
import spring.MemberDao;

public class MainProfile {
    
    /*
     * 프로필 사용 시 주의점 : 설정 정보를 전달하기 전 어떤 프로필을 사용할지 지정해야 함
     *                      (프로필 선택 전 설정 정보를 먼저 전달 -> 설정을 읽어오는 과정에서 빈을 찾지 못해 익셉션 발생)
     */
    
    /*
     * @Configuration 설정에서 프로필 선택 방법 (우선순위 순)
     * 1. setActiveProfiles() 사용
     * 2. 자바 시스템 프로퍼티에 사용할 프로필 값 지정
     *    - 명령행에서 -D 옵션 이용 ex) java -Dspring.profiles.active=dev main.Main
     *    - System.setProperty() 이용 ex) System.setProperty("spring.profiles.active", "dev")
     * 3. OS의 "spring.profiles.active" 환경 변수에 값 설정
     */
    
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev"); // ex) "dev" 프로필에 속하는 "dataSource" 빈 사용
        // getEnvironment() : 스프링 실행 환경을 설정하는데 사용되는 Environment 리턴
        // setActiveProfiles() : 컨테이너 초기화 전에 사용할 프로필 선택
        context.register(MemberConfig.class, DsDevConfig.class, DsRealConfig.class); // 설정 파일 목록 지정
        //context.register(MemberConfigWithProfile.class);
        context.refresh(); // 컨테이너 초기화
        
        MemberDao dao = context.getBean(MemberDao.class);
        List<Member> members = dao.selectAll();
        members.forEach(m -> System.out.println(m.getEmail()));
        
        context.close();
    }
}
