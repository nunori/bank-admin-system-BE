package com.im.user.service;

import com.im.user.entity.User;
import com.im.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNumber) throws UsernameNotFoundException {
        User user = userRepository.findByUserNumber(userNumber)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with userNumber"));

        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("DEPT_" + user.getDeptId()));

        return new org.springframework.security.core.userdetails.User(
                user.getUserNumber(), user.getUserPassword(), authorities);
    }
}
