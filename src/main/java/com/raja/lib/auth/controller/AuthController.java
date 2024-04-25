package com.raja.lib.auth.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raja.lib.auth.model.ERole;
import com.raja.lib.auth.model.Role;
import com.raja.lib.auth.model.User;
import com.raja.lib.auth.repository.RoleRepository;
import com.raja.lib.auth.repository.UserRepository;
import com.raja.lib.auth.request.LoginRequest;
import com.raja.lib.auth.request.SignupRequest;
import com.raja.lib.auth.response.JwtResponse;
import com.raja.lib.auth.response.MessageResponse;
import com.raja.lib.auth.security.JwtUtils;
import com.raja.lib.auth.service.UserDetailsImpl;

import jakarta.validation.Valid;



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
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = jwtUtils.generateJwtToken(authentication);

      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
      List<String> roles = userDetails.getAuthorities().stream()
          .map(item -> item.getAuthority())
          .collect(Collectors.toList());

      // Retrieve user details from the database
      User user = userRepository.findByUsername(loginRequest.getUsername())
              .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));

      // Check if the user is blocked
      if (user.isBlock()) {
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Your account is blocked!"));
      }

//      // Check memberIdf if needed
//      if (user.getMemberIdf() != null && !user.getMemberIdf().equals(loginRequest.getMemberIdf())) {
//          return ResponseEntity
//              .badRequest()
//              .body(new MessageResponse("Error: Invalid memberIdf!"));
//      }

      return ResponseEntity.ok(new JwtResponse(jwt, 
                           userDetails.getId(), 
                           userDetails.getUsername(), 
                           userDetails.getEmail(), 
                           roles));
  }


  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
      if (userRepository.existsByUsername(signUpRequest.getUsername())) {
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Username is already taken!"));
      }

      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
          return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Error: Email is already in use!"));
      }

      // Create new user's account
      User user = new User(signUpRequest.getUsername(), 
                           signUpRequest.getEmail(),
                           encoder.encode(signUpRequest.getPassword()),
                           false, // Default value for isBlock
                           null); // Default value for memberIdf

      // Set isBlock value if provided
      user.setBlock(signUpRequest.isBlock());


      // Set memberIdf value if provided
      if (signUpRequest.getMemberIdf() != null) {
          user.setMemberIdf(signUpRequest.getMemberIdf());
      }

      Set<String> strRoles = signUpRequest.getRole();
      Set<Role> roles = new HashSet<>();

      if (strRoles == null) {
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
      } else {
          strRoles.forEach(role -> {
              switch (role) {
                  case "admin":
                      Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(adminRole);
                      break;
                  case "mod":
                      Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(modRole);
                      break;
                  default:
                      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                      roles.add(userRole);
              }
          });
      }

      user.setRoles(roles);
      userRepository.save(user);

      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


  @GetMapping("/users")
  List<User> getAllUser()
  {
	  return userRepository.findAll();
  }
  
  @GetMapping("/{id}")
  Optional<User> getUserById(@PathVariable Long id)
  {
	return userRepository.findById(id);
  }
  
  @DeleteMapping("/{id}")
  void deleteUserById(@PathVariable Long id)
  {
	userRepository.deleteById(id);
  }
  
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUserById(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
      Optional<User> optionalUser = userRepository.findById(id);
      if (optionalUser.isPresent()) {
          User user = optionalUser.get();
          user.setUsername(updatedUser.getUsername());
          user.setEmail(updatedUser.getEmail());
          user.setPassword(updatedUser.getPassword());
          user.setBlock(updatedUser.isBlock());
          user.setMemberIdf(updatedUser.getMemberIdf());

          userRepository.save(user);
          return ResponseEntity.ok(new MessageResponse("User updated successfully"));
      } else {
          return ResponseEntity.notFound().build();
      }
  }
  
}
