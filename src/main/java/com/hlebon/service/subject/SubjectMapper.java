package com.hlebon.service.subject;

import com.hlebon.repository.entity.SubjectEntity;
import com.hlebon.gui.subject.SubjectModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubjectMapper {

    SubjectModalDto sourceToDestination(SubjectEntity source);

    SubjectEntity destinationToSource(SubjectModalDto destination);

}
