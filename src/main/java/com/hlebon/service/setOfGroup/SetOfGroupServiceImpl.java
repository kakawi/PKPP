package com.hlebon.service.setOfGroup;

import com.hlebon.repository.dao.SetOfGroupDao;
import com.hlebon.repository.entity.SetOfGroupEntity;
import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class SetOfGroupServiceImpl implements SetOfGroupService {

    private SetOfGroupDao setOfGroupDao = new SetOfGroupDao();
    private SetOfGroupMapper setOfGroupMapper = Mappers.getMapper(SetOfGroupMapper.class);

    @Override
    public List<SetOfGroupModalDto> getAll() {
        List<SetOfGroupEntity> setOfGroupEntities = setOfGroupDao.getAll();
        return convertList(setOfGroupEntities);
    }

    @Override
    public List<SetOfGroupModalDto> getBySpeciality(long id) {
        List<SetOfGroupEntity> departmentEntities = setOfGroupDao.getBySpeciality(id);
        return convertList(departmentEntities);
    }

    @Override
    public void add(SetOfGroupModalDto setOfGroupModalDto) {
        SetOfGroupEntity setOfGroupEntity = setOfGroupMapper.destinationToSource(setOfGroupModalDto);
        setOfGroupEntity.setId(null);
        setOfGroupDao.save(setOfGroupEntity);
    }

    @Override
    public void update(SetOfGroupModalDto setOfGroupModalDto) {
        SetOfGroupEntity setOfGroupEntity = setOfGroupMapper.destinationToSource(setOfGroupModalDto);
        setOfGroupDao.update(setOfGroupEntity);
    }

    @Override
    public void delete(SetOfGroupModalDto setOfGroupModalDto) {
        setOfGroupDao.delete(setOfGroupModalDto.getId());
    }

    private List<SetOfGroupModalDto> convertList(List<SetOfGroupEntity> setOfGroupEntities) {
        List<SetOfGroupModalDto> result = new ArrayList<>();
        for (SetOfGroupEntity setOfGroupEntity : setOfGroupEntities) {
            SetOfGroupModalDto destination = setOfGroupMapper.sourceToDestination(setOfGroupEntity);
            result.add(destination);
        }
        return result;
    }

}
