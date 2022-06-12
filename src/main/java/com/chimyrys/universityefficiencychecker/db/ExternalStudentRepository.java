package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ExternalAuthor;
import com.chimyrys.universityefficiencychecker.model.ExternalStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExternalStudentRepository extends JpaRepository<ExternalStudent, Integer> {
    Optional<ExternalStudent> findBySurnameAndNameAndPatronymic(String surname, String name, String Patronymic);
}
