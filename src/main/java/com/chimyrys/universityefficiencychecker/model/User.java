package com.chimyrys.universityefficiencychecker.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @NotBlank(message = "Введіть ім'я")
    private String firstName;
    @NotBlank(message = "Введіть прізвище")
    private String lastName;
    @NotBlank(message = "Введіть по-батькові")
    private String patronymic;
    @OneToMany(mappedBy = "user")
    private List<UserCredential> userCredentialList;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @Email
    @NotBlank(message = "Введіть емейл")
    private String email;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @ManyToOne
    @JoinColumn(name = "degree_id")
    private Degree degree;
    @ManyToOne
    @JoinColumn(name = "scientific_title_id")
    private ScientificTitle scientificTitle;
    @OneToMany(mappedBy = "user")
    private List<Contract> contract;
    @ManyToMany(mappedBy = "users")
    private List<ScienceWork> scienceWorks;
    public User(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Incorrect fullName");
        }
        this.firstName = parts[0];
        this.lastName = parts[1];
        this.patronymic = parts[2];
    }
    public String getFullName() {
        return lastName + " " + firstName + " " + patronymic;
    }
}
