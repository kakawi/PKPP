package com.hlebon.gui.student;

import com.hlebon.gui.DialogManager;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.group.GroupService;
import com.hlebon.service.group.GroupServiceImpl;
import com.hlebon.service.student.StudentService;
import com.hlebon.service.student.StudentServiceImpl;
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

public class StudentsController implements Initializable {

    private static final String STUDENT_MODAL_FXML = "student/studentModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private StudentModalController studentModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private StudentService studentService = new StudentServiceImpl();
    private GroupService groupService = new GroupServiceImpl();

    @FXML
    private TableView<StudentModalDto> tableDepartments;

    @FXML
    public TableColumn<StudentModalDto, Long> columnId;

    @FXML
    public TableColumn<StudentModalDto, String> columnFullName;

    @FXML
    public TableColumn<StudentModalDto, String> columnGroupNumber;

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
        columnFullName.setCellValueFactory(cellData -> cellData.getValue().fullNameProperty());
        columnGroupNumber.setCellValueFactory(cellData -> cellData.getValue().groupProperty().getValue().groupNumberProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(STUDENT_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            studentModalController = fxmlLoader.getController();
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
                    studentModalController.setDependencies(groupService.getAll());
                    showDialog();
                    Optional<StudentModalDto> optional = studentModalController.getStudent();
                    if (optional.isPresent()) {
                        studentService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    StudentModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                    if (!departmentIsSelected(selectedFaculty)) {
                        return;
                    }
                    studentService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        }
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(studentService.getAll()));
    }

    private void editButtonHandler() {
        StudentModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedFaculty)) {
            return;
        }
        studentModalController.setStudentAndDependencies(selectedFaculty, groupService.getAll());
        showDialog();
        Optional<StudentModalDto> optional = studentModalController.getStudent();
        if (optional.isPresent()) {
            studentService.update(selectedFaculty);
        }
    }

    private boolean departmentIsSelected(StudentModalDto selectedFaculty) {
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
        modalDialogStage.showAndWait(); // для ожидания закрытия окна

    }
}
