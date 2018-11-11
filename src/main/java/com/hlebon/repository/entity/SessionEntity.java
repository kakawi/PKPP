package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "session")
@Getter
@Setter
public class SessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sessionGenerator")
    @SequenceGenerator(name = "sessionGenerator", allocationSize = 1, sequenceName = "session_id_seq")
    private Long id;

    private String name;

    @Column(name = "year_of_session")
    private int year;

    @Column(name = "is_finished")
    private boolean isFinished;

    @OneToMany(mappedBy = "session")
    private List<ScheduleEntity> schedules;

}
