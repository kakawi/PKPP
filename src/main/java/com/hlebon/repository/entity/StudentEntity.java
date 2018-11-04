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
@Table(name = "student")
@Getter
@Setter
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentGenerator")
    @SequenceGenerator(name = "studentGenerator", sequenceName = "student_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "record_book")
    private String recordBook;

    @Column(name = "address_of_permanent_residence")
    private String addressOfPermanentResidence;

    @Column(name = "address_of_residence")
    private String addressOfResidence;

    @Column(name = "is_get_scholarship")
    private boolean isGetScholarship;

    private Integer premium;

    @Column(name = "is_local")
    private boolean isLocal;

    @ManyToOne
    private GroupEntity group;

}
