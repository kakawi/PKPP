package com.hlebon.service.faculty;

import com.hlebon.gui.faculty.FacultyModalDto;

import java.util.List;

public interface FacultyService {

    List<FacultyModalDto> getAll();

    void add(FacultyModalDto faculty);

    void update(FacultyModalDto faculty);

    void delete(FacultyModalDto faculty);
}
