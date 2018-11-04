package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "set")
@Getter
@Setter
public class SetOfGroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setGenerator")
    @SequenceGenerator(name = "setGenerator", allocationSize = 1, sequenceName = "set_id_seq")
    private Long id;

    @Column(name = "year_of_establishment")
    private int year;

    @ManyToOne
    private SpecialityEntity speciality;

}
