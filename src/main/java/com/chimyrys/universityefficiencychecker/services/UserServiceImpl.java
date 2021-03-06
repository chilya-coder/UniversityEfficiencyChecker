package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.UserCredentialRepository;
import com.chimyrys.universityefficiencychecker.db.UserRepository;
import com.chimyrys.universityefficiencychecker.model.*;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserCredentialRepository userCredentialRepository;

    public UserServiceImpl(UserRepository userRepository, UserCredentialRepository userCredentialRepository) {
        this.userRepository = userRepository;
        this.userCredentialRepository = userCredentialRepository;
    }

    @Override
    public void createDefaultUser(UserCredential userCredential,
                                  Optional<Department> optionalDepartment,
                                  Optional<Position> optionalPosition,
                                  Optional<ScientificTitle> optionalScientificTitle,
                                  Optional<Degree> optionalDegree) {

        optionalDepartment.ifPresent(department -> userCredential.getUser().setDepartment(department));

        optionalPosition.ifPresent(position -> userCredential.getUser().setPosition(position));
        userCredential.getUser().setScientificTitle(optionalScientificTitle.get());
        userCredential.getUser().setDegree(optionalDegree.get());
        userCredential.setIsUserEnabled(IsUserEnabled.DISABLED);
        userCredential.setRole(Role.TEACHER);
        userRepository.save(userCredential.getUser());
        userCredentialRepository.save(userCredential);
    }

    public UserDetails getCurrentUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public User getCurrentUser() {
        return userCredentialRepository.getUserCredentialByLogin(getCurrentUserDetails().getUsername()).get().getUser();
    }

    public UserCredential getUserCredentials() {
        return userCredentialRepository.getUserCredentialByLogin(getCurrentUserDetails().getUsername()).get();
    }

}
