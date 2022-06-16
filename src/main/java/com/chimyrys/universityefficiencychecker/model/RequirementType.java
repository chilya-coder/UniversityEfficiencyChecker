package com.chimyrys.universityefficiencychecker.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "requirements_types")
@Builder
public class RequirementType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reqTypeId;
    private String name;
    private int reqNumber;
    private String condition1;
}
