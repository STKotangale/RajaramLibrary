package com.raja.lib.auth.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.raja.lib.auth.entity.Role;
import com.raja.lib.auth.entity.User;
import com.raja.lib.auth.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	  @Autowired
	  UserRepository userRepository;

	  public UserDetailsServiceImpl(UserRepository userRepository) {
		  this.userRepository = userRepository;
	  }
	  
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	System.out.println("Auth1");
        User user = userRepository.findByEmail(email);
    	System.out.println("Auth2");

        if (user != null) {
        	System.out.println("Auth3");
            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(),
                    mapRolesToAuthorities(user.getRoles()));
        } else {
        	System.out.println("Auth4");
            throw new UsernameNotFoundException("Invalid username or password.");
        }
    }
	  
//	  @Override
//	  @Transactional
//	  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//	    User user = userRepository.findByEmail(email)
//	        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + email));
//
//	    return UserDetailsImpl.build(user);
//	  }
	  
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
	        Collection<? extends GrantedAuthority> mapRoles = roles.stream()
	                .map(role -> new SimpleGrantedAuthority(role.getUsername()))
	                .collect(Collectors.toList());
	        return mapRoles;
	    }
	  
}