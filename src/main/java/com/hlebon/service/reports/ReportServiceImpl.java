package com.hlebon.service.reports;

import com.hlebon.gui.reports.AverageMarkBySubjectDto;
import com.hlebon.repository.dao.ReportDao;
import com.hlebon.repository.entity.AverageMarkBySubjectForSessionEntity;
import org.mapstruct.factory.Mappers;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReportServiceImpl implements ReportService {

    private ReportDao reportDao = new ReportDao();
    private AveragemarkBySubjectMapper mapper = Mappers.getMapper(AveragemarkBySubjectMapper.class);

    @Override
    public Collection<AverageMarkBySubjectDto> getAverageMarkBySubjectForSession(String sessionId) {
        List<AverageMarkBySubjectForSessionEntity> averageMarkBySubjectForSession = reportDao.getAverageMarkBySubjectForSession(Long.valueOf(sessionId));
        return convert(averageMarkBySubjectForSession);
    }

    private Collection<AverageMarkBySubjectDto> convert(Collection<AverageMarkBySubjectForSessionEntity> averageMarkBySubjectForSessionEntities) {
        final Collection<AverageMarkBySubjectDto> result = new ArrayList<>();
        for (AverageMarkBySubjectForSessionEntity averageMarkBySubjectForSessionEntity : averageMarkBySubjectForSessionEntities) {
            AverageMarkBySubjectDto averageMarkBySubjectDto = mapper.sourceToDestination(averageMarkBySubjectForSessionEntity);

            NumberFormat formatter = new DecimalFormat("#0.00");
            String averageMark = formatter.format(averageMarkBySubjectForSessionEntity.getAverageMark());

            averageMarkBySubjectDto.setAverageMark(averageMark);

            result.add(averageMarkBySubjectDto);
        }
        return result;
    }

}
