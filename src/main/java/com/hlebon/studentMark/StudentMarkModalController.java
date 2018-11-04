package com.hlebon.studentMark;

import com.hlebon.DialogManager;
import com.hlebon.department.DepartmentModalDto;
import com.hlebon.faculty.FacultyModalDto;
import com.hlebon.group.GroupModalDto;
import com.hlebon.schedule.ScheduleModalDto;
import com.hlebon.session.SessionModalDto;
import com.hlebon.setOfGroup.SetOfGroupModalDto;
import com.hlebon.speciality.SpecialityModalDto;
import com.hlebon.student.StudentModalDto;
import com.hlebon.subject.SubjectModalDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.BiFunction;
import java.util.function.Function;

public class StudentMarkModalController implements Initializable {

    @FXML
    public TextField txtId;

    @FXML
    public ChoiceBox<String> facultiesChoiceBox;

    @FXML
    public ChoiceBox<String> departmentsChoiceBox;

    @FXML
    public ChoiceBox<String> specialitiesChoiceBox;

    @FXML
    public ChoiceBox<String> setOfGroupsChoiceBox;

    @FXML
    public ChoiceBox<String> groupsChoiceBox;

    @FXML
    public ChoiceBox<String> sessionsChoiceBox;

    @FXML
    public ChoiceBox<String> subjectsChoiceBox;

    @FXML
    public ChoiceBox<String> studentsChoiceBox;

    @FXML
    public TextField txtMark;

    private StudentMarkModalDto studentMarkModalDto;

    private Map<String, FacultyModalDto> facultyModalDtoMap = new HashMap<>();
    private Map<String, DepartmentModalDto> departmentModalDtoMap = new HashMap<>();
    private Map<String, SpecialityModalDto> specialityModalDtoMap = new HashMap<>();
    private Map<String, SetOfGroupModalDto> setOfGroupModalDtoMap = new HashMap<>();
    private Map<String, GroupModalDto> groupModalDtoMap = new HashMap<>();
    private Map<String, SessionModalDto> sessionModalDtoMap = new HashMap<>();
    private Map<String, SubjectModalDto> subjectModalDtoMap = new HashMap<>();
    private Map<String, StudentModalDto> studentModalDtoMap = new HashMap<>();

    private Function<FacultyModalDto, Collection<DepartmentModalDto>> generateDepartment;
    private Function<DepartmentModalDto, Collection<SpecialityModalDto>> generateSpeciality;
    private Function<SpecialityModalDto, Collection<SetOfGroupModalDto>> generateSetOfGroup;
    private Function<SetOfGroupModalDto, Collection<SessionModalDto>> generateSession;

    private Function<SetOfGroupModalDto, Collection<GroupModalDto>> generateGroup;
    private BiFunction<SessionModalDto, SetOfGroupModalDto, Collection<SubjectModalDto>> generateSubject;
    private Function<GroupModalDto, Collection<StudentModalDto>> generateStudent;

    private TripleFunction<SessionModalDto, SetOfGroupModalDto, SubjectModalDto, ScheduleModalDto> generateScheduleModalDto;

    public void setGenerateDepartment(Function<FacultyModalDto, Collection<DepartmentModalDto>> generateDepartment) {
        this.generateDepartment = generateDepartment;
    }

    public void setGenerateSpeciality(Function<DepartmentModalDto, Collection<SpecialityModalDto>> generateSpeciality) {
        this.generateSpeciality = generateSpeciality;
    }

    public void setGenerateSetOfGroup(Function<SpecialityModalDto, Collection<SetOfGroupModalDto>> generateSetOfGroup) {
        this.generateSetOfGroup = generateSetOfGroup;
    }

    public void setGenerateGroup(Function<SetOfGroupModalDto, Collection<GroupModalDto>> generateGroup) {
        this.generateGroup = generateGroup;
    }

    public void setGenerateSession(Function<SetOfGroupModalDto, Collection<SessionModalDto>> generateSession) {
        this.generateSession = generateSession;
    }

    public void setGenerateSubject(BiFunction<SessionModalDto, SetOfGroupModalDto, Collection<SubjectModalDto>> generateSubject) {
        this.generateSubject = generateSubject;
    }

    public void setGenerateStudent(Function<GroupModalDto, Collection<StudentModalDto>> generateStudent) {
        this.generateStudent = generateStudent;
    }

    public void setGenerateScheduleModalDto(TripleFunction<SessionModalDto, SetOfGroupModalDto, SubjectModalDto, ScheduleModalDto> generateScheduleModalDto) {
        this.generateScheduleModalDto = generateScheduleModalDto;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        facultiesChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    FacultyModalDto selectedFacultyDto = facultyModalDtoMap.get(newValue);
                    Collection<DepartmentModalDto> generatedDepartments = generateDepartment.apply(selectedFacultyDto);
                    refreshDepartments(generatedDepartments);
                    departmentsChoiceBox.setDisable(false);
                }
        );
        departmentsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    DepartmentModalDto selectedFacultyDto = departmentModalDtoMap.get(newValue);
                    Collection<SpecialityModalDto> generatedSpecialities = generateSpeciality.apply(selectedFacultyDto);
                    refreshSpecialities(generatedSpecialities);
                    specialitiesChoiceBox.setDisable(false);
                }
        );
        specialitiesChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    SpecialityModalDto selectedFacultyDto = specialityModalDtoMap.get(newValue);
                    Collection<SetOfGroupModalDto> generatedSetOfGroups = generateSetOfGroup.apply(selectedFacultyDto);
                    refreshSetOfGroup(generatedSetOfGroups);
                    setOfGroupsChoiceBox.setDisable(false);
                }
        );

        setOfGroupsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }

                    SetOfGroupModalDto selectedFacultyDto = setOfGroupModalDtoMap.get(newValue);
                    Collection<GroupModalDto> generatedGroups = generateGroup.apply(selectedFacultyDto);
                    refreshGroup(generatedGroups);
                    groupsChoiceBox.setDisable(false);
                }
        );
        setOfGroupsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }

                    SetOfGroupModalDto selectedFacultyDto = setOfGroupModalDtoMap.get(newValue);
                    Collection<SessionModalDto> generatedSessions = generateSession.apply(selectedFacultyDto);
                    refreshSession(generatedSessions);
                    sessionsChoiceBox.setDisable(false);
                }
        );

        sessionsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }

                    SessionModalDto selectedFacultyDto = sessionModalDtoMap.get(newValue);
                    String selectedSetOfGroupName = setOfGroupsChoiceBox.getSelectionModel().getSelectedItem();
                    SetOfGroupModalDto setOfGroupModalDto = setOfGroupModalDtoMap.get(selectedSetOfGroupName);
                    Collection<SubjectModalDto> generatedSubjects = generateSubject.apply(selectedFacultyDto, setOfGroupModalDto);
                    refreshSubject(generatedSubjects);
                    subjectsChoiceBox.setDisable(false);
                }
        );

        groupsChoiceBox.getSelectionModel().selectedItemProperty().addListener(
                (v, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }

                    GroupModalDto selectedFacultyDto = groupModalDtoMap.get(newValue);
                    Collection<StudentModalDto> generatedStudents = generateStudent.apply(selectedFacultyDto);
                    refreshStudent(generatedStudents);
                    studentsChoiceBox.setDisable(false);
                }
        );
    }

    private void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void actionSave(ActionEvent actionEvent) {
        if (!checkValues()) {
            return;
        }
        String selectedSubjectItem = subjectsChoiceBox.getSelectionModel().getSelectedItem();
        String selectedSessionItem = sessionsChoiceBox.getSelectionModel().getSelectedItem();
        String selectedSetOfGroupItem = setOfGroupsChoiceBox.getSelectionModel().getSelectedItem();
        SubjectModalDto subjectModalDto = subjectModalDtoMap.get(selectedSubjectItem);
        SessionModalDto sessionModalDto = sessionModalDtoMap.get(selectedSessionItem);
        SetOfGroupModalDto setOfGroupModalDto = setOfGroupModalDtoMap.get(selectedSetOfGroupItem);
        ScheduleModalDto resultedScheduleModalDto = generateScheduleModalDto.apply(sessionModalDto, setOfGroupModalDto, subjectModalDto);

        String selectedStudentItem = studentsChoiceBox.getSelectionModel().getSelectedItem();
        StudentModalDto studentModalDto = studentModalDtoMap.get(selectedStudentItem);
        String markText = txtMark.getText();
        studentMarkModalDto.setMark(Integer.valueOf(markText));

        studentMarkModalDto.setSchedule(resultedScheduleModalDto);
        studentMarkModalDto.setStudent(studentModalDto);

        actionClose(actionEvent);
    }

    private boolean checkValues() {
        String selectedStudentItem = studentsChoiceBox.getSelectionModel().getSelectedItem();
        String selectedSubjectItem = subjectsChoiceBox.getSelectionModel().getSelectedItem();
        String selectedSessionItem = sessionsChoiceBox.getSelectionModel().getSelectedItem();
        String selectedSetOfGroupItem = setOfGroupsChoiceBox.getSelectionModel().getSelectedItem();
        String markText = txtMark.getText();
        if (selectedStudentItem == null
                || selectedSubjectItem == null
                || selectedSessionItem == null
                || selectedSetOfGroupItem == null
                || markText.trim().length() == 0) {
            DialogManager.showErrorDialog("Невалидные данные");
            return false;
        }

        return true;
    }

    public void setStudentMarkAndDependencies(StudentMarkModalDto studentMarkModalDto, List<FacultyModalDto> facultyModalDtos) {
        this.studentMarkModalDto = studentMarkModalDto;
        refreshFaculties(facultyModalDtos);
        StudentModalDto student = studentMarkModalDto.getStudent();
        GroupModalDto group = student.getGroup();
        SetOfGroupModalDto setOfGroup = group.getSetOfGroup();
        SpecialityModalDto speciality = setOfGroup.getSpeciality();
        DepartmentModalDto department = speciality.getDepartment();
        FacultyModalDto faculty = department.getFaculty();
        ScheduleModalDto schedule = studentMarkModalDto.getSchedule();
        SubjectModalDto subject = schedule.getSubject();
        SessionModalDto session = schedule.getSession();

        facultiesChoiceBox.getSelectionModel().select(faculty.getName());
        departmentsChoiceBox.getSelectionModel().select(department.getName());
        specialitiesChoiceBox.getSelectionModel().select(speciality.getName());
        setOfGroupsChoiceBox.getSelectionModel().select(setOfGroup.getSetOfGroupName());
        sessionsChoiceBox.getSelectionModel().select(session.getName());
        groupsChoiceBox.getSelectionModel().select(group.getGroupNumber());
        subjectsChoiceBox.getSelectionModel().select(subject.getName());
        studentsChoiceBox.getSelectionModel().select(student.getFullName());

        txtId.setText(String.valueOf(studentMarkModalDto.getId()));
        txtMark.setText(String.valueOf(studentMarkModalDto.getMark()));
    }

    private void refreshFaculties(List<FacultyModalDto> facultyModalDtos) {
        clearFaculty();
        for (FacultyModalDto facultyModalDto : facultyModalDtos) {
            String facultyName = facultyModalDto.getName();
            facultyModalDtoMap.put(facultyName, facultyModalDto);
            facultiesChoiceBox.getItems().add(facultyName);
        }
    }

    private void refreshDepartments(Collection<DepartmentModalDto> departmentModalDtos) {
        clearDepartment();
        for (DepartmentModalDto departmentModalDto : departmentModalDtos) {
            String facultyName = departmentModalDto.getName();
            departmentModalDtoMap.put(facultyName, departmentModalDto);
            departmentsChoiceBox.getItems().add(facultyName);
        }
    }

    private void refreshSpecialities(Collection<SpecialityModalDto> specialityModalDtos) {
        clearSpeciality();
        for (SpecialityModalDto specialityModalDto : specialityModalDtos) {
            String facultyName = specialityModalDto.getName();
            specialityModalDtoMap.put(facultyName, specialityModalDto);
            specialitiesChoiceBox.getItems().add(facultyName);
        }
    }

    private void refreshSetOfGroup(Collection<SetOfGroupModalDto> setOfGroupModalDtos) {
        clearSetOfGroup();
        for (SetOfGroupModalDto setOfGroupModalDto : setOfGroupModalDtos) {
            String facultyName = setOfGroupModalDto.getSetOfGroupName();
            setOfGroupModalDtoMap.put(facultyName, setOfGroupModalDto);
            setOfGroupsChoiceBox.getItems().add(facultyName);
        }
    }

    private void refreshGroup(Collection<GroupModalDto> groupModalDtos) {
        clearGroup();
        for (GroupModalDto groupModalDto : groupModalDtos) {
            String facultyName = groupModalDto.getGroupNumber();
            groupModalDtoMap.put(facultyName, groupModalDto);
            groupsChoiceBox.getItems().add(facultyName);
        }
    }

    private void refreshSession(Collection<SessionModalDto> sessionModalDtos) {
        clearSession();
        for (SessionModalDto sessionModalDto : sessionModalDtos) {
            String sessionName = sessionModalDto.getName();
            sessionModalDtoMap.put(sessionName, sessionModalDto);
            sessionsChoiceBox.getItems().add(sessionName);
        }
    }

    private void refreshSubject(Collection<SubjectModalDto> subjectModalDtos) {
        clearSubject();
        for (SubjectModalDto subjectModalDto : subjectModalDtos) {
            String subjectName = subjectModalDto.getName();
            subjectModalDtoMap.put(subjectName, subjectModalDto);
            subjectsChoiceBox.getItems().add(subjectName);
        }
    }

    private void refreshStudent(Collection<StudentModalDto> studentModalDtos) {
        clearStudent();
        for (StudentModalDto studentModalDto : studentModalDtos) {
            String studentFullName = studentModalDto.getFullName();
            studentModalDtoMap.put(studentFullName, studentModalDto);
            studentsChoiceBox.getItems().add(studentFullName);
        }
    }

    private void clearFaculty() {
        facultiesChoiceBox.getItems().clear();
        clearDepartment();
        departmentsChoiceBox.setDisable(true);
    }

    private void clearDepartment() {
        departmentsChoiceBox.getItems().clear();
        clearSpeciality();
        specialitiesChoiceBox.setDisable(true);
    }

    private void clearSpeciality() {
        specialitiesChoiceBox.getItems().clear();
        clearSetOfGroup();
        setOfGroupsChoiceBox.setDisable(true);
    }

    private void clearSetOfGroup() {
        setOfGroupsChoiceBox.getItems().clear();
        clearGroup();
        clearSession();
        groupsChoiceBox.setDisable(true);
        sessionsChoiceBox.setDisable(true);
    }

    private void clearGroup() {
        groupsChoiceBox.getItems().clear();
        clearStudent();
        studentsChoiceBox.setDisable(true);
    }

    private void clearSession() {
        sessionsChoiceBox.getItems().clear();
        clearSubject();
        subjectsChoiceBox.setDisable(true);
    }

    private void clearSubject() {
        subjectsChoiceBox.getItems().clear();
    }

    private void clearStudent() {
        studentsChoiceBox.getItems().clear();
    }

    public void setDependencies(List<FacultyModalDto> facultyModalDtos) {
        this.studentMarkModalDto = new StudentMarkModalDto();
        refreshFaculties(facultyModalDtos);
    }

    public Optional<StudentMarkModalDto> getDepartment() {
        return Optional.ofNullable(studentMarkModalDto);
    }

    public void actionCancel(ActionEvent actionEvent) {
        studentMarkModalDto = null;
        actionClose(actionEvent);
    }

    @FunctionalInterface
    public interface TripleFunction<F, S, T, R> {
        R apply(F first, S second, T third);
    }
}
