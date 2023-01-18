package s.m.learning.footballapp.security;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JWTHelper {

    private Logger logger = LoggerFactory.getLogger(JWTHelper.class);

    private static final String SECRET_KEY = "my_little_secret";
    private static final int TEN_MINUTES = 10*60*1000;
    public static final String CLAIM_ATTR_ROLES = "roles";
    public static final String ROLE_AUTHORITY_PREFIX = "ROLE_";

    @Value("${spring.application.name}")
    private String appName;

    public boolean isJWTValid(String token) {
        try {
            final Date expiryDate = readVerifiedClaim(token, Claims::getExpiration);
            final Date now = new Date();
            return expiryDate.after(now);
        } catch (ExpiredJwtException expiredJwtException){
            logger.error("jwt has expired!", expiredJwtException);
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
                .setSigningKey(SECRET_KEY)
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
                .setExpiration(new Date(System.currentTimeMillis() + TEN_MINUTES))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
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
            logger.error("cannot decode jwt : {}", e.getMessage(), e);
            return null;
        }
    }
}
