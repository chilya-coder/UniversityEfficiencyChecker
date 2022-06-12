package com.chimyrys.universityefficiencychecker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scientific_titles")
public class ScientificTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int scientificTitleId;
    private String name;
    @OneToMany(mappedBy = "scientificTitle")
    @JsonIgnore
    private List<User> userList;
}
