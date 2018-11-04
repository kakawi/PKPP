package com.hlebon.service.student;

import com.hlebon.repository.entity.StudentEntity;
import com.hlebon.gui.student.StudentModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface StudentMapper {

    StudentModalDto sourceToDestination(StudentEntity source);

    StudentEntity destinationToSource(StudentModalDto destination);

}
