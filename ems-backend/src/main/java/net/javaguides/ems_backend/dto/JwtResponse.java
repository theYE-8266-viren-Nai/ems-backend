package net.javaguides.ems_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	private String email;
	private String role;

	public JwtResponse(String token, String username, String email, String role) {
		this.token = token;
		this.username = username;
		this.email = email;
		this.role = role;
	}
}