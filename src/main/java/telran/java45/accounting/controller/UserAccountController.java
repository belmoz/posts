package telran.java45.accounting.controller;

import java.util.Base64;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import telran.java45.accounting.dto.RolesResponseDto;
import telran.java45.accounting.dto.UserAccountResponseDto;
import telran.java45.accounting.dto.UserRegisterDto;
import telran.java45.accounting.dto.UserUpdateDto;
import telran.java45.accounting.service.UserAccountService;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserAccountController {
	
	final UserAccountService accountService;
	
	@PostMapping("/register")
	public UserAccountResponseDto register(@RequestBody UserRegisterDto userRegisterDto) {
		return accountService.addUser(userRegisterDto);
	}
	
	@PostMapping("/login")
	public UserAccountResponseDto login(@RequestHeader("Authorization") String token) {
		String[] credentials = getCredentials(token);
		return accountService.getUser(credentials[0]);
	}
	
	@DeleteMapping("/user/{user}")
	public UserAccountResponseDto deleteUser(@PathVariable("user") String login) {
		return accountService.removeUser(login);
	}
	
	@PutMapping("/user/{user}")
	public UserAccountResponseDto updateUser(@PathVariable("user") String login, @RequestBody UserUpdateDto userUpdateDto) {
		return accountService.editUser(login, userUpdateDto);
	}
	
	@PutMapping("/user/{user}/role/{role}")
	public RolesResponseDto addRole(@PathVariable("user") String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, true);
	}
	
	@DeleteMapping("/user/{user}/role/{role}")
	public RolesResponseDto removeRole(@PathVariable("user") String login, @PathVariable String role) {
		return accountService.changeRolesList(login, role, false);
	}
	
	@PutMapping("/password")
	public void changePassword(@RequestHeader("X-Password") String newPassword, @RequestHeader("Authorization") String token) {
		String[] credentials = getCredentials(token);
		accountService.changePassword(credentials[0], newPassword);
	}

	private String[] getCredentials(String token) {
		String[] basicAuth = token.split(" ");
		String decode = new String(Base64.getDecoder().decode(basicAuth[1]));
		return decode.split(":");
	}
	
}
