package com.hlebon.gui.student;

import com.hlebon.gui.group.GroupModalDto;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class StudentModalDto {
    private ObjectProperty<Long> id = new SimpleObjectProperty<>(null);

    private SimpleStringProperty lastName = new SimpleStringProperty();
    private SimpleStringProperty firstName = new SimpleStringProperty();
    private SimpleStringProperty middleName = new SimpleStringProperty();
    private SimpleStringProperty recordBook = new SimpleStringProperty();
    private SimpleStringProperty addressOfPermanentResidence = new SimpleStringProperty();
    private SimpleStringProperty addressOfResidence = new SimpleStringProperty();
    private SimpleBooleanProperty isGetScholarship = new SimpleBooleanProperty();
    private SimpleBooleanProperty isLocal = new SimpleBooleanProperty();
    private SimpleIntegerProperty premium = new SimpleIntegerProperty();

    private ObjectProperty<GroupModalDto> group = new SimpleObjectProperty<>();

    private SimpleStringProperty fullName = new SimpleStringProperty();

    public String getFullName() {
        return fullName.get();
    }

    public SimpleStringProperty fullNameProperty() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName.set(fullName);
    }

    private void updateFullName() {
        String fullName = this.lastName.getValue() + " " + this.firstName.getValue() + " " + this.middleName.getValue();
        setFullName(fullName);
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

    public String getLastName() {
        return lastName.get();
    }

    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
        updateFullName();
    }

    public String getFirstName() {
        return firstName.get();
    }

    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
        updateFullName();
    }

    public String getMiddleName() {
        return middleName.get();
    }

    public SimpleStringProperty middleNameProperty() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName.set(middleName);
        updateFullName();
    }

    public String getRecordBook() {
        return recordBook.get();
    }

    public SimpleStringProperty recordBookProperty() {
        return recordBook;
    }

    public void setRecordBook(String recordBook) {
        this.recordBook.set(recordBook);
    }

    public String getAddressOfPermanentResidence() {
        return addressOfPermanentResidence.get();
    }

    public SimpleStringProperty addressOfPermanentResidenceProperty() {
        return addressOfPermanentResidence;
    }

    public void setAddressOfPermanentResidence(String addressOfPermanentResidence) {
        this.addressOfPermanentResidence.set(addressOfPermanentResidence);
    }

    public String getAddressOfResidence() {
        return addressOfResidence.get();
    }

    public SimpleStringProperty addressOfResidenceProperty() {
        return addressOfResidence;
    }

    public void setAddressOfResidence(String addressOfResidence) {
        this.addressOfResidence.set(addressOfResidence);
    }

    public boolean isGetScholarship() {
        return isGetScholarship.get();
    }

    public SimpleBooleanProperty isGetScholarshipProperty() {
        return isGetScholarship;
    }

    public void setIsGetScholarship(boolean isGetScholarship) {
        this.isGetScholarship.set(isGetScholarship);
    }

    public boolean isLocal() {
        return isLocal.get();
    }

    public SimpleBooleanProperty isLocalProperty() {
        return isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal.set(isLocal);
    }

    public int getPremium() {
        return premium.get();
    }

    public SimpleIntegerProperty premiumProperty() {
        return premium;
    }

    public void setPremium(int premium) {
        this.premium.set(premium);
    }

    public GroupModalDto getGroup() {
        return group.get();
    }

    public ObjectProperty<GroupModalDto> groupProperty() {
        return group;
    }

    public void setGroup(GroupModalDto group) {
        GroupModalDto groupModalDto = this.group.get();
        if (groupModalDto == null) {
            this.group.set(group);
            return;
        }
        groupModalDto.setId(group.getId());
        groupModalDto.setGroupNumber(group.getGroupNumber());
    }

}
