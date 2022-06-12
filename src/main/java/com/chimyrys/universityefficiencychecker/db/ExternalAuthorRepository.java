package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ExternalAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalAuthorRepository extends JpaRepository<ExternalAuthor, Integer> {
    Optional<ExternalAuthor> findBySurnameAndNameAndPatronymic(String surname, String name, String Patronymic);
}
