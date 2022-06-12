package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.Degree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegreeRepository extends JpaRepository<Degree, Integer> {
}
