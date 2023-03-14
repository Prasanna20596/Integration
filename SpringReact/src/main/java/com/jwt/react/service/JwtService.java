package com.jwt.react.service;

import com.jwt.react.dao.UserDao;
import com.jwt.react.entity.JwtRequest;
import com.jwt.react.entity.JwtResponse;
import com.jwt.react.entity.TokenRefreshResponse;
import com.jwt.react.entity.TokenRequest;
import com.jwt.react.entity.User;
import com.jwt.react.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtService implements UserDetailsService {
	
	String subject;
	
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationManager authenticationManager;
    
	public JwtResponse signinToken(JwtRequest request) throws Exception {
		String userName = request.getUserName();
		String userPassword = request.getUserPassword();
		authenticate(userName, userPassword);

		UserDetails userDetails = loadUserByUsername(userName);
		String newGeneratedToken = jwtUtil.generateToken(userDetails);
		
		User user = userDao.findByUserName(userName);
		return new JwtResponse(user, newGeneratedToken);
	}
	
	public TokenRefreshResponse getNewAccessToken(TokenRequest request) {
		String newAccessToken=jwtUtil.getNewAccessToken(request);
		return new TokenRefreshResponse(newAccessToken);	
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username);

		if (user != null) {
			return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(),
					getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities = Arrays.stream(user.getAuthenticaterole().split(",")).map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		return authorities;
	}

	private void authenticate(String userName, String userPassword) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
