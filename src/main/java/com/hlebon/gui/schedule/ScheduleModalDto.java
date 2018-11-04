package com.hlebon.gui.schedule;

import com.hlebon.gui.session.SessionModalDto;
import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;
import com.hlebon.gui.subject.SubjectModalDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScheduleModalDto {

    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleStringProperty setOfGroupName = new SimpleStringProperty();

    private SubjectModalDto subject;

    private SetOfGroupModalDto setOfGroup;

    private SessionModalDto session;

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

    public SubjectModalDto getSubject() {
        return subject;
    }

    public void setSubject(SubjectModalDto subject) {
        if (this.subject == null) {
            this.subject = subject;
            return;
        }
        this.subject.setId(subject.getId());
        this.subject.setName(subject.getName());
    }

    public SetOfGroupModalDto getSetOfGroup() {
        return setOfGroup;
    }

    public void setSetOfGroup(SetOfGroupModalDto setOfGroup) {
        this.setOfGroup = setOfGroup;
        String specialityName = setOfGroup.getSpeciality().getName();
        int year = setOfGroup.getYear();
        String setOfGroupName = specialityName + " - " + year;
        setSetOfGroupName(setOfGroupName);
    }

    public SessionModalDto getSession() {
        return session;
    }

    public void setSession(SessionModalDto session) {
        if (this.session == null) {
            this.session = session;
            return;
        }
        this.session.setId(session.getId());
        this.session.setName(session.getName());
        this.session.setYear(session.getYear());
    }

}
