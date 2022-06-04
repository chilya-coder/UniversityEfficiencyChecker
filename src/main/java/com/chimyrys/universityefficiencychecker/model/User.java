package com.chimyrys.universityefficiencychecker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
