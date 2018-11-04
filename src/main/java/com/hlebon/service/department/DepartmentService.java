package com.hlebon.service.department;

import com.hlebon.gui.department.DepartmentModalDto;

import java.util.List;

public interface DepartmentService {

    List<DepartmentModalDto> getAll();

    List<DepartmentModalDto> getByFaculty(long id);

    void add(DepartmentModalDto faculty);

    void update(DepartmentModalDto faculty);

    void delete(DepartmentModalDto faculty);
}
