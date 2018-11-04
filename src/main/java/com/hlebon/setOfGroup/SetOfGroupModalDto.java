package com.hlebon.setOfGroup;

import com.hlebon.speciality.SpecialityModalDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class SetOfGroupModalDto {

    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleIntegerProperty year = new SimpleIntegerProperty();

    private ObjectProperty<SpecialityModalDto> speciality = new SimpleObjectProperty<>();

    public SpecialityModalDto getSpeciality() {
        return speciality.get();
    }

    public ObjectProperty<SpecialityModalDto> specialityProperty() {
        return speciality;
    }

    public void setSpeciality(SpecialityModalDto speciality) {
        SpecialityModalDto specialityModalDto = this.speciality.get();
        if (specialityModalDto == null) {
            this.speciality.set(speciality);
            return;
        }
        specialityModalDto.setId(speciality.getId());
        specialityModalDto.setName(speciality.getName());
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

    public Long getId() {
        return id.get();
    }

    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

}
