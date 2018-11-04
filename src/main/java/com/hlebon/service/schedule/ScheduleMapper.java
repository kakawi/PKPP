package com.hlebon.service.schedule;

import com.hlebon.repository.entity.ScheduleEntity;
import com.hlebon.schedule.ScheduleModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface ScheduleMapper {

    ScheduleModalDto sourceToDestination(ScheduleEntity source);

    ScheduleEntity destinationToSource(ScheduleModalDto destination);

}
