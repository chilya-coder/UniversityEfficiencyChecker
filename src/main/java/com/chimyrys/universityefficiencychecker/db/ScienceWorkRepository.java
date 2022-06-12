package com.chimyrys.universityefficiencychecker.db;

import com.chimyrys.universityefficiencychecker.model.ScienceWork;
import com.chimyrys.universityefficiencychecker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScienceWorkRepository extends JpaRepository<ScienceWork, Integer> {
    List<ScienceWork> findAllByUsersEqualsOrderByDateOfPublicationAsc(User user);
    List<ScienceWork> findAllByDateOfPublicationBetweenAndUsersEqualsOrderByDateOfPublicationAsc(Date from, Date to, User user);
}
