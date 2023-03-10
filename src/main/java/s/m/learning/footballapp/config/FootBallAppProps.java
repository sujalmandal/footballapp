package s.m.learning.footballapp.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("footballapp")
@Getter
@Setter
public class FootBallAppProps {

    private String footballApiBaseurl;
    private String threadPoolSize;
    private Integer jwtExpiryMinutes;
    private String secretKey;

}
