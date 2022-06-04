package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository <Department, Integer> {
}
