package com.chimyrys.universityefficiencychecker.model;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "external_students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int extStudentId;
    private String name;
    private String surname;
    private String patronymic;
    private String groupId;
    @ManyToMany(mappedBy = "externalStudents")
    private List<ScienceWork> scienceWorks;
    public ExternalStudent(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Incorrect fullName");
        }
        this.surname = parts[0];
        this.name = parts[1];
        this.patronymic = parts[2].split("\\(")[0];
        this.groupId = "(" + parts[2].split("\\(")[1];
    }
    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }
}