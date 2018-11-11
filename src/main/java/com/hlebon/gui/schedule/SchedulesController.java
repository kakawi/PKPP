package com.hlebon.gui.schedule;

import com.hlebon.gui.DialogManager;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.schedule.ScheduleService;
import com.hlebon.service.schedule.ScheduleServiceImpl;
import com.hlebon.service.session.SessionService;
import com.hlebon.service.session.SessionServiceImpl;
import com.hlebon.service.setOfGroup.SetOfGroupService;
import com.hlebon.service.setOfGroup.SetOfGroupServiceImpl;
import com.hlebon.service.subject.SubjectService;
import com.hlebon.service.subject.SubjectServiceImpl;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SchedulesController implements Initializable {

    private static final String SCHEDULE_MODAL_FXML = "schedule/scheduleModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private ScheduleModalController scheduleModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private ScheduleService scheduleService = new ScheduleServiceImpl();
    private SetOfGroupService setOfGroupService = new SetOfGroupServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();
    private SubjectService subjectService = new SubjectServiceImpl();

    @FXML
    private TableView<ScheduleModalDto> tableDepartments;

    @FXML
    public TableColumn<ScheduleModalDto, Long> columnId;

    @FXML
    public TableColumn<ScheduleModalDto, String> columnSetOfGroupName;
    @FXML
    public TableColumn<ScheduleModalDto, String> columnSessionName;
    @FXML
    public TableColumn<ScheduleModalDto, String> columnSubjectName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLoader();
        initializeTable();
        refreshTable();
        initListeners();
    }

    private void initListeners() {
        tableDepartments.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                try {
                    editButtonHandler();
                } catch (WrongValues e) {
                    handleWrongValueException(e);
                } catch (Exception e) {
                    handleUncheckedException(e);
                }
            }
        });
    }

    private void handleWrongValueException(WrongValues e) {
        DialogManager.showErrorDialog(e.getMessage());
        refreshTable();
    }

    private void handleUncheckedException(Exception e) {
        DialogManager.showErrorDialog(e.getMessage());
        refreshTable();
    }

    private void initializeTable() {
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        columnSetOfGroupName.setCellValueFactory(cellData -> cellData.getValue().setOfGroupNameProperty());
        columnSessionName.setCellValueFactory(cellData -> cellData.getValue().getSession().nameProperty());
        columnSubjectName.setCellValueFactory(cellData -> cellData.getValue().getSubject().nameProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(SCHEDULE_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            scheduleModalController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        try {
            switch (clickedButton.getId()) {
                case "btnAdd": {
                    scheduleModalController.setDependencies(
                            setOfGroupService.getAll(),
                            sessionService.getAll(),
                            subjectService.getAll()
                    );
                    showDialog();
                    Optional<ScheduleModalDto> optional = scheduleModalController.getDepartment();
                    if (optional.isPresent()) {
                        scheduleService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    ScheduleModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                    if (!departmentIsSelected(selectedFaculty)) {
                        return;
                    }
                    scheduleService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        }
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(scheduleService.getAll()));
    }

    private void editButtonHandler() {
        ScheduleModalDto selectedSchedule = tableDepartments.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedSchedule)) {
            return;
        }
        scheduleModalController.setScheduleAndDependencies(
                selectedSchedule,
                setOfGroupService.getAll(),
                sessionService.getAll(),
                subjectService.getAll()
        );
        showDialog();
        Optional<ScheduleModalDto> optional = scheduleModalController.getDepartment();
        if (optional.isPresent()) {
            scheduleService.update(selectedSchedule);
        }
    }

    private boolean departmentIsSelected(ScheduleModalDto selectedFaculty) {
        if (selectedFaculty == null) {
            DialogManager.showErrorDialog("Не выбрана кафедра");
            return false;
        }
        return true;
    }

    private void showDialog() {
        if (modalDialogStage == null) {
            modalDialogStage = new Stage();
            modalDialogStage.setTitle("Редактирование");
            modalDialogStage.setMinHeight(150);
            modalDialogStage.setMinWidth(300);
            modalDialogStage.setResizable(false);
            modalDialogStage.setScene(new Scene(fxmlEdit));
            modalDialogStage.initModality(Modality.WINDOW_MODAL);
            modalDialogStage.initOwner(mainStage);
        }
        scheduleModalController.addStage(modalDialogStage);
        modalDialogStage.showAndWait();
    }

}
