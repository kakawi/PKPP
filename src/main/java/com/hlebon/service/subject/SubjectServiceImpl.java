package com.hlebon.service.subject;

import com.hlebon.repository.dao.SubjectDao;
import com.hlebon.repository.entity.SubjectEntity;
import com.hlebon.gui.subject.SubjectModalDto;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubjectServiceImpl implements SubjectService {

    private SubjectDao subjectDao = new SubjectDao();
    private SubjectMapper subjectMapper = Mappers.getMapper(SubjectMapper.class);

    @Override
    public List<SubjectModalDto> getAll() {
        List<SubjectEntity> subjectEntities = subjectDao.getAll();
        return convertList(subjectEntities);
    }

    @Override
    public void add(SubjectModalDto subjectModalDto) {
        SubjectEntity subjectEntity = subjectMapper.destinationToSource(subjectModalDto);
        subjectEntity.setId(null);
        subjectDao.save(subjectEntity);
    }

    @Override
    public void update(SubjectModalDto subjectModalDto) {
        SubjectEntity subjectEntity = subjectMapper.destinationToSource(subjectModalDto);
        subjectDao.update(subjectEntity);
    }

    @Override
    public void delete(SubjectModalDto subjectModalDto) {
        subjectDao.delete(subjectModalDto.getId());
    }

    @Override
    public Collection<SubjectModalDto> getBySessionAndSetOfGroup(long sessionId, long setOfGroupId) {
        Collection<SubjectEntity> subjectEntities = subjectDao.getBySessionAndSetOfGroup(sessionId, setOfGroupId);
        return convertList(subjectEntities);
    }

    private List<SubjectModalDto> convertList(Collection<SubjectEntity> subjectEntities) {
        List<SubjectModalDto> result = new ArrayList<>();
        for (SubjectEntity subjectEntity : subjectEntities) {
            SubjectModalDto destination = subjectMapper.sourceToDestination(subjectEntity);
            result.add(destination);
        }
        return result;
    }

}
