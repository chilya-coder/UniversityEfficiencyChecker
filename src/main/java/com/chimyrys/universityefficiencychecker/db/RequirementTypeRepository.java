package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.RequirementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequirementTypeRepository extends JpaRepository<RequirementType, Integer> {
}
