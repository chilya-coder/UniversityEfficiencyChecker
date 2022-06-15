package com.chimyrys.universityefficiencychecker.services.api;

import com.chimyrys.universityefficiencychecker.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    void createDefaultUser(UserCredential userCredential,
                           Optional<Department> optionalDepartment,
                           Optional<Position> optionalPosition,
                           Optional<ScientificTitle> optionalScientificTitle,
                           Optional<Degree> optionalDegree);

    User getCurrentUser();

    UserDetails getCurrentUserDetails();

    UserCredential getUserCredentials();
}
