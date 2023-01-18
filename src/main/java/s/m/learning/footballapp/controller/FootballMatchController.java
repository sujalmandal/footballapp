package s.m.learning.footballapp.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import s.m.learning.footballapp.model.MatchDrawResponse;
import s.m.learning.footballapp.service.FootballService;

import static s.m.learning.footballapp.security.ThreadLocalUserContext.getRoles;
import static s.m.learning.footballapp.security.ThreadLocalUserContext.getUserName;

@RequestMapping("/api/v1/football")
@RestController
@Slf4j
public class FootballMatchController {

    private final FootballService footballService;

    public FootballMatchController(FootballService footballService) {
        this.footballService = footballService;
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @RequestMapping("/draws/{year}")
    @ResponseBody
    public ResponseEntity<?> getDrawMatches(@PathVariable("year") String year){
        log.info("accessed by : {} with roles : {}", getUserName(), getRoles());
        final MatchDrawResponse result = footballService.getDrawMatches(Integer.valueOf(year));
        //result
        return ResponseEntity.ok(result);
    }
}
