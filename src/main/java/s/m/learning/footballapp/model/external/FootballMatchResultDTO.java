package s.m.learning.footballapp.model.external;

import lombok.Data;

@Data
public class FootballMatchResultDTO {
    private String competition;
    private Integer year;
    private String round;
    private String team1;
    private String team2;
    private String team1goals;
    private String team2goals;
}
