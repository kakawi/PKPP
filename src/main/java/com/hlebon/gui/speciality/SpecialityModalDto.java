package com.hlebon.gui.speciality;

import com.hlebon.gui.department.DepartmentModalDto;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class SpecialityModalDto {
    private SimpleLongProperty id = new SimpleLongProperty();

    private SimpleStringProperty name = new SimpleStringProperty();

    private DepartmentModalDto department;

    private SimpleStringProperty departmentName = new SimpleStringProperty();

    public void setDepartment(DepartmentModalDto department) {
        this.department = department;
        setDepartmentName(department.getName());
    }

    public DepartmentModalDto getDepartment() {
        return department;
    }

    public long getId() {
        return id.get();
    }

    public SimpleLongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
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

    public String getDepartmentName() {
        return departmentName.get();
    }

    public SimpleStringProperty departmentNameProperty() {
        return departmentName;
    }

    private void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }
}
