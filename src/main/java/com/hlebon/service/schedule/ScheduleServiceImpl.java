package com.hlebon.service.schedule;

import com.hlebon.schedule.ScheduleModalDto;
import com.hlebon.repository.dao.ScheduleDao;
import com.hlebon.repository.entity.ScheduleEntity;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleDao scheduleDao = new ScheduleDao();
    private ScheduleMapper scheduleMapper = Mappers.getMapper(ScheduleMapper.class);

    @Override
    public List<ScheduleModalDto> getAll() {
        List<ScheduleEntity> scheduleEntities = scheduleDao.getAll();
        return convertList(scheduleEntities);
    }

    @Override
    public List<ScheduleModalDto> getBySetOfGroup(long id) {
        List<ScheduleEntity> scheduleEntities = scheduleDao.getBySetOfGroup(id);
        return convertList(scheduleEntities);
    }

    @Override
    public void add(ScheduleModalDto schedule) {
        ScheduleEntity scheduleEntity = scheduleMapper.destinationToSource(schedule);
        scheduleEntity.setId(null);
        scheduleDao.save(scheduleEntity);
    }

    @Override
    public void update(ScheduleModalDto schedule) {
        ScheduleEntity scheduleEntity = scheduleMapper.destinationToSource(schedule);
        scheduleDao.update(scheduleEntity);
    }

    @Override
    public void delete(ScheduleModalDto schedule) {
        scheduleDao.delete(schedule.getId());
    }

    @Override
    public ScheduleModalDto getBySessionAndSetOfGroupAndSubject(long sessionId, long setOfGroupId, long subjectId) {
        ScheduleEntity scheduleEntity = scheduleDao.getBySessionAndSetOfGroupAndSubject(sessionId, setOfGroupId, subjectId);
        return scheduleMapper.sourceToDestination(scheduleEntity);
    }

    private List<ScheduleModalDto> convertList(Collection<ScheduleEntity> scheduleEntities) {
        List<ScheduleModalDto> result = new ArrayList<>();
        for (ScheduleEntity scheduleEntity : scheduleEntities) {
            ScheduleModalDto destination = scheduleMapper.sourceToDestination(scheduleEntity);
            result.add(destination);
        }
        return result;
    }

}
