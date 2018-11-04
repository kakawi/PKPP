package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "schedule")
@Getter
@Setter
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "scheduleGenerator")
    @SequenceGenerator(name = "scheduleGenerator", sequenceName = "s_schedule", allocationSize = 1)
    private Long id;

    @ManyToOne
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private SetOfGroupEntity setOfGroup;

    @ManyToOne
    private SessionEntity session;

}
