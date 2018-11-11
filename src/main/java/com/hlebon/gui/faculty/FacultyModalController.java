package com.hlebon.gui.faculty;

import com.hlebon.gui.DialogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class FacultyModalController {

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtId;

    private FacultyModalDto faculty;

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
        faculty.setName(txtName.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (txtName.getText().trim().length() == 0) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setFaculty(FacultyModalDto faculty) {
        this.faculty = faculty;
        txtId.setText(String.valueOf(faculty.getId()));
        txtName.setText(faculty.getName());
    }

    public Optional<FacultyModalDto> getFaculty() {
        return Optional.ofNullable(faculty);
    }

    public void actionCancel(ActionEvent actionEvent) {
        clearModalDto();
        actionClose(actionEvent);
    }

    private void clearModalDto() {
        faculty = null;
    }

}
