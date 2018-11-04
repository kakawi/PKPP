package com.hlebon.gui.speciality;

import com.hlebon.gui.DialogManager;
import com.hlebon.gui.department.DepartmentModalDto;
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

public class SpecialityModalController {

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtId;

    @FXML
    public ChoiceBox<String> departmentsChoiseBox;

    private SpecialityModalDto specialityModalDto;

    private Map<String, DepartmentModalDto> departmentModalDtoMap = new HashMap<>();

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        String selectedDepartment = departmentsChoiseBox.getSelectionModel().getSelectedItem();
        specialityModalDto.setDepartment(departmentModalDtoMap.get(selectedDepartment));
        specialityModalDto.setName(txtName.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (txtName.getText().trim().length() == 0) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setDepartment(SpecialityModalDto department, List<DepartmentModalDto> departmentModalDtos) {
        this.specialityModalDto = department;
        departmentsChoiseBox.getItems().clear();
        for (DepartmentModalDto departmentModalDto : departmentModalDtos) {
            String departmentName = departmentModalDto.getName();
            departmentModalDtoMap.put(departmentName, departmentModalDto);
            departmentsChoiseBox.getItems().add(departmentName);
        }
        departmentsChoiseBox.getSelectionModel().select(department.getDepartment().getName());
        txtId.setText(String.valueOf(department.getId()));
        txtName.setText(department.getName());
    }

    public Optional<SpecialityModalDto> getDepartment() {
        return Optional.ofNullable(specialityModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        specialityModalDto = null;
        actionClose(actionEvent);
    }
}
