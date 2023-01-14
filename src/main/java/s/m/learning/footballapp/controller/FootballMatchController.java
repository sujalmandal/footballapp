package s.m.learning.footballapp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/football")
@RestController
public class FootballMatchController {
    Logger logger = LoggerFactory.getLogger(FootballMatchController.class);

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @RequestMapping("/draws/{year}")
    public ResponseEntity<?> getDrawMatches(@PathVariable("year") String year){
        logger.info("access by : {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return ResponseEntity.ok(year);
    }
}
