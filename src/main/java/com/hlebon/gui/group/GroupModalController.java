package com.hlebon.gui.group;

import com.hlebon.gui.DialogManager;
import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GroupModalController {

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtId;

    @FXML
    public ChoiceBox<String> setOfGroupsChoiseBox;

    private GroupModalDto groupModalDto;

    private Map<String, SetOfGroupModalDto> setOfGroupModalDtoMap = new HashMap<>();

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
        String selectedSetOfGroupName = setOfGroupsChoiseBox.getSelectionModel().getSelectedItem();
        SetOfGroupModalDto selectedSetOfGroup = setOfGroupModalDtoMap.get(selectedSetOfGroupName);
        groupModalDto.setSetOfGroup(selectedSetOfGroup);
        groupModalDto.setGroupNumber(txtName.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        String selectedItem = setOfGroupsChoiseBox.getSelectionModel().getSelectedItem();
        if (txtName.getText().trim().length() == 0
                || selectedItem == null) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setDepartmentAndDependencies(GroupModalDto groupModalDto, List<SetOfGroupModalDto> setOfGroupModalDtos) {
        this.groupModalDto = groupModalDto;
        setOfGroupsChoiseBox.getItems().clear();
        for (SetOfGroupModalDto setOfGroupModalDto : setOfGroupModalDtos) {
            String specialityName = setOfGroupModalDto.getSpeciality().getName();
            int year = setOfGroupModalDto.getYear();
            String setOfGroupName = specialityName + " - " + year;
            setOfGroupModalDtoMap.put(setOfGroupName, setOfGroupModalDto);
            setOfGroupsChoiseBox.getItems().add(setOfGroupName);
        }
        setOfGroupsChoiseBox.getSelectionModel().select(groupModalDto.getSetOfGroupName());
        txtId.setText(String.valueOf(groupModalDto.getId()));
        txtName.setText(groupModalDto.getGroupNumber());
    }

    public void setDependencies(List<SetOfGroupModalDto> setOfGroupModalDtos) {
        this.groupModalDto = new GroupModalDto();
        setOfGroupsChoiseBox.getItems().clear();
        for (SetOfGroupModalDto setOfGroupModalDto : setOfGroupModalDtos) {
            String specialityName = setOfGroupModalDto.getSpeciality().getName();
            int year = setOfGroupModalDto.getYear();
            String setOfGroupName = specialityName + " - " + year;
            setOfGroupModalDtoMap.put(setOfGroupName, setOfGroupModalDto);
            setOfGroupsChoiseBox.getItems().add(setOfGroupName);
        }
    }

    public Optional<GroupModalDto> getDepartment() {
        return Optional.ofNullable(groupModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        clearModalDto();
        actionClose(actionEvent);
    }

    private void clearModalDto() {
        groupModalDto = null;
    }

}
