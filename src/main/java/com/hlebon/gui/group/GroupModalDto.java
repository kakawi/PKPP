package com.hlebon.gui.group;

import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class GroupModalDto {

    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleStringProperty groupNumber = new SimpleStringProperty();

    private ObjectProperty<SetOfGroupModalDto> setOfGroup = new SimpleObjectProperty<>();

    private SimpleStringProperty setOfGroupName = new SimpleStringProperty();

    public SetOfGroupModalDto getSetOfGroup() {
        return setOfGroup.get();
    }

    public ObjectProperty<SetOfGroupModalDto> setOfGroupProperty() {
        return setOfGroup;
    }

    public void setSetOfGroup(SetOfGroupModalDto setOfGroup) {
        this.setOfGroup.set(setOfGroup);
        setSetOfGroupName(setOfGroup.getSpeciality().getName() + " - " + setOfGroup.getYear());
    }

    public String getSetOfGroupName() {
        return setOfGroupName.get();
    }

    public SimpleStringProperty setOfGroupNameProperty() {
        return setOfGroupName;
    }

    private void setSetOfGroupName(String setOfGroupName) {
        this.setOfGroupName.set(setOfGroupName);
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

    public String getGroupNumber() {
        return groupNumber.get();
    }

    public SimpleStringProperty groupNumberProperty() {
        return groupNumber;
    }

    public void setGroupNumber(String name) {
        this.groupNumber.set(name);
    }

}
