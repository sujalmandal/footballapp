package s.m.learning.footballapp.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
public class MatchDrawResponse {

    private int totalDraws;
    private int year;
    private int timeTakenInSeconds;

    private List<Draw> draws;

    @Builder
    @Getter
    public static class Draw{
        private String team1;
        private String team2;
        private Integer totalGoals;
        private String competition;
    }
}
