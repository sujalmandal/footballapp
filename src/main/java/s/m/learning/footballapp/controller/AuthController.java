package s.m.learning.footballapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import s.m.learning.footballapp.model.CredentialDTO;
import s.m.learning.footballapp.security.JWTHelper;

import java.text.ParseException;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {

	private final JWTHelper jwtHelper;
	private final UserDetailsManager userCredRepo;
	private final BCryptPasswordEncoder passwordEncoder;

	public AuthController(JWTHelper jwtHelper, UserDetailsManager userCredRepo, BCryptPasswordEncoder passwordEncoder) {
		this.jwtHelper = jwtHelper;
		this.userCredRepo = userCredRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Secured("ROLE_PUBLIC")
	@PostMapping("/get-token")
	public ResponseEntity<?> authAndGetJWT(@RequestBody CredentialDTO credentialDTO) {
		UserDetails user = userCredRepo.loadUserByUsername(credentialDTO.getUsername());
		if(passwordEncoder.matches(credentialDTO.getPassword(), user.getPassword())){
			return ResponseEntity.ok(jwtHelper.createJWT(user));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}


	@Secured({"ROLE_ADMIN"})
	@RequestMapping("/introspect-token")
	public ResponseEntity<?> getUserDetail(@RequestBody CredentialDTO credentialDTO) throws ParseException {
		return ResponseEntity.ok(jwtHelper.readToken(credentialDTO.getJwt()));
	}
}
