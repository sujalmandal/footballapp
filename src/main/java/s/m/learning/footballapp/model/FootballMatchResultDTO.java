package s.m.learning.footballapp.model;

public class FootballMatchResultDTO {
    private String competition;
    private Integer year;
    private String round;
    private String team1;
    private String team2;
    private String team1goals;
    private String team2goals;
    public String getCompetition() {
        return competition;
    }
    public void setCompetition(String competition) {
        this.competition = competition;
    }
    public Integer getYear() {
        return year;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public String getRound() {
        return round;
    }
    public void setRound(String round) {
        this.round = round;
    }
    public String getTeam1() {
        return team1;
    }
    public void setTeam1(String team1) {
        this.team1 = team1;
    }
    public String getTeam2() {
        return team2;
    }
    public void setTeam2(String team2) {
        this.team2 = team2;
    }
    public String getTeam1goals() {
        return team1goals;
    }
    public void setTeam1goals(String team1goals) {
        this.team1goals = team1goals;
    }
    public String getTeam2goals() {
        return team2goals;
    }
    public void setTeam2goals(String team2goals) {
        this.team2goals = team2goals;
    }
}
