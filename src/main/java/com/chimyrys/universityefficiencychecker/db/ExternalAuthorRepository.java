package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ExternalAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalAuthorRepository extends JpaRepository<ExternalAuthor, Integer> {
}
