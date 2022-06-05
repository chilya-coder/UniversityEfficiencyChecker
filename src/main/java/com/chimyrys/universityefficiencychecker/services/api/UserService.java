package com.chimyrys.universityefficiencychecker.services.api;

import com.chimyrys.universityefficiencychecker.model.Department;
import com.chimyrys.universityefficiencychecker.model.Position;
import com.chimyrys.universityefficiencychecker.model.User;
import com.chimyrys.universityefficiencychecker.model.UserCredential;

import java.util.Optional;

public interface UserService {
    public void createDefaultUser(UserCredential userCredential,
                                  Optional<Department> optionalDepartment,
                                  Optional<Position> optionalPosition);

    public void updateUserByFormData(User userOld, User newUser);
}
