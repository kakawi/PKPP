package com.hlebon.setOfGroup;

import com.hlebon.DialogManager;
import com.hlebon.speciality.SpecialityModalDto;
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

public class SetOfGroupModalController {

    @FXML
    public TextField txtYear;

    @FXML
    public TextField txtId;

    @FXML
    public ChoiceBox<String> specialitiesChoiseBox;

    private SetOfGroupModalDto setOfGroupModalDto;

    private Map<String, SpecialityModalDto> specialityModalDtoMap = new HashMap<>();

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        String selectedSpecialityName = specialitiesChoiseBox.getSelectionModel().getSelectedItem();
        SpecialityModalDto selectedFaculty = specialityModalDtoMap.get(selectedSpecialityName);
        setOfGroupModalDto.setSpeciality(selectedFaculty);
        setOfGroupModalDto.setYear(Integer.valueOf(txtYear.getText()));
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        String selectedItem = specialitiesChoiseBox.getSelectionModel().getSelectedItem();
        String text = txtYear.getText();
        // TODO validate year
        if (text.trim().length() == 0 || selectedItem == null) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setSetOfGroupAndDependencies(SetOfGroupModalDto setOfGroupModalDto, List<SpecialityModalDto> specialityModalDtos) {
        this.setOfGroupModalDto = setOfGroupModalDto;
        specialitiesChoiseBox.getItems().clear();
        for (SpecialityModalDto specialityModalDto : specialityModalDtos) {
            String facultyName = specialityModalDto.getName();
            specialityModalDtoMap.put(facultyName, specialityModalDto);
            specialitiesChoiseBox.getItems().add(facultyName);
        }
        specialitiesChoiseBox.getSelectionModel().select(setOfGroupModalDto.getSpeciality().getName());
        txtId.setText(String.valueOf(setOfGroupModalDto.getId()));
        txtYear.setText(String.valueOf(setOfGroupModalDto.getYear()));
    }

    public void setDependencies(List<SpecialityModalDto> specialityModalDtosModalDtos) {
        this.setOfGroupModalDto = new SetOfGroupModalDto();
        specialitiesChoiseBox.getItems().clear();
        for (SpecialityModalDto specialityModalDto : specialityModalDtosModalDtos) {
            String specialityName = specialityModalDto.getName();
            specialityModalDtoMap.put(specialityName, specialityModalDto);
            specialitiesChoiseBox.getItems().add(specialityName);
        }
    }

    public Optional<SetOfGroupModalDto> getSetOfGroup() {
        return Optional.ofNullable(setOfGroupModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        setOfGroupModalDto = null;
        actionClose(actionEvent);
    }
}
