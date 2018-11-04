package com.hlebon.gui.department;

import com.hlebon.gui.DialogManager;
import com.hlebon.gui.faculty.FacultyModalDto;
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

public class DepartmentModalController {

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtId;

    @FXML
    public ChoiceBox<String> facultiesChoiceBox;

    private DepartmentModalDto departmentModalDto;

    private Map<String, FacultyModalDto> facultyModalDtoMap = new HashMap<>();

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        String selectedFacultyName = facultiesChoiceBox.getSelectionModel().getSelectedItem();
        FacultyModalDto selectedFaculty = facultyModalDtoMap.get(selectedFacultyName);
        departmentModalDto.setFaculty(selectedFaculty);
        departmentModalDto.setName(txtName.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        String selectedItem = facultiesChoiceBox.getSelectionModel().getSelectedItem();
        if (txtName.getText().trim().length() == 0
                || selectedItem == null) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setDepartmentAndDependencies(DepartmentModalDto department, List<FacultyModalDto> facultyModalDtos) {
        this.departmentModalDto = department;
        facultiesChoiceBox.getItems().clear();
        for (FacultyModalDto facultyModalDto : facultyModalDtos) {
            String facultyName = facultyModalDto.getName();
            facultyModalDtoMap.put(facultyName, facultyModalDto);
            facultiesChoiceBox.getItems().add(facultyName);
        }
        facultiesChoiceBox.getSelectionModel().select(department.getFaculty().getName());
        txtId.setText(String.valueOf(department.getId()));
        txtName.setText(department.getName());
    }

    public void setDependencies(List<FacultyModalDto> facultyModalDtos) {
        this.departmentModalDto = new DepartmentModalDto();
        facultiesChoiceBox.getItems().clear();
        for (FacultyModalDto facultyModalDto : facultyModalDtos) {
            String facultyName = facultyModalDto.getName();
            facultyModalDtoMap.put(facultyName, facultyModalDto);
            facultiesChoiceBox.getItems().add(facultyName);
        }
    }

    public Optional<DepartmentModalDto> getDepartment() {
        return Optional.ofNullable(departmentModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        departmentModalDto = null;
        actionClose(actionEvent);
    }
}
