package com.hlebon.gui.student;

import com.hlebon.gui.DialogManager;
import com.hlebon.gui.group.GroupModalDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StudentModalController {

    @FXML
    public TextField txtId;

    @FXML
    public TextField txtLastName;
    @FXML
    public TextField txtFirstName;
    @FXML
    public TextField txtMiddleName;
    @FXML
    public TextField txtRecordBook;
    @FXML
    public TextField txtAddressOfPermanentResidence;
    @FXML
    public TextField txtAddressOfResidence;
    @FXML
    public CheckBox bIsGetScholarship;
    @FXML
    public TextField txtPremium;
    @FXML
    public CheckBox bIsLocal;

    @FXML
    public ChoiceBox<String> groupsChoiceBox;

    private StudentModalDto studentModalDto;

    private Map<String, GroupModalDto> groupModalDtoMap = new HashMap<>();

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void addStage(Stage stage) {
        stage.setOnCloseRequest(we -> clearModalDto());
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        String selectedGroupName = groupsChoiceBox.getSelectionModel().getSelectedItem();
        GroupModalDto selectedGroup = groupModalDtoMap.get(selectedGroupName);
        studentModalDto.setGroup(selectedGroup);
        studentModalDto.setLastName(txtLastName.getText());
        studentModalDto.setFirstName(txtFirstName.getText());
        studentModalDto.setMiddleName(txtMiddleName.getText());
        studentModalDto.setRecordBook(txtRecordBook.getText());
        studentModalDto.setAddressOfPermanentResidence(txtAddressOfPermanentResidence.getText());
        studentModalDto.setAddressOfResidence(txtAddressOfResidence.getText());
        studentModalDto.setIsGetScholarship(bIsGetScholarship.isSelected());
        studentModalDto.setPremium(Integer.valueOf(txtPremium.getText()));
        studentModalDto.setIsLocal(bIsLocal.isSelected());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        String lastName = txtLastName.getText();
        String firstName = txtFirstName.getText();
        String recordBook = txtRecordBook.getText();

        String selectedItem = groupsChoiceBox.getSelectionModel().getSelectedItem();
        if (lastName.trim().length() == 0
                || firstName.trim().length() == 0
                || recordBook.trim().length() == 0
                || selectedItem == null) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setStudentAndDependencies(StudentModalDto studentModalDto, List<GroupModalDto> groupModalDtos) {
        this.studentModalDto = studentModalDto;
        refreshDependencies(groupModalDtos);
        groupsChoiceBox.getSelectionModel().select(studentModalDto.getGroup().getGroupNumber());
        txtId.setText(String.valueOf(studentModalDto.getId()));
        txtLastName.setText(studentModalDto.getLastName());
        txtFirstName.setText(studentModalDto.getFirstName());
        txtMiddleName.setText(studentModalDto.getMiddleName());
        txtRecordBook.setText(studentModalDto.getRecordBook());
        txtAddressOfPermanentResidence.setText(studentModalDto.getAddressOfPermanentResidence());
        txtAddressOfResidence.setText(studentModalDto.getAddressOfResidence());
        txtPremium.setText(String.valueOf(studentModalDto.getPremium()));
        bIsGetScholarship.setSelected(studentModalDto.isGetScholarship());
        bIsLocal.setSelected(studentModalDto.isLocal());

    }

    public void setDependencies(List<GroupModalDto> groupModalDtos) {
        this.studentModalDto = new StudentModalDto();
        refreshDependencies(groupModalDtos);
    }

    private void refreshDependencies(List<GroupModalDto> groupModalDtos) {
        groupsChoiceBox.getItems().clear();
        for (GroupModalDto groupModalDto : groupModalDtos) {
            String groupNumber = groupModalDto.getGroupNumber();
            groupModalDtoMap.put(groupNumber, groupModalDto);
            groupsChoiceBox.getItems().add(groupNumber);
        }
    }

    public Optional<StudentModalDto> getStudent() {
        return Optional.ofNullable(studentModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        clearModalDto();
        actionClose(actionEvent);
    }

    private void clearModalDto() {
        studentModalDto = null;
    }

}
