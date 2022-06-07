package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ExternalStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalStudentRepository extends JpaRepository<ExternalStudent, Integer> {
}
