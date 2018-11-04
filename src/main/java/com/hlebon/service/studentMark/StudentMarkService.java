package com.hlebon.service.studentMark;

import com.hlebon.gui.studentMark.StudentMarkModalDto;

import java.util.List;

public interface StudentMarkService {

    List<StudentMarkModalDto> getAll();

    void add(StudentMarkModalDto faculty);

    void update(StudentMarkModalDto faculty);

    void delete(StudentMarkModalDto faculty);
}
