package com.hlebon.service.reports;

import com.hlebon.gui.reports.CountSubjectsInSessionDto;
import com.hlebon.repository.entity.report.CountSubjectsInSessionEntity;
import org.mapstruct.Mapper;

@Mapper
public interface CountSubjectsInSessionMapper {

    CountSubjectsInSessionDto sourceToDestination(CountSubjectsInSessionEntity source);

    CountSubjectsInSessionEntity destinationToSource(CountSubjectsInSessionDto destination);

}
