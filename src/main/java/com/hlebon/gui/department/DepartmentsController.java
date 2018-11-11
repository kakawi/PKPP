package com.hlebon.gui.department;

import com.hlebon.gui.DialogManager;
import com.hlebon.service.department.DepartmentService;
import com.hlebon.service.department.DepartmentServiceImpl;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.faculty.FacultyService;
import com.hlebon.service.faculty.FacultyServiceImpl;
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

public class DepartmentsController implements Initializable {

    private static final String DEPARTMENT_MODAL_FXML = "department/departmentModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private DepartmentModalController departmentModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private FacultyService facultyService = new FacultyServiceImpl();

    @FXML
    private TableView<DepartmentModalDto> tableDepartments;

    @FXML
    public TableColumn<DepartmentModalDto, Long> columnId;

    @FXML
    public TableColumn<DepartmentModalDto, String> columnName;
    @FXML
    public TableColumn<DepartmentModalDto, String> columnFacultyName;

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
        columnFacultyName.setCellValueFactory(cellData -> cellData.getValue().facultyProperty().getValue().nameProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(DEPARTMENT_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            departmentModalController = fxmlLoader.getController();
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
                    departmentModalController.setDependencies(facultyService.getAll());
                    showDialog();
                    Optional<DepartmentModalDto> optional = departmentModalController.getDepartment();
                    if (optional.isPresent()) {
                        departmentService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    DepartmentModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                    if (!departmentIsSelected(selectedFaculty)) {
                        return;
                    }
                    departmentService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        }
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(departmentService.getAll()));
    }

    private void editButtonHandler() {
        DepartmentModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedFaculty)) {
            return;
        }
        departmentModalController.setDepartmentAndDependencies(selectedFaculty, facultyService.getAll());
        showDialog();
        Optional<DepartmentModalDto> optional = departmentModalController.getDepartment();
        if (optional.isPresent()) {
            departmentService.update(selectedFaculty);
        }
    }

    private boolean departmentIsSelected(DepartmentModalDto selectedFaculty) {
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
        departmentModalController.addStage(modalDialogStage);
        modalDialogStage.showAndWait();
    }

}
