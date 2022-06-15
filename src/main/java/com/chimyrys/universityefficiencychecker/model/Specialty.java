package com.chimyrys.universityefficiencychecker.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "specialties")
@EqualsAndHashCode
@Builder
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int specialtyId;
    private String name;
    private int code;
    private String specialtyAlias;
    @OneToMany(mappedBy = "specialty")
    private List<ScienceWork> scienceWorkList;
    @OneToMany(mappedBy = "specialty")
    private List<Contract> contractList;
}
