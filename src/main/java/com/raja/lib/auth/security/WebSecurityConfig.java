package com.raja.lib.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.raja.lib.auth.service.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

  @Autowired
  UserDetailsServiceImpl userDetailsService;

  @Autowired
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
       
      authProvider.setUserDetailsService(userDetailsService);
      authProvider.setPasswordEncoder(passwordEncoder());
   
      return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
      http.csrf(csrf -> csrf.disable())
          .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
          .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)) // Change session creation policy here
          .authorizeHttpRequests(auth -> 
              auth.requestMatchers("/api/auth/**").permitAll()
                  .requestMatchers("/api/purchase/**").permitAll()
                  .requestMatchers("/api/ledger/**").permitAll()
                  .requestMatchers("/api/permanent-members/**").permitAll()
                  .requestMatchers("/api/general-members/**").permitAll()
                  .requestMatchers("/api/user-members/**").permitAll()
                  .requestMatchers("/api/bookdetails/**").permitAll()
                  .requestMatchers("/api/book-authors/**").permitAll()
                  .requestMatchers("/api/book-publications/**").permitAll()
                  .requestMatchers("/api/stock/**").permitAll()
                  .requestMatchers("/api/issue/**").permitAll()
                  .requestMatchers("/api/fees/**").permitAll()
                  .requestMatchers("/api/excel/**").permitAll()
                  .requestMatchers("/api/config/**").permitAll()
                  .requestMatchers("/api/monthly-member-fees/**").permitAll()
                  .requestMatchers("/api/membership-fees/**").permitAll()
                  .requestMatchers("/api/member-bookings/**").permitAll()
                  .requestMatchers("/api/reports/**").permitAll()
                  .anyRequest().authenticated()
          );
      http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
      return http.build(); 
  }
}
