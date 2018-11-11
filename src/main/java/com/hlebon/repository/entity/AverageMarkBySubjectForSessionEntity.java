package com.hlebon.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AverageMarkBySubjectForSessionEntity {

    public AverageMarkBySubjectForSessionEntity(String subjectName, double averageMark) {
        this.subjectName = subjectName;
        this.averageMark = averageMark;
    }

    private String subjectName;

    private double averageMark;

}
