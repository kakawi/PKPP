package com.hlebon.service.department;

import com.hlebon.department.DepartmentModalDto;
import com.hlebon.repository.entity.DepartmentEntity;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {

    DepartmentModalDto sourceToDestination(DepartmentEntity source);

    DepartmentEntity destinationToSource(DepartmentModalDto destination);

}
