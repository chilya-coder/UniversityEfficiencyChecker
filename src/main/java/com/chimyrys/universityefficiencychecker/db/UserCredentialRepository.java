package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Integer> {
    Optional<UserCredential> getUserCredentialByLogin(String login);
}
