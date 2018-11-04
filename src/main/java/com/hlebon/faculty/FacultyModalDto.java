package com.hlebon.faculty;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class FacultyModalDto {

//    private SimpleLongProperty id = new SimpleLongProperty();
    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleStringProperty name = new SimpleStringProperty();

    public FacultyModalDto() {
    }

    public FacultyModalDto(Long id, String name) {
        this.id = new SimpleObjectProperty<>(id);
        this.name = new SimpleStringProperty(name);
    }

    public Long getId() {
        return id.get();
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public ObjectProperty<Long> idProperty() {
        return id;
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

}
