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
@Table(name = "student_mark")
@Getter
@Setter
public class StudentMarkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "studentMarkGenerator")
    @SequenceGenerator(name = "studentMarkGenerator", sequenceName = "student_mark_id_seq", allocationSize = 1)
    private Long id;

    private int mark;

    @ManyToOne
    private StudentEntity student;

    @ManyToOne
    private ScheduleEntity schedule;

}
