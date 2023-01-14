package s.m.learning.footballapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfiguration{

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JWTAuthFilter jwtAuthFilter) throws Exception {
		return http.csrf(csrf-> csrf.disable())
				.sessionManagement(sessionMgmt-> sessionMgmt.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	public JWTAuthFilter jwtAuthFilter(
			InMemoryUserDetailsManager inMemoryUserDetailsManager, JWTHelper jwtHelper) {
		return new JWTAuthFilter(inMemoryUserDetailsManager, jwtHelper);
	}

	/**
	 * some hard coded users
	 *
	 * @param passwordEncoder
	 * @return
	 */
	@Bean
	public InMemoryUserDetailsManager userDetailsService(final BCryptPasswordEncoder passwordEncoder) {
		UserDetails user = User.builder().username("user").password("user123")
				.roles("USER").passwordEncoder(passwordEncoder::encode).build();

		UserDetails admin = User.builder().username("admin").password("admin123")
				.roles("ADMIN").passwordEncoder(passwordEncoder::encode).build();
		return new InMemoryUserDetailsManager(user, admin);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}


}
