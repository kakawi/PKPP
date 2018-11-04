package com.hlebon.service.session;

import com.hlebon.session.SessionModalDto;

import java.util.List;

public interface SessionService {

    List<SessionModalDto> getAll();

    void add(SessionModalDto sessionModalDto);

    void update(SessionModalDto sessionModalDto);

    void delete(SessionModalDto sessionModalDto);
}
