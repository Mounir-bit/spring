package com.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.users.entities.Role;
import com.users.entities.User;
import com.users.payload.request.LoginRequest;
import com.users.payload.request.SignupRequest;
import com.users.payload.response.JwtResponse;
import com.users.payload.response.MessageResponse;
import com.users.repositories.RoleRepository;
import com.users.repositories.UserRepository;
import com.users.secruity.jwt.JwtUtils;
import com.users.secruity.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		System.out.println("do AuthController signin！");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		System.out.println("--------");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = JwtUtils.generateJwtCookie(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> {
			System.out.println("Item > " + item.toString());
			return item.getAuthority();
		}).collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Username is already taken!"));
		}
		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Email is already in use!"));
		}
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(),
				encoder.encode(signupRequest.getPassword()));

		String strRoles = signupRequest.getRole();
		Role role=roleRepository.findByName(strRoles);
		user.setRoles(new HashSet<Role>(Arrays.asList(role)));
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	@RequestMapping(value ="/login/{username}",method = RequestMethod.GET)
	public Optional<User> getUserByUsernamePassword(@PathVariable("username") String username) {
	return userRepository.findByUsername(username);
	}
}