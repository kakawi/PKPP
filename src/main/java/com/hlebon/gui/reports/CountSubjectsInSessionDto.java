package com.hlebon.gui.reports;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CountSubjectsInSessionDto {

    private SimpleStringProperty sessionName = new SimpleStringProperty();

    private SimpleIntegerProperty sessionYear = new SimpleIntegerProperty();

    private SimpleIntegerProperty countOfSubjects = new SimpleIntegerProperty();

    public String getSessionName() {
        return sessionName.get();
    }

    public SimpleStringProperty sessionNameProperty() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName.set(sessionName);
    }

    public int getSessionYear() {
        return sessionYear.get();
    }

    public SimpleIntegerProperty sessionYearProperty() {
        return sessionYear;
    }

    public void setSessionYear(int sessionYear) {
        this.sessionYear.set(sessionYear);
    }

    public int getCountOfSubjects() {
        return countOfSubjects.get();
    }

    public SimpleIntegerProperty countOfSubjectsProperty() {
        return countOfSubjects;
    }

    public void setCountOfSubjects(int countOfSubjects) {
        this.countOfSubjects.set(countOfSubjects);
    }
}
