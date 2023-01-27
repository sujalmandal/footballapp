package s.m.learning.footballapp.service.external;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import s.m.learning.footballapp.config.FootBallAppProps;
import s.m.learning.footballapp.model.external.FootballMatchResultDTO;
import s.m.learning.footballapp.model.external.PaginatedResponse;
import s.m.learning.footballapp.util.CommonUtil;

import java.util.Optional;

@Service
@Slf4j
public class FootBallMatchExternalService {

    private final RestTemplate restTemplate;
    private final FootBallAppProps footBallAppProps;

    public FootBallMatchExternalService(final RestTemplate restTemplate, FootBallAppProps footBallAppProps) {
        this.restTemplate = restTemplate;
        this.footBallAppProps = footBallAppProps;
    }

    public PaginatedResponse<FootballMatchResultDTO> getAllMatchesByYearAndPageInfo(Integer year, Integer pageNo){
        final String fullyQualifiedURL = footBallAppProps.getFootballApiBaseurl().formatted(year, pageNo);
        log.debug("request outbound on : {}", fullyQualifiedURL);
        final String rawResponse = restTemplate.getForObject(fullyQualifiedURL, String.class);
        return Optional.ofNullable(rawResponse).map(json-> CommonUtil.fromJson(json,
                new TypeReference<PaginatedResponse<FootballMatchResultDTO>>() {})).orElse(null);
    }
}
