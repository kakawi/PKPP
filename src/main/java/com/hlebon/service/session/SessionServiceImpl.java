package com.hlebon.service.session;

import com.hlebon.repository.dao.SessionDao;
import com.hlebon.repository.entity.SessionEntity;
import com.hlebon.gui.session.SessionModalDto;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SessionServiceImpl implements SessionService {

    private SessionDao sessionDao = new SessionDao();
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Override
    public List<SessionModalDto> getAll() {
        List<SessionEntity> sessionEntities = sessionDao.getAll();
        return convertList(sessionEntities);
    }

    @Override
    public Collection<SessionModalDto> getBySetOfGroup(long id) {
        Collection<SessionEntity> sessionEntities = sessionDao.getBySetOfGroup(id);
        return convertList(sessionEntities);
    }

    @Override
    public void add(SessionModalDto sessionModalDto) {
        SessionEntity sessionEntity = sessionMapper.destinationToSource(sessionModalDto);
        sessionEntity.setId(null);
        sessionDao.save(sessionEntity);
    }

    @Override
    public void update(SessionModalDto sessionModalDto) {
        SessionEntity sessionEntity = sessionMapper.destinationToSource(sessionModalDto);
        sessionDao.update(sessionEntity);
    }

    @Override
    public void delete(SessionModalDto sessionModalDto) {
        sessionDao.delete(sessionModalDto.getId());
    }

    private List<SessionModalDto> convertList(Collection<SessionEntity> sessionEntities) {
        List<SessionModalDto> result = new ArrayList<>();
        for (SessionEntity sessionEntity : sessionEntities) {
            SessionModalDto destination = sessionMapper.sourceToDestination(sessionEntity);
            result.add(destination);
        }
        return result;
    }

}
