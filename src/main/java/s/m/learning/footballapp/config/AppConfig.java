package s.m.learning.footballapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class WorkerConfig {

    @Bean
    public ExecutorService executorService(final FootBallAppProps props){
        return Executors.newFixedThreadPool(Integer.parseInt(props.getThreadPoolSize()));
    }
}
