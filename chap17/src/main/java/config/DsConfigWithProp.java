package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DsConfigWithProp {
    
    @Value("${db.driver}") // PropertySourcesPlaceholderConfigurer가 플레이스홀더의 값을 일치하는 프로퍼티 값으로 치환
    private String driver;
    @Value("${db.url}")
    private String jdbcUrl;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;
    
    @Bean(destroyMethod = "close")
    public DataSource dataSource() { // 실제 빈을 생성하는 메서드 (@Value 필드를 통해 해당 프로퍼티 값 사용 가능)
        DataSource ds = new DataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(jdbcUrl);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setInitialSize(2);
        ds.setMaxActive(10);
        ds.setTestWhileIdle(true);
        ds.setMinEvictableIdleTimeMillis(60000 * 3);
        ds.setTimeBetweenEvictionRunsMillis(10 * 1000);
        return ds;
    }
    
}
