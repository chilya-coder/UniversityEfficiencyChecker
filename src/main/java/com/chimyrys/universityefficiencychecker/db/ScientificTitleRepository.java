package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ScientificTitle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScientificTitleRepository extends JpaRepository<ScientificTitle, Integer> {
}
