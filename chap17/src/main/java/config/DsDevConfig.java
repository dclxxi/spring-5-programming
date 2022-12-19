package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev") // 프로필 지정 (스프링 컨테이너를 초기화할 때 "dev" 프로필 활성화 -> DsDevConfig 클래스를 설정으로 사용)
public class DsDevConfig {
    
    /*
     * 프로필(profile) : 개발 목적 설정과 실 서비스 목적의 설정 구분해서 작성
     *                  논리적 이름 -> 설정 집합에 프로필 지정 가능
     *
     * 스프링 컨테이너 : 설정 집합 중 지정한 이름을 사용하는 프로필 선택 후 해당 프로필에 속한 설정을 이용해 컨테이너 초기화
     */
    
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        DataSource ds = new DataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost/spring5fs?characterEncoding=utf8");
        ds.setUsername("spring5");
        ds.setPassword("spring5");
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(60000 * 3);
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
        return ds;
    }
}
