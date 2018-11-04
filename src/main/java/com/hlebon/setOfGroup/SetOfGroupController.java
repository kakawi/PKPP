package com.hlebon.setOfGroup;

import com.hlebon.DialogManager;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.setOfGroup.SetOfGroupService;
import com.hlebon.service.setOfGroup.SetOfGroupServiceImpl;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SetOfGroupController implements Initializable {

    private static final String SET_OF_GROUP_MODAL_FXML = "setOfGroup/setOfGroupModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private SetOfGroupModalController setOfGroupModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private SetOfGroupService setOfGroupService = new SetOfGroupServiceImpl();
    private SpecialityService specialityService = new SpecialityServiceImpl();

    @FXML
    private TableView<SetOfGroupModalDto> tableDepartments;

    @FXML
    public TableColumn<SetOfGroupModalDto, Long> columnId;

    @FXML
    public TableColumn<SetOfGroupModalDto, Number> columnYear;

    @FXML
    public TableColumn<SetOfGroupModalDto, String> columnSpecialityName;

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

    private void initializeTable() {
        columnId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        columnYear.setCellValueFactory(cellData -> cellData.getValue().yearProperty());
        columnSpecialityName.setCellValueFactory(cellData -> cellData.getValue().specialityProperty().getValue().nameProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(SET_OF_GROUP_MODAL_FXML));
//            fxmlLoader.setResources(ResourceBundle.getBundle(Main.BUNDLES_FOLDER, LocaleManager.getCurrentLang().getLocale()));
            fxmlEdit = fxmlLoader.load();
            setOfGroupModalController = fxmlLoader.getController();
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
                    setOfGroupModalController.setDependencies(specialityService.getAll());
                    showDialog();
                    Optional<SetOfGroupModalDto> optional = setOfGroupModalController.getSetOfGroup();
                    if (optional.isPresent()) {
                        setOfGroupService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    SetOfGroupModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                    if (!departmentIsSelected(selectedFaculty)) {
                        return;
                    }
                    setOfGroupService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        } catch (Exception e) {
            handleUncheckedException(e);
        }
    }

    private void handleUncheckedException(Exception e) {
        DialogManager.showErrorDialog(e.getMessage());
        refreshTable();
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(setOfGroupService.getAll()));
    }

    private void editButtonHandler() {
        SetOfGroupModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedFaculty)) {
            return;
        }
        setOfGroupModalController.setSetOfGroupAndDependencies(selectedFaculty, specialityService.getAll());
        showDialog();
        Optional<SetOfGroupModalDto> optional = setOfGroupModalController.getSetOfGroup();
        if (optional.isPresent()) {
            setOfGroupService.update(selectedFaculty);
        }
    }

    private boolean departmentIsSelected(SetOfGroupModalDto selectedFaculty) {
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
