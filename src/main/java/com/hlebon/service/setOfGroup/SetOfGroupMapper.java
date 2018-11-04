package com.hlebon.service.setOfGroup;

import com.hlebon.repository.entity.SetOfGroupEntity;
import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface SetOfGroupMapper {

    SetOfGroupModalDto sourceToDestination(SetOfGroupEntity source);

    SetOfGroupEntity destinationToSource(SetOfGroupModalDto destination);

}
