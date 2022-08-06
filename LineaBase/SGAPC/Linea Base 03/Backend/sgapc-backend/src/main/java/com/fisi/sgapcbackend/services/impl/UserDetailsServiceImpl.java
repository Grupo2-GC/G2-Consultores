package com.fisi.sgapcbackend.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.fisi.sgapcbackend.entities.User;
import com.fisi.sgapcbackend.repositories.IUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
    private final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

	@Autowired
    private IUserRepository repo;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String usernameOrEmail) {
        Optional<User> userOptional = repo.findByUsernameOrEmail(usernameOrEmail,usernameOrEmail);

        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ese username o email: "+usernameOrEmail));

        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .peek(authority -> log.info("Role: "+ authority.getAuthority()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getEnabled(), true, true, true, authorities);

    }

}
