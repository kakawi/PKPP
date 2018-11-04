package com.hlebon.service.session;

import com.hlebon.repository.entity.SessionEntity;
import com.hlebon.gui.session.SessionModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface SessionMapper {

    SessionModalDto sourceToDestination(SessionEntity source);

    SessionEntity destinationToSource(SessionModalDto destination);

}
