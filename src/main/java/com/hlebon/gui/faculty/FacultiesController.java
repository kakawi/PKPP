package com.hlebon.gui.faculty;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FacultiesController implements Initializable {

    private static final String FACULTY_MODAL_FXML = "faculty/facultyModal.fxml";

    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private FacultyModalController facultyModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private FacultyService facultyService = new FacultyServiceImpl();

    @FXML
    private TableView<FacultyModalDto> tableFaculties;
    @FXML
    public TableColumn<FacultyModalDto, Long> columnId;

    @FXML
    public TableColumn<FacultyModalDto, String> columnName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLoader();
        initializeTable();
        refreshTable();
        initListeners();
    }

    private void initializeTable() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void initListeners() {
        tableFaculties.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                editButtonHandler();
            }
        });
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(FACULTY_MODAL_FXML));
//            fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
            fxmlEdit = fxmlLoader.load();
            facultyModalController = fxmlLoader.getController();
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
                facultyModalController.setFaculty(new FacultyModalDto());
                showDialog();
                Optional<FacultyModalDto> optional = facultyModalController.getFaculty();
                if (optional.isPresent()) {
                    facultyService.add(optional.get());
                }
                refreshTable();
                break;
            }
            case "btnEdit": {
                editButtonHandler();
                break;
            }
            case "btnDelete": {
                FacultyModalDto selectedFaculty = tableFaculties.getSelectionModel().getSelectedItem();
                if (!facultyIsSelected(selectedFaculty)) {
                    return;
                }
                facultyService.delete(selectedFaculty);
                refreshTable();
                break;
            }
        }
    }

    private void refreshTable() {
        tableFaculties.setItems(FXCollections.observableArrayList(facultyService.getAll()));
    }

    private void editButtonHandler() {
        FacultyModalDto selectedFaculty = tableFaculties.getSelectionModel().getSelectedItem();
        if (!facultyIsSelected(selectedFaculty)) {
            return;
        }
        facultyModalController.setFaculty(selectedFaculty);
        showDialog();
        Optional<FacultyModalDto> optional = facultyModalController.getFaculty();
        if (optional.isPresent()) {
            facultyService.update(selectedFaculty);
        }
    }

    private boolean facultyIsSelected(FacultyModalDto selectedFaculty) {
        if (selectedFaculty == null) {
//            DialogManager.showInfoDialog(resourceBundle.getString("error"), resourceBundle.getString("select_person"));
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
