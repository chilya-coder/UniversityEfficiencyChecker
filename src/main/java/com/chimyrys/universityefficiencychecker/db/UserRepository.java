package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.Contract;
import com.chimyrys.universityefficiencychecker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLastNameAndFirstNameAndPatronymic(String lastName, String firstName, String Patronymic);
}
