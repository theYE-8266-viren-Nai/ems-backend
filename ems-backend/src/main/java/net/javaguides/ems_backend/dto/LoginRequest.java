package net.javaguides.ems_backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String username;
	private String password;
}