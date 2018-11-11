package com.hlebon.service.reports;

import com.hlebon.gui.reports.AverageMarkBySubjectDto;
import com.hlebon.repository.entity.AverageMarkBySubjectForSessionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface AveragemarkBySubjectMapper {

    AverageMarkBySubjectDto sourceToDestination(AverageMarkBySubjectForSessionEntity source);

    AverageMarkBySubjectForSessionEntity destinationToSource(AverageMarkBySubjectDto destination);

}
