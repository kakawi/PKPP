package com.hlebon.session;

import com.hlebon.DialogManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class SessionModalController {

    @FXML
    public TextField txtName;

    @FXML
    public TextField txtId;

    @FXML
    public TextField txtYear;

    @FXML
    public CheckBox bFinished;

    private SessionModalDto sessionModalDto;

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        sessionModalDto.setName(txtName.getText());
        sessionModalDto.setYear(Integer.valueOf(txtYear.getText()));
        sessionModalDto.setIsFinished(bFinished.isSelected());
        actionClose(actionEvent);
    }

    private boolean checkValues() {
        // TODO validate YEAR
        if (txtName.getText().trim().length() == 0
                || txtYear.getText().trim().length() == 0
                ) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setSession(SessionModalDto sessionModalDto) {
        this.sessionModalDto = sessionModalDto;
        txtId.setText(String.valueOf(sessionModalDto.getId()));
        txtName.setText(sessionModalDto.getName());
        txtYear.setText(String.valueOf(sessionModalDto.getYear()));
        bFinished.setSelected(sessionModalDto.isIsFinished());
    }

    public Optional<SessionModalDto> getSubject() {
        return Optional.ofNullable(sessionModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        sessionModalDto = null;
        actionClose(actionEvent);
    }

    public void setDependencies() {
        this.sessionModalDto = new SessionModalDto();
    }

}
