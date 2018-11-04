package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "speciality")
@Getter
@Setter
public class SpecialityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "specialityGenerator")
    @SequenceGenerator(name = "specialityGenerator", allocationSize = 1, sequenceName = "speciality_id_seq")
    private Long id;
    private String name;

    @ManyToOne
    private DepartmentEntity department;

    public SpecialityEntity() {
    }

    public SpecialityEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
