package com.hlebon.session;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class SessionModalDto {
    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty year = new SimpleIntegerProperty();
    private SimpleBooleanProperty isFinished = new SimpleBooleanProperty();

    public Long getId() {
        return id.get();
    }

    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public boolean isIsFinished() {
        return isFinished.get();
    }

    public SimpleBooleanProperty isFinishedProperty() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished.set(isFinished);
    }
}
