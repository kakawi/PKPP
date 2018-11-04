package com.hlebon.service.group;

import com.hlebon.gui.group.GroupModalDto;
import com.hlebon.repository.entity.GroupEntity;
import org.mapstruct.Mapper;

@Mapper
public interface GroupMapper {

    GroupModalDto sourceToDestination(GroupEntity source);

    GroupEntity destinationToSource(GroupModalDto destination);

}
