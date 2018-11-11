package com.hlebon.gui.reports;

import javafx.beans.property.SimpleStringProperty;

public class AverageMarkBySubjectDto {

    private SimpleStringProperty subjectName = new SimpleStringProperty();

    private SimpleStringProperty averageMark = new SimpleStringProperty();

    public String getSubjectName() {
        return subjectName.get();
    }

    public SimpleStringProperty subjectNameProperty() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName.set(subjectName);
    }

    public String getAverageMark() {
        return averageMark.get();
    }

    public SimpleStringProperty averageMarkProperty() {
        return averageMark;
    }

    public void setAverageMark(String averageMark) {
        this.averageMark.set(averageMark);
    }
}
