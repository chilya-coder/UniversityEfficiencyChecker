package com.chimyrys.universityefficiencychecker.services;

import com.chimyrys.universityefficiencychecker.db.*;
import com.chimyrys.universityefficiencychecker.model.*;
import com.chimyrys.universityefficiencychecker.services.api.ScienceWorkService;
import com.chimyrys.universityefficiencychecker.services.api.UserService;
import com.chimyrys.universityefficiencychecker.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

@Service
public class ScienceWorkServiceImpl implements ScienceWorkService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ScienceWorkRepository scienceWorkRepository;
    private final ExternalStudentRepository externalStudentRepository;
    private final ExternalAuthorRepository externalAuthorRepository;
    private final SpecialtyRepository specialtyRepository;

    public ScienceWorkServiceImpl(UserService userService, UserRepository userRepository, ScienceWorkRepository scienceWorkRepository, ExternalStudentRepository externalStudentRepository, ExternalAuthorRepository externalAuthorRepository, SpecialtyRepository specialtyRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.scienceWorkRepository = scienceWorkRepository;
        this.externalStudentRepository = externalStudentRepository;
        this.externalAuthorRepository = externalAuthorRepository;
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public void addScienceWorkByUserInput(Map<String, String> input) {
        ScienceWork.ScienceWorkBuilder builder = ScienceWork.builder();
        User currentUser = userService.getCurrentUser();
        List<User> users = new ArrayList<>();
        users.add(currentUser);
        for(Map.Entry<String, String> entry : input.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.equals("name")) {
                builder.name(value);
            } else if (key.equals("typeOfWorkId")) {
                builder.typeOfWork(Arrays.stream(TypeOfWork.values())
                        .filter(x -> x.getId() == Integer.parseInt(value))
                        .findFirst()
                        .get());
            } else if (key.equals("charOfWorkId")) {
                builder.charOfWork(Arrays.stream(CharOfWork.values())
                        .filter(x -> x.getId() == Integer.parseInt(value))
                        .findFirst()
                        .get());
            } else if (key.equals("outputData")) {
                builder.outputData(value);
            } else if (key.equals("dateOfPublication")) {
                try {
                    builder.dateOfPublication(DateUtils.getDateFromString(value));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            else if (key.equals("size")) {
                builder.size(Integer.parseInt(value));
            } else if (key.equals("specialtyId")) {
                builder.specialty(specialtyRepository.findById(Integer.parseInt(value)).get());
            } else if (key.equals("extTeacherName") && value != null && !value.isEmpty()) {
                String[] additionalUsers = value.split(", ");
                for (String userFullName: additionalUsers) {
                    String[] names = userFullName.split(" ");
                    Optional<User> userOptional = userRepository.findByLastNameAndFirstNameAndPatronymic(names[0], names[1], names[2]);
                    userOptional.ifPresent(users::add);
                }
            } else if (key.equals("extStudentName") && value != null && !value.isEmpty()) {
                builder.hasExtStud(true);
                String[] studentsNames = value.split(", ");
                List<ExternalStudent> externalStudents = new ArrayList<>();
                for (String studentFullName: studentsNames) {
                    ExternalStudent externalStudent = new ExternalStudent(studentFullName);
                    Optional<ExternalStudent> optional = externalStudentRepository.findBySurnameAndNameAndPatronymic(externalStudent.getSurname(), externalStudent.getName(), externalStudent.getPatronymic());
                    if (optional.isPresent()) {
                        externalStudents.add(optional.get());
                    } else {
                        externalStudents.add(externalStudent);
                    }
                }
                externalStudents.forEach(externalStudentRepository::save);
                builder.externalStudents(externalStudents);
            } else if (key.equals("extAuthorsName") && value != null && !value.isEmpty()) {
                builder.hasExtAuthors(true);
                String[] extAuthorsNames = value.split(", ");
                List<ExternalAuthor> externalAuthors = new ArrayList<>();
                for (String extAuthorFullName: extAuthorsNames) {
                    ExternalAuthor externalAuthor = new ExternalAuthor(extAuthorFullName);
                    Optional<ExternalAuthor> optional = externalAuthorRepository.findBySurnameAndNameAndPatronymic(externalAuthor.getSurname(), externalAuthor.getName(), externalAuthor.getPatronymic());
                    if (optional.isPresent()) {
                        externalAuthors.add(optional.get());
                    } else {
                        externalAuthors.add(externalAuthor);
                    }
                }
                externalAuthors.forEach(externalAuthorRepository::save);
                builder.externalAuthors(externalAuthors);
            }
        }
        builder.users(users);
        ScienceWork scienceWork = builder.build();
        scienceWorkRepository.save(scienceWork);
    }

}
