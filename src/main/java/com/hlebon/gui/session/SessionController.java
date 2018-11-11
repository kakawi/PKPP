package com.hlebon.gui.session;

import com.hlebon.gui.DialogManager;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.session.SessionService;
import com.hlebon.service.session.SessionServiceImpl;
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

public class SessionController implements Initializable {

    private static final String SESSION_MODAL_FXML = "session/sessionModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private SessionModalController sessionModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private SessionService sessionService = new SessionServiceImpl();

    @FXML
    private TableView<SessionModalDto> tableDepartments;

    @FXML
    public TableColumn<SessionModalDto, Long> columnId;

    @FXML
    public TableColumn<SessionModalDto, String> columnName;

    @FXML
    public TableColumn<SessionModalDto, Number> columnYear;

    @FXML
    public TableColumn<SessionModalDto, Boolean> columnIsFinished;

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
        columnName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        columnYear.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        columnIsFinished.setCellValueFactory(cellData -> cellData.getValue().isFinishedProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(SESSION_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            sessionModalController = fxmlLoader.getController();
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
                    sessionModalController.setDependencies();
                    showDialog();
                    Optional<SessionModalDto> optional = sessionModalController.getSubject();
                    if (optional.isPresent()) {
                        sessionService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    SessionModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                    if (!sessionIsSelected(selectedFaculty)) {
                        return;
                    }
                    sessionService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        }
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(sessionService.getAll()));
    }

    private void editButtonHandler() {
        SessionModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
        if (!sessionIsSelected(selectedFaculty)) {
            return;
        }
        sessionModalController.setSession(selectedFaculty);
        showDialog();
        Optional<SessionModalDto> optional = sessionModalController.getSubject();
        if (optional.isPresent()) {
            sessionService.update(selectedFaculty);
        }
    }

    private boolean sessionIsSelected(SessionModalDto selectedFaculty) {
        if (selectedFaculty == null) {
            DialogManager.showErrorDialog("Не выбрана сессия");
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
        sessionModalController.addStage(modalDialogStage);
        modalDialogStage.showAndWait(); // для ожидания закрытия окна
    }

}
