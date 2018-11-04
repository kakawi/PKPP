package com.hlebon.subject;

import com.hlebon.DialogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class SubjectModalController {

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtId;

    private SubjectModalDto subjectModalDto;

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        subjectModalDto.setName(txtName.getText());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        if (txtName.getText().trim().length() == 0) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setSubject(SubjectModalDto subjectModalDto) {
        this.subjectModalDto = subjectModalDto;
        txtId.setText(String.valueOf(subjectModalDto.getId()));
        txtName.setText(subjectModalDto.getName());
    }

    public Optional<SubjectModalDto> getSubject() {
        return Optional.ofNullable(subjectModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        subjectModalDto = null;
        actionClose(actionEvent);
    }

    public void setDependencies() {
        this.subjectModalDto = new SubjectModalDto();
    }

}
