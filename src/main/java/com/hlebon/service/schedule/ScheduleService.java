package com.hlebon.service.schedule;

import com.hlebon.schedule.ScheduleModalDto;

import java.util.List;

public interface ScheduleService {

    List<ScheduleModalDto> getAll();

    List<ScheduleModalDto> getBySetOfGroup(long id);

    void add(ScheduleModalDto faculty);

    void update(ScheduleModalDto faculty);

    void delete(ScheduleModalDto faculty);

    ScheduleModalDto getBySessionAndSetOfGroupAndSubject(long sessionId, long setOfGroupId, long subjectId);
}
