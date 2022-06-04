package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
}
