package com.raja.lib.auth.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                .collect(Collectors.toList());

        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with username: " + loginRequest.getUsername()));

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        if (signUpRequest.getPassword().length() < 6) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Password length must be at least 6 characters!"));
        }

        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), 'N', signUpRequest.getMobileNo()); // Updated constructor

        Set<Role> roles = new HashSet<>();

        if (signUpRequest.getRole() != null && !signUpRequest.getRole().isEmpty()) {
            signUpRequest.getRole().forEach(roleName -> {
                Role role = roleRepository.findByRoleName(roleName)
                        .orElseThrow(() -> new RuntimeException("Error: Role '" + roleName + "' not found."));
                roles.add(role);
            });
        } else {
            Role defaultRole = roleRepository.findByRoleName("admin")
                    .orElseThrow(() -> new RuntimeException("Error: Default role 'admin' not found."));
            roles.add(defaultRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/users")
    List<User> getAllUser() {
        return userRepository.getAllAdminUsers();
    }

    @GetMapping("/{id}")
    Optional<User> getUserById(@PathVariable int id) {
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    void deleteUserById(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable int id, @Valid @RequestBody User updatedUser) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setUseremail(updatedUser.getUseremail());
            user.setIsBlock(updatedUser.getIsBlock());
            user.setMobileNo(updatedUser.getMobileNo()); // New field
            user.setGeneralMember(updatedUser.getGeneralMember());

            if (updatedUser.getUserpassword() != null) {
                user.setUserpassword(encoder.encode(updatedUser.getUserpassword()));
            }

            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("User updated successfully"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
