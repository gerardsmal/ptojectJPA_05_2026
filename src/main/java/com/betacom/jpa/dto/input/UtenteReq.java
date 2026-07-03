package com.betacom.jpa.dto.input;

import com.betacom.jpa.enums.Roles;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UtenteReq {
	@NotNull (groups = ValidationGroups.Create.class , message ="user_empty")
	private String userName;
	
	@NotNull (groups = ValidationGroups.Create.class , message ="user_pwd_empty")
	private String pwd;
	private String email;
	@NotNull (groups = ValidationGroups.Create.class , message ="user_role_empty")
	private String role;
}
