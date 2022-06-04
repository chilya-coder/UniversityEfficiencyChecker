package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.UserCredentialRepository;
import com.chimyrys.universityefficiencychecker.model.UserCredential;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserCredentialRepository userCredentialRepository;
    private final static String NO_USER_WITH_CURRENT_LOGIN = "User with login %s not found";

    public UserDetailsServiceImpl(UserCredentialRepository userCredentialRepository) {
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> userCredential = userCredentialRepository.getUserCredentialByLogin(username);
        if (!userCredential.isPresent()) {
            throw new UsernameNotFoundException(String.format(NO_USER_WITH_CURRENT_LOGIN, username));
        }
        return userCredential.get();
    }
}
