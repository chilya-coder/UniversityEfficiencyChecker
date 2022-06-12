package com.chimyrys.universityefficiencychecker.services.api;

import com.chimyrys.universityefficiencychecker.model.Department;
import com.chimyrys.universityefficiencychecker.model.Position;
import com.chimyrys.universityefficiencychecker.model.User;
import com.chimyrys.universityefficiencychecker.model.UserCredential;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {
    void createDefaultUser(UserCredential userCredential,
                           Optional<Department> optionalDepartment,
                           Optional<Position> optionalPosition);
    User getCurrentUser();
    UserDetails getCurrentUserDetails();
}
