package com.hlebon.gui.reports;

import com.hlebon.service.reports.ReportService;
import com.hlebon.service.reports.ReportServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

public class CountSubjectsInSessionController implements Initializable {

    private ReportService reportService = new ReportServiceImpl();

    @FXML
    private TableView<CountSubjectsInSessionDto> tableCountSubjectsInSession;

    @FXML
    public TableColumn<CountSubjectsInSessionDto, String> columnSessionName;

    @FXML
    public TableColumn<CountSubjectsInSessionDto, Number> columnSessionYear;

    @FXML
    public TableColumn<CountSubjectsInSessionDto, Number> columnCountSubjects;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
        refreshTable();
    }

    private void initializeTable() {
        columnSessionName.setCellValueFactory(cellData -> cellData.getValue().sessionNameProperty());
        columnSessionYear.setCellValueFactory(cellData -> cellData.getValue().sessionYearProperty());
        columnCountSubjects.setCellValueFactory(cellData -> cellData.getValue().countOfSubjectsProperty());
    }

    public void actionButtonRefreshPressed(ActionEvent actionEvent) {
        refreshTable();
    }

    private void refreshTable() {
        Collection<CountSubjectsInSessionDto> countSubjectsInSessionDtos = reportService.getCountSubjectsInSession();

        tableCountSubjectsInSession
                .setItems(
                        FXCollections.observableArrayList(
                                countSubjectsInSessionDtos));
    }

}
