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
@Table(name = "department")
@Getter
@Setter
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departmentGenerator")
    @SequenceGenerator(name = "departmentGenerator", sequenceName = "kafedra_id_seq", allocationSize = 1)
    private Long id;

    private String name;

    @ManyToOne
    private FacultyEntity faculty;

}
