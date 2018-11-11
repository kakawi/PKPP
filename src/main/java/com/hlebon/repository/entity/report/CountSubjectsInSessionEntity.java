package com.hlebon.repository.entity.report;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CountSubjectsInSessionEntity {

    private String sessionName;

    private int sessionYear;

    private long countOfSubjects;

    public CountSubjectsInSessionEntity(String sessionName, int sessionYear, long countOfSubjects) {
        this.sessionName = sessionName;
        this.sessionYear = sessionYear;
        this.countOfSubjects = countOfSubjects;
    }
}
