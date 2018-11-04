package com.hlebon.gui.department;

import com.hlebon.gui.faculty.FacultyModalDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class DepartmentModalDto {
    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleStringProperty name = new SimpleStringProperty();

    private ObjectProperty<FacultyModalDto> faculty = new SimpleObjectProperty<>();

    public FacultyModalDto getFaculty() {
        return faculty.get();
    }

    public ObjectProperty<FacultyModalDto> facultyProperty() {
        return faculty;
    }

    public void setFaculty(FacultyModalDto faculty) {
        FacultyModalDto existingFacultyModalDto = this.faculty.get();
        if (existingFacultyModalDto == null) {
            this.faculty.set(faculty);
            return;
        }

        existingFacultyModalDto.setId(faculty.getId());
        existingFacultyModalDto.setName(faculty.getName());
    }

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

}
