package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "faculty")
@Getter
@Setter
public class FacultyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "facultyGenerator")
    @SequenceGenerator(name = "facultyGenerator", allocationSize = 1, sequenceName = "fakultet_id_seq")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "faculty")
    private List<DepartmentEntity> departments;

    public FacultyEntity() {
    }

    public FacultyEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
}
