package com.hlebon.studentMark;

import com.hlebon.schedule.ScheduleModalDto;
import com.hlebon.student.StudentModalDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentMarkModalDto {
    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private StudentModalDto student;

    private ScheduleModalDto schedule;

    private SimpleStringProperty scheduleName = new SimpleStringProperty();

    private SimpleIntegerProperty mark = new SimpleIntegerProperty();

    public Long getId() {
        return id.get();
    }

    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public void setId(Long id) {
        this.id.set(id);
    }

    public StudentModalDto getStudent() {
        return student;
    }

    public void setStudent(StudentModalDto student) {
        if (this.student == null) {
            this.student = student;
            return;
        }
        this.student.setId(student.getId());
        this.student.setFullName(student.getFullName());
    }

    public ScheduleModalDto getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleModalDto schedule) {
        this.schedule = schedule;
        String setOfGroupName = schedule.getSetOfGroupName();
        String subjectName = schedule.getSubject().getName();
        String sessionName = schedule.getSession().getName();
        String scheduleName = setOfGroupName + " - " + subjectName + " - " + sessionName;
        setScheduleName(scheduleName);
    }

    public String getScheduleName() {
        return scheduleName.get();
    }

    public SimpleStringProperty scheduleNameProperty() {
        return scheduleName;
    }

    private void setScheduleName(String scheduleName) {
        this.scheduleName.set(scheduleName);
    }

    public int getMark() {
        return mark.get();
    }

    public SimpleIntegerProperty markProperty() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark.set(mark);
    }
}
