package com.hlebon.service.studentMark;

import com.hlebon.repository.entity.StudentMarkEntity;
import com.hlebon.gui.studentMark.StudentMarkModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMarkMapper {

    StudentMarkModalDto sourceToDestination(StudentMarkEntity source);

    StudentMarkEntity destinationToSource(StudentMarkModalDto destination);

}
