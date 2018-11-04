package com.hlebon.service.student;

import com.hlebon.gui.student.StudentModalDto;

import java.util.List;

public interface StudentService {

    List<StudentModalDto> getAll();

    List<StudentModalDto> getByGroup(long id);

    void add(StudentModalDto studentModalDto);

    void update(StudentModalDto studentModalDto);

    void delete(StudentModalDto studentModalDto);
}
