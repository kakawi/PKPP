package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "groups")
@Getter
@Setter
public class GroupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groupGenerator")
    @SequenceGenerator(name = "groupGenerator", sequenceName = "group_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "group_number")
    private String groupNumber;

    @ManyToOne
    @JoinColumn(name = "set_id")
    private SetOfGroupEntity setOfGroup;

}
