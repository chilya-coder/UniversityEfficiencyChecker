package com.chimyrys.universityefficiencychecker.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "external_authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExternalAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int extAuthorId;
    private String name;
    private String surname;
    private String patronymic;
    @ManyToMany(mappedBy = "externalAuthors")
    private List<ScienceWork> scienceWorks;
    public ExternalAuthor(String fullName) {
        String[] parts = fullName.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Incorrect fullName");
        }
        this.surname = parts[0];
        this.name = parts[1];
        this.patronymic = parts[2];
    }
    public String getFullName() {
        return surname + " " + name + " " + patronymic;
    }
}
