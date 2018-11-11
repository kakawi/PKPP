package com.hlebon.gui.reports;

import com.hlebon.gui.session.SessionModalDto;
import com.hlebon.service.reports.ReportService;
import com.hlebon.service.reports.ReportServiceImpl;
import com.hlebon.service.session.SessionService;
import com.hlebon.service.session.SessionServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class AverageMarkBySubjectController implements Initializable {

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private SessionService sessionService = new SessionServiceImpl();
    private ReportService reportService = new ReportServiceImpl();

    @FXML
    private ChoiceBox<String> sessionsChoiceBox;
    private Map<String, SessionModalDto> sessionModalDtoMap = new HashMap<>();

    @FXML
    private TableView<AverageMarkBySubjectDto> tableAverageMarkBySubject;

    @FXML
    public TableColumn<AverageMarkBySubjectDto, String> columnSubjectName;

    @FXML
    public TableColumn<AverageMarkBySubjectDto, String> columnAverageMark;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        fillSessionChoiceBox();
    }

    private void fillSessionChoiceBox() {
        List<SessionModalDto> sessionModalDtos = sessionService.getAll();
        for (SessionModalDto sessionModalDto : sessionModalDtos) {
            String sessionName = generateSessionName(sessionModalDto);
            sessionModalDtoMap.put(sessionName, sessionModalDto);
            sessionsChoiceBox.getItems().add(sessionName);
        }
    }

    private String generateSessionName(SessionModalDto sessionModalDto) {
        return sessionModalDto.getName() + " - " + sessionModalDto.getYear();
    }

    private void initializeTable() {
        columnSubjectName.setCellValueFactory(cellData -> cellData.getValue().subjectNameProperty());
        columnAverageMark.setCellValueFactory(cellData -> cellData.getValue().averageMarkProperty());
    }

    public void actionButtonOkPressed(ActionEvent actionEvent) {
        String sessionSelectedName = sessionsChoiceBox.getSelectionModel().getSelectedItem();
        SessionModalDto sessionModalDto = sessionModalDtoMap.get(sessionSelectedName);
        Collection<AverageMarkBySubjectDto> averageMarkBySubjectForSession =
                reportService.getAverageMarkBySubjectForSession(sessionModalDto.getId().toString());

        tableAverageMarkBySubject
                .setItems(
                        FXCollections.observableArrayList(
                                averageMarkBySubjectForSession));
    }


}
