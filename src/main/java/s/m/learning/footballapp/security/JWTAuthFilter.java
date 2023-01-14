package s.m.learning.footballapp.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthFilter extends OncePerRequestFilter {

    public static final String SPACE = " ";
    public static final List<String> WHITELISTED_URIS = List.of("/api/v1/auth/get-token");

    private final InMemoryUserDetailsManager inMemoryUserDetailsManager;
     private final JWTHelper jwtHelper;

    public JWTAuthFilter(InMemoryUserDetailsManager inMemoryUserDetailsManager, JWTHelper jwtHelper) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
        this.jwtHelper = jwtHelper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        boolean isPublicResource = WHITELISTED_URIS.stream()
                .anyMatch(whiteListedURI -> request.getRequestURI().endsWith(whiteListedURI));

        if(isPublicResource){
            //set public security context
            setupSecurityContext(request, User.builder().username("anonymous").password("").roles("PUBLIC").build());
            filterChain.doFilter(request, response);
            return;
        }

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        //no header found
        if (header == null || header.trim().length()==0 || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(SPACE)[1].trim();
        if (!jwtHelper.isJWTValid(token)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // use in-memory user details
        UserDetails userDetails = inMemoryUserDetailsManager
                .loadUserByUsername(jwtHelper.getUsername(token));

        //inject authenticated user details in the security context
        setupSecurityContext(request, userDetails);

        filterChain.doFilter(request, response);
    }

    private void setupSecurityContext(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails == null ? List.of() : userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
