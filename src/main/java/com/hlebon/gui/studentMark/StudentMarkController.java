package com.hlebon.gui.studentMark;

import com.hlebon.gui.DialogManager;
import com.hlebon.service.department.DepartmentService;
import com.hlebon.service.department.DepartmentServiceImpl;
import com.hlebon.service.exception.WrongValues;
import com.hlebon.service.faculty.FacultyService;
import com.hlebon.service.faculty.FacultyServiceImpl;
import com.hlebon.service.group.GroupService;
import com.hlebon.service.group.GroupServiceImpl;
import com.hlebon.service.schedule.ScheduleService;
import com.hlebon.service.schedule.ScheduleServiceImpl;
import com.hlebon.service.session.SessionService;
import com.hlebon.service.session.SessionServiceImpl;
import com.hlebon.service.setOfGroup.SetOfGroupService;
import com.hlebon.service.setOfGroup.SetOfGroupServiceImpl;
import com.hlebon.service.speciality.SpecialityService;
import com.hlebon.service.speciality.SpecialityServiceImpl;
import com.hlebon.service.student.StudentService;
import com.hlebon.service.student.StudentServiceImpl;
import com.hlebon.service.studentMark.StudentMarkService;
import com.hlebon.service.studentMark.StudentMarkServiceImpl;
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

public class StudentMarkController implements Initializable {

    private static final String DEPARTMENT_MODAL_FXML = "studentMark/studentMarkModal.fxml";
    private FXMLLoader fxmlLoader = new FXMLLoader();

    private Parent fxmlEdit;
    private StudentMarkModalController studentMarkModalController;
    private Stage modalDialogStage;
    private Stage mainStage;
    private StudentMarkService studentMarkService = new StudentMarkServiceImpl();
    private FacultyService facultyService = new FacultyServiceImpl();
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private SpecialityService specialityService = new SpecialityServiceImpl();
    private SetOfGroupService setOfGroupService = new SetOfGroupServiceImpl();
    private GroupService groupService = new GroupServiceImpl();
    private SessionService sessionService = new SessionServiceImpl();
    private SubjectService subjectService = new SubjectServiceImpl();
    private StudentService studentService = new StudentServiceImpl();
    private ScheduleService scheduleService = new ScheduleServiceImpl();

    @FXML
    private TableView<StudentMarkModalDto> tableStudentMarks;

    @FXML
    public TableColumn<StudentMarkModalDto, Long> columnId;

    @FXML
    public TableColumn<StudentMarkModalDto, String> columnStudentFullName;
    @FXML
    public TableColumn<StudentMarkModalDto, String> columnScheduleName;

    @FXML
    public TableColumn<StudentMarkModalDto, Number> columnMark;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLoader();
        initializeTable();
        refreshTable();
        initListeners();
        addGenerators();
    }

    private void addGenerators() {
        studentMarkModalController.setGenerateDepartment(facultyDto -> departmentService.getByFaculty(facultyDto.getId()));
        studentMarkModalController.setGenerateSpeciality(departmentDto -> specialityService.getByDepartment(departmentDto.getId()));
        studentMarkModalController.setGenerateSetOfGroup(specialityDto -> setOfGroupService.getBySpeciality(specialityDto.getId()));
        studentMarkModalController.setGenerateGroup(setOfGroupDto -> groupService.getBySetOfGroup(setOfGroupDto.getId()));

        studentMarkModalController.setGenerateSession(groupModalDto -> sessionService.getBySetOfGroup(groupModalDto.getId()));
        studentMarkModalController.setGenerateSubject((sessionModalDto, setOfGroupModalDto) -> subjectService.getBySessionAndSetOfGroup(sessionModalDto.getId(), setOfGroupModalDto.getId()));
        studentMarkModalController.setGenerateStudent(groupModalDto -> studentService.getByGroup(groupModalDto.getId()));
        studentMarkModalController.setGenerateScheduleModalDto((sessionModalDto, setOfGroupModalDto, subjectModalDto) -> scheduleService.getBySessionAndSetOfGroupAndSubject(sessionModalDto.getId(), setOfGroupModalDto.getId(), subjectModalDto.getId()));
    }

    private void initListeners() {
        tableStudentMarks.setOnMouseClicked(event -> {
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
        columnStudentFullName.setCellValueFactory(cellData -> cellData.getValue().getStudent().fullNameProperty());
        columnScheduleName.setCellValueFactory(cellData -> cellData.getValue().scheduleNameProperty());
        columnMark.setCellValueFactory(cellData -> cellData.getValue().markProperty());
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    private void initLoader() {
        try {
            fxmlLoader.setLocation(getClass().getClassLoader().getResource(DEPARTMENT_MODAL_FXML));
            fxmlEdit = fxmlLoader.load();
            studentMarkModalController = fxmlLoader.getController();
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
                    studentMarkModalController.setDependencies(facultyService.getAll());
                    showDialog();
                    Optional<StudentMarkModalDto> optional = studentMarkModalController.getDepartment();
                    if (optional.isPresent()) {
                        studentMarkService.add(optional.get());
                        refreshTable();
                    }
                    break;
                }
                case "btnEdit": {
                    editButtonHandler();
                    break;
                }
                case "btnDelete": {
                    StudentMarkModalDto selectedFaculty = tableStudentMarks.getSelectionModel().getSelectedItem();
                    if (!departmentIsSelected(selectedFaculty)) {
                        return;
                    }
                    studentMarkService.delete(selectedFaculty);
                    refreshTable();
                    break;
                }
            }
        } catch (WrongValues e) {
            handleWrongValueException(e);
        }
    }

    private void refreshTable() {
        tableStudentMarks.setItems(FXCollections.observableArrayList(studentMarkService.getAll()));
    }

    private void editButtonHandler() {
        StudentMarkModalDto selectedFaculty = tableStudentMarks.getSelectionModel().getSelectedItem();
        if (!departmentIsSelected(selectedFaculty)) {
            return;
        }
        studentMarkModalController.setStudentMarkAndDependencies(selectedFaculty, facultyService.getAll());
        showDialog();
        Optional<StudentMarkModalDto> optional = studentMarkModalController.getDepartment();
        if (optional.isPresent()) {
            studentMarkService.update(selectedFaculty);
        }
    }

    private boolean departmentIsSelected(StudentMarkModalDto selectedFaculty) {
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
        studentMarkModalController.addStage(modalDialogStage);
        modalDialogStage.showAndWait();
    }

}
