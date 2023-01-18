package s.m.learning.footballapp.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * define all your important beans here
 */
@Configuration
public class AppConfig {

    @Bean
    public ExecutorService executorService(final FootBallAppProps props){
        return Executors.newFixedThreadPool(Integer.parseInt(props.getThreadPoolSize()));
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplateBuilder().build();
    }
}
