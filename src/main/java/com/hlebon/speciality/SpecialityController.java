package com.hlebon.speciality;

import com.hlebon.DialogManager;
import com.hlebon.service.department.DepartmentService;
import com.hlebon.service.department.DepartmentServiceImpl;
import com.hlebon.service.speciality.SpecialityService;
import com.hlebon.service.speciality.SpecialityServiceImpl;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SpecialityController implements Initializable {

    private static final String SPECIALITY_MODAL_FXML = "speciality/specialityModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private SpecialityModalController specialityModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private SpecialityService specialityService = new SpecialityServiceImpl();
    private DepartmentService departmentService = new DepartmentServiceImpl();

    @FXML
    private TableView<SpecialityModalDto> tableDepartments;

    @FXML
    public TableColumn<SpecialityModalDto, Long> columnId;

    @FXML
    public TableColumn<SpecialityModalDto, String> columnName;

    @FXML
    public TableColumn<SpecialityModalDto, String> columnDepartmentName;

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
                editButtonHandler();
            }
        });
    }

    private void initializeTable() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDepartmentName.setCellValueFactory(new PropertyValueFactory<>("departmentName"));
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(SPECIALITY_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            specialityModalController = fxmlLoader.getController();
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

        switch (clickedButton.getId()) {
            case "btnAdd": {
                specialityModalController.setDepartment(new SpecialityModalDto(), departmentService.getAll());
                showDialog();
                Optional<SpecialityModalDto> optional = specialityModalController.getDepartment();
                if (optional.isPresent()) {
                    specialityService.add(optional.get());
                    refreshTable();
                }
                break;
            }
            case "btnEdit": {
                editButtonHandler();
                break;
            }
            case "btnDelete": {
                SpecialityModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                if (!departmentIsSelected(selectedFaculty)) {
                    return;
                }
                specialityService.delete(selectedFaculty);
                refreshTable();
                break;
            }
        }
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(specialityService.getAll()));
    }

    private void editButtonHandler() {
        SpecialityModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedFaculty)) {
            return;
        }
        specialityModalController.setDepartment(selectedFaculty, departmentService.getAll());
        showDialog();
        Optional<SpecialityModalDto> optional = specialityModalController.getDepartment();
        if (optional.isPresent()) {
            specialityService.update(selectedFaculty);
        }
    }

    private boolean departmentIsSelected(SpecialityModalDto selectedFaculty) {
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
