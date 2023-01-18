package s.m.learning.footballapp.service;

import org.springframework.stereotype.Service;
import s.m.learning.footballapp.model.MatchDrawResponse;

import java.util.concurrent.ExecutorService;

@Service
public class FootballMatchService {
    private final ExecutorService executorService;

    public FootballMatchService(final ExecutorService executorService) {
        this.executorService = executorService;
    }

    public MatchDrawResponse getDrawMatches(final Integer year){
        
    }
}
