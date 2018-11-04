package com.hlebon.service.speciality;

import com.hlebon.speciality.SpecialityModalDto;

import java.util.List;

public interface SpecialityService {

    List<SpecialityModalDto> getAll();
    List<SpecialityModalDto> getByDepartment(long id);

    void add(SpecialityModalDto faculty);

    void update(SpecialityModalDto faculty);

    void delete(SpecialityModalDto faculty);
}
