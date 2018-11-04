package com.hlebon.service.faculty;

import com.hlebon.gui.faculty.FacultyModalDto;
import com.hlebon.repository.entity.FacultyEntity;
import org.mapstruct.Mapper;

@Mapper
public interface FacultyMapper {

    FacultyModalDto sourceToDestination(FacultyEntity source);

    FacultyEntity destinationToSource(FacultyModalDto destination);

}
