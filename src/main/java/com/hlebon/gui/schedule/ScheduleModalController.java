package com.hlebon.gui.schedule;

import com.hlebon.gui.DialogManager;
import com.hlebon.gui.session.SessionModalDto;
import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;
import com.hlebon.gui.subject.SubjectModalDto;
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

public class ScheduleModalController {

    @FXML
    public TextField txtId;

    @FXML
    public ChoiceBox<String> setOfGroupsChoiceBox;

    @FXML
    public ChoiceBox<String> sessionsChoiceBox;

    @FXML
    public ChoiceBox<String> subjectsChoiceBox;

    private ScheduleModalDto scheduleModalDto;

    private Map<String, SetOfGroupModalDto> setOfGroupModalDtoMap = new HashMap<>();
    private Map<String, SessionModalDto> sessionModalDtoMap = new HashMap<>();
    private Map<String, SubjectModalDto> subjectModalDtoMap = new HashMap<>();

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }


    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        String setOfGroupsSelectedName = setOfGroupsChoiceBox.getSelectionModel().getSelectedItem();
        String sessionSelectedName = sessionsChoiceBox.getSelectionModel().getSelectedItem();
        String subjectSelectedName = subjectsChoiceBox.getSelectionModel().getSelectedItem();
        SetOfGroupModalDto setOfGroupModalDto = setOfGroupModalDtoMap.get(setOfGroupsSelectedName);
        SessionModalDto sessionModalDto = sessionModalDtoMap.get(sessionSelectedName);
        SubjectModalDto subjectModalDto = subjectModalDtoMap.get(subjectSelectedName);
        scheduleModalDto.setSetOfGroup(setOfGroupModalDto);
        scheduleModalDto.setSession(sessionModalDto);
        scheduleModalDto.setSubject(subjectModalDto);

        actionClose(actionEvent);
    }

    private boolean checkValues() {
        String setOfGroupsSelectedItem = setOfGroupsChoiceBox.getSelectionModel().getSelectedItem();
        String sessionSelectedItem = sessionsChoiceBox.getSelectionModel().getSelectedItem();
        String subjectSelectedItem = subjectsChoiceBox.getSelectionModel().getSelectedItem();
        if (setOfGroupsSelectedItem == null || sessionSelectedItem == null || subjectSelectedItem == null) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setScheduleAndDependencies(
            ScheduleModalDto scheduleModalDto,
            List<SetOfGroupModalDto> setOfGroupModalDtos,
            List<SessionModalDto> sessionModalDtos,
            List<SubjectModalDto> subjectModalDtos
    ) {
        this.scheduleModalDto = scheduleModalDto;
        refreshSetOfGroupsChoiceBox(setOfGroupModalDtos);
        refreshSesionChoiceBox(sessionModalDtos);
        refreshSubjectChoiceBox(subjectModalDtos);

        setOfGroupsChoiceBox.getSelectionModel().select(scheduleModalDto.getSetOfGroup().getSetOfGroupName());
        sessionsChoiceBox.getSelectionModel().select(scheduleModalDto.getSession().getName());
        subjectsChoiceBox.getSelectionModel().select(scheduleModalDto.getSubject().getName());
        txtId.setText(String.valueOf(scheduleModalDto.getId()));
    }

    private void refreshSetOfGroupsChoiceBox(List<SetOfGroupModalDto> setOfGroupModalDtos) {
        setOfGroupsChoiceBox.getItems().clear();
        for (SetOfGroupModalDto setOfGroupModalDto : setOfGroupModalDtos) {
            String setOfGroupName = setOfGroupModalDto.getSetOfGroupName();
            setOfGroupModalDtoMap.put(setOfGroupName, setOfGroupModalDto);
            setOfGroupsChoiceBox.getItems().add(setOfGroupName);
        }
    }

    private void refreshSesionChoiceBox(List<SessionModalDto> sessionModalDtos) {
        sessionsChoiceBox.getItems().clear();
        for (SessionModalDto sessionModalDto : sessionModalDtos) {
            String sessionName = sessionModalDto.getName();
            sessionModalDtoMap.put(sessionName, sessionModalDto);
            sessionsChoiceBox.getItems().add(sessionName);
        }
    }

    private void refreshSubjectChoiceBox(List<SubjectModalDto> subjectModalDtos) {
        subjectsChoiceBox.getItems().clear();
        for (SubjectModalDto subjectModalDto : subjectModalDtos) {
            String subjectName = subjectModalDto.getName();
            subjectModalDtoMap.put(subjectName, subjectModalDto);
            subjectsChoiceBox.getItems().add(subjectName);
        }
    }

    public void setDependencies(List<SetOfGroupModalDto> setOfGroupModalDtos,
                                List<SessionModalDto> sessionModalDtos,
                                List<SubjectModalDto> subjectModalDtos) {
        this.scheduleModalDto = new ScheduleModalDto();
        refreshSetOfGroupsChoiceBox(setOfGroupModalDtos);
        refreshSesionChoiceBox(sessionModalDtos);
        refreshSubjectChoiceBox(subjectModalDtos);
    }

    public Optional<ScheduleModalDto> getDepartment() {
        return Optional.ofNullable(scheduleModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        scheduleModalDto = null;
        actionClose(actionEvent);
    }
}
