package com.hlebon.group;

import com.hlebon.DialogManager;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.group.GroupService;
import com.hlebon.service.group.GroupServiceImpl;
import com.hlebon.service.setOfGroup.SetOfGroupService;
import com.hlebon.service.setOfGroup.SetOfGroupServiceImpl;
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

public class GroupController implements Initializable {

    private static final String GROUP_MODAL_FXML = "group/groupModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private GroupModalController groupModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private GroupService groupService = new GroupServiceImpl();
    private SetOfGroupService setOfGroupService = new SetOfGroupServiceImpl();

    @FXML
    private TableView<GroupModalDto> tableDepartments;

    @FXML
    public TableColumn<GroupModalDto, Long> columnId;

    @FXML
    public TableColumn<GroupModalDto, String> columnNumber;

    @FXML
    public TableColumn<GroupModalDto, String> columnSetOfGroupName;

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
        columnNumber.setCellValueFactory(cellData -> cellData.getValue().groupNumberProperty());
        columnSetOfGroupName.setCellValueFactory(cellData -> cellData.getValue().setOfGroupNameProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(GROUP_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            groupModalController = fxmlLoader.getController();
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
                    groupModalController.setDependencies(setOfGroupService.getAll());
                    showDialog();
                    Optional<GroupModalDto> optional = groupModalController.getDepartment();
                    if (optional.isPresent()) {
                        groupService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    GroupModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
                    if (!departmentIsSelected(selectedFaculty)) {
                        return;
                    }
                    groupService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        }
    }

    private void refreshTable() {
        tableDepartments.setItems(FXCollections.observableArrayList(groupService.getAll()));
    }

    private void editButtonHandler() {
        GroupModalDto selectedFaculty = tableDepartments.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedFaculty)) {
            return;
        }
        groupModalController.setDepartmentAndDependencies(selectedFaculty, setOfGroupService.getAll());
        showDialog();
        Optional<GroupModalDto> optional = groupModalController.getDepartment();
        if (optional.isPresent()) {
            groupService.update(selectedFaculty);
        }
    }

    private boolean departmentIsSelected(GroupModalDto selectedFaculty) {
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
