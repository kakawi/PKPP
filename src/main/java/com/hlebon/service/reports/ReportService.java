package com.hlebon.service.reports;

import com.hlebon.gui.reports.AverageMarkBySubjectDto;

import java.util.Collection;

public interface ReportService {

    Collection<AverageMarkBySubjectDto> getAverageMarkBySubjectForSession(String sessionId);

}
