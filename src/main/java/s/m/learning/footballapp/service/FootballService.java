package s.m.learning.footballapp.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StopWatch;
import s.m.learning.footballapp.model.external.FootballMatchResultDTO;
import s.m.learning.footballapp.model.MatchDrawResponse;
import s.m.learning.footballapp.model.external.PaginatedResponse;
import s.m.learning.footballapp.service.external.FootBallMatchExternalService;
import s.m.learning.footballapp.util.CommonUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Predicate;
import java.util.stream.IntStream;

@Service
@Slf4j
public class FootballService {

    private final ExecutorService executorService;
    private final FootBallMatchExternalService footBallMatchExternalService;

    private final static Predicate<FootballMatchResultDTO> isMatchDraw = match -> Objects.equals(match.getTeam1goals(), match.getTeam2goals());

    public FootballService(final ExecutorService executorService, FootBallMatchExternalService footBallMatchExternalService) {
        this.executorService = executorService;
        this.footBallMatchExternalService = footBallMatchExternalService;
    }


    @SneakyThrows
    public MatchDrawResponse getDrawMatches(final Integer year){
        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        final PaginatedResponse<FootballMatchResultDTO> initialQueryResult =
                footBallMatchExternalService.getAllMatchesByYearAndPageInfo(year, 0);
        log.info("initial request made -> result : {}", CommonUtil.toJson(initialQueryResult));
        final Integer totalPages = initialQueryResult.getTotalPages();
        //prepare tasks
        final List<Callable<PaginatedResponse<FootballMatchResultDTO>>> tasks =
                IntStream.range(1, totalPages).mapToObj(page -> (Callable<PaginatedResponse<FootballMatchResultDTO>>)
                () -> footBallMatchExternalService.getAllMatchesByYearAndPageInfo(year, page)).toList();
        log.info("prepared : {} tasks ", tasks.size());
        final List<Future<PaginatedResponse<FootballMatchResultDTO>>> result = executorService.invokeAll(tasks);
        List<FootballMatchResultDTO> finalResult =  result.stream()
                .map(CommonUtil::resolve).map(PaginatedResponse::getData).flatMap(Collection::stream).toList();

        final List<MatchDrawResponse.Draw> draws = compileAllDrawMatches(initialQueryResult.getData(), finalResult);
        //count how much time was taken
        stopWatch.stop();
        return MatchDrawResponse.builder().draws(draws).totalDraws(draws.size())
                .timeTakenInSeconds((int) stopWatch.getTotalTimeSeconds()).build();
    }

    private List<MatchDrawResponse.Draw> compileAllDrawMatches(
            final List<FootballMatchResultDTO> initialQueryResult, final List<FootballMatchResultDTO> finalResult) {
        List<FootballMatchResultDTO> allMatches = new ArrayList<>(initialQueryResult.size()+finalResult.size());
        //draws in the initial data

        allMatches.addAll(initialQueryResult.stream().filter(isMatchDraw).toList());
        allMatches.addAll(finalResult.stream().filter(isMatchDraw).toList());

        return allMatches.stream().map(drawMatch->
                MatchDrawResponse.Draw.builder().team1(drawMatch.getTeam1()).team2(drawMatch.getTeam2())
                        .totalGoals(Integer.valueOf(drawMatch.getTeam1goals())).competition(drawMatch.getCompetition()).build()).toList();
    }


}
