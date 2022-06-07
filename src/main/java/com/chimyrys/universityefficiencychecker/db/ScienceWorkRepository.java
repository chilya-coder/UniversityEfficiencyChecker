package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ScienceWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScienceWorkRepository extends JpaRepository<ScienceWork, Integer> {
}
