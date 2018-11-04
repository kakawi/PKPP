package com.hlebon.service.session;

import com.hlebon.repository.dao.SessionDao;
import com.hlebon.repository.entity.SessionEntity;
import com.hlebon.session.SessionModalDto;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class SessionServiceImpl implements SessionService {

    private SessionDao subjectDao = new SessionDao();
    private SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Override
    public List<SessionModalDto> getAll() {
        List<SessionEntity> sessionEntities = subjectDao.getAll();
        return convertList(sessionEntities);
    }

    @Override
    public void add(SessionModalDto sessionModalDto) {
        SessionEntity sessionEntity = sessionMapper.destinationToSource(sessionModalDto);
        sessionEntity.setId(null);
        subjectDao.save(sessionEntity);
    }

    @Override
    public void update(SessionModalDto sessionModalDto) {
        SessionEntity sessionEntity = sessionMapper.destinationToSource(sessionModalDto);
        subjectDao.update(sessionEntity);
    }

    @Override
    public void delete(SessionModalDto sessionModalDto) {
        subjectDao.delete(sessionModalDto.getId());
    }

    private List<SessionModalDto> convertList(List<SessionEntity> sessionEntities) {
        List<SessionModalDto> result = new ArrayList<>();
        for (SessionEntity sessionEntity : sessionEntities) {
            SessionModalDto destination = sessionMapper.sourceToDestination(sessionEntity);
            result.add(destination);
        }
        return result;
    }

}
