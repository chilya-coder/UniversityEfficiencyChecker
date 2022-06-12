package com.chimyrys.universityefficiencychecker.model;

import lombok.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "science_works")
@EqualsAndHashCode
public class ScienceWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scienceWorkId;
    @NotBlank(message = "Введіть назву публікації")
    private String name;
    @Column(name = "char_of_work_id")
    @Enumerated(EnumType.ORDINAL)
    private CharOfWork charOfWork;
    private Date dateOfPublication;
    @Column(name = "type_of_work_id")
    @Enumerated(EnumType.ORDINAL)
    private TypeOfWork typeOfWork;
    private int size;
    private boolean hasExtAuthors;
    private String outputData;
    private boolean hasExtStud;
    @ManyToMany
    @JoinTable(
            name = "ext_auth_science_works",
            joinColumns = @JoinColumn(name = "science_work_id"),
            inverseJoinColumns = @JoinColumn(name = "ext_author_id"))
    private List<ExternalAuthor> externalAuthors;
    @ManyToMany
    @JoinTable(
            name = "ext_stud_science_work",
            joinColumns = @JoinColumn(name = "science_work_id"),
            inverseJoinColumns = @JoinColumn(name = "ext_student_id"))
    private List<ExternalStudent> externalStudents;
    @ManyToMany
    @JoinTable(
            name = "authors_science_works",
            joinColumns = @JoinColumn(name = "science_work_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;
    public String getCoAuthorsNames(User currentUser) {
        List<String> names = new ArrayList<>();
        for (User user : users) {
            if (!user.equals(currentUser)) {
                names.add(user.getFullName());
            }
        }
        for (ExternalAuthor externalAuthor : externalAuthors) {
            names.add(externalAuthor.getFullName());
        }
        for (ExternalStudent externalStudent : externalStudents) {
            names.add(externalStudent.getFullName() + " " + externalStudent.getGroupId());
        }
        return String.join(", ", names);
    }
}
