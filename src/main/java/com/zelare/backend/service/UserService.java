package com.zelare.backend.service;


import com.zelare.backend.entity.User;
import com.zelare.backend.entity.Enums.UserStatus;
import com.zelare.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + phoneNumber));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getPhoneNumber())
                .password("") // Pas de mot de passe pour l'auth OTP
                .authorities(user.getRole().name())
                .accountExpired(false)
                .accountLocked(user.getStatus() == UserStatus.SUSPENDED)
                .credentialsExpired(false)
                .disabled(!user.getPhoneVerified())
                .build();
    }
}

