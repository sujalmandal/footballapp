package s.m.learning.footballapp.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import s.m.learning.footballapp.config.FootBallAppProps;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
@Slf4j
public class JWTHelper {

    public static final String CLAIM_ATTR_ROLES = "roles";
    public static final String ROLE_AUTHORITY_PREFIX = "ROLE_";

    private final FootBallAppProps footBallAppProps;

    @Value("${spring.application.name}")
    private String appName;

    public JWTHelper(FootBallAppProps footBallAppProps) {
        this.footBallAppProps = footBallAppProps;
    }


    public boolean isJWTValid(String token) {
        try {
            final Date expiryDate = readVerifiedClaim(token, Claims::getExpiration);
            final Date now = new Date();
            return expiryDate.after(now);
        } catch (ExpiredJwtException expiredJwtException){
            log.error("jwt has expired!", expiredJwtException);
            return false;
        }
    }

    public String getUsernameClaim(String token) {
        return readVerifiedClaim(token, Claims::getSubject);
    }

    public <T> T readVerifiedClaim(String token, Function<Claims, T> claimFunc) {
        return claimFunc.apply(readVerifiedClaims(token));
    }

    private Claims readVerifiedClaims(String token) {
        return Jwts.parser()
                .setSigningKey(footBallAppProps.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public String createJWT(UserDetails userDetails) {

        Claims claims = Jwts.claims().setSubject(userDetails.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).map(
                        authRoleStr-> authRoleStr.replaceAll(ROLE_AUTHORITY_PREFIX,"")).toList();
        claims.put(CLAIM_ATTR_ROLES, roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(appName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getExpiryDate(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS256, footBallAppProps.getSecretKey())
                .compact();
    }

    private Date getExpiryDate(long from) {
        return new Date(from+footBallAppProps.getJwtExpiryMinutes()*60*1000);
    }

    /**
     * <b> just a token reader - does not verify the token </b>
     *
     * @param jwt - json web token
     * @return Claims in the jwt
     */
    public JWTClaimsSet readToken(String jwt){
        try{
            return JWTParser.parse(jwt).getJWTClaimsSet();
        } catch (ParseException e){
            log.error("cannot decode jwt : {}", e.getMessage(), e);
            return null;
        }
    }
}
