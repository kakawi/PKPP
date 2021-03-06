package com.hlebon.service.session;

import com.hlebon.gui.session.SessionModalDto;

import java.util.Collection;
import java.util.List;

public interface SessionService {

    List<SessionModalDto> getAll();

    void add(SessionModalDto sessionModalDto);

    void update(SessionModalDto sessionModalDto);

    void delete(SessionModalDto sessionModalDto);

    Collection<SessionModalDto> getBySetOfGroup(long id);
}
