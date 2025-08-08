package net.javaguides.ems_backend.Controller;

import net.javaguides.ems_backend.Entity.User;
import net.javaguides.ems_backend.Mapper.Config.JwtUtil;
import net.javaguides.ems_backend.dto.JwtResponse;
import net.javaguides.ems_backend.dto.LoginRequest;
import net.javaguides.ems_backend.dto.SignupRequest;
import net.javaguides.ems_backend.repository.UserRepository;
import net.javaguides.ems_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
		try {
			if (userService.existsByUsername(signupRequest.getUsername())) {
				return ResponseEntity.badRequest()
						.body("Error: Username is already taken!");
			}

			if (userService.existsByEmail(signupRequest.getEmail())) {
				return ResponseEntity.badRequest()
						.body("Error: Email is already in use!");
			}

			User user = userService.createUser(signupRequest);

			return ResponseEntity.ok("User registered successfully!");

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getUsername(),
							loginRequest.getPassword())
			);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtUtil.generateToken(userDetails);

			User user = userRepository.findByUsername(loginRequest.getUsername())
					.orElseThrow(() -> new RuntimeException("User not found"));

			return ResponseEntity.ok(new JwtResponse(jwt,
					user.getUsername(),
					user.getEmail(),
					user.getRole().name()));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error: Invalid username or password!");
		}
	}

	@GetMapping("/test")
	public ResponseEntity<String> testEndpoint() {
		return ResponseEntity.ok("Auth endpoints are working!");
	}
}