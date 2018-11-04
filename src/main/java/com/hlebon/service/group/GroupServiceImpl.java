package com.hlebon.service.group;

import com.hlebon.group.GroupModalDto;
import com.hlebon.repository.dao.GroupDao;
import com.hlebon.repository.entity.GroupEntity;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class GroupServiceImpl implements GroupService {

    private GroupDao groupDao = new GroupDao();
    private GroupMapper groupMapper = Mappers.getMapper(GroupMapper.class);

    @Override
    public List<GroupModalDto> getAll() {
        List<GroupEntity> departmentEntities = groupDao.getAll();
        return convertList(departmentEntities);
    }

    @Override
    public List<GroupModalDto> getBySetOfGroup(long id) {
        List<GroupEntity> setOfGroup = groupDao.getBySetOfGroupId(id);
        return convertList(setOfGroup);
    }

    @Override
    public void add(GroupModalDto groupModalDto) {
        GroupEntity groupEntity = groupMapper.destinationToSource(groupModalDto);
        groupEntity.setId(null);
        groupDao.save(groupEntity);
    }

    @Override
    public void update(GroupModalDto groupModalDto) {
        GroupEntity departmentEntity = groupMapper.destinationToSource(groupModalDto);
        groupDao.update(departmentEntity);
    }

    @Override
    public void delete(GroupModalDto groupModalDto) {
        groupDao.delete(groupModalDto.getId());
    }

    private List<GroupModalDto> convertList(List<GroupEntity> groupEntities) {
        List<GroupModalDto> result = new ArrayList<>();
        for (GroupEntity groupEntity : groupEntities) {
            GroupModalDto destination = groupMapper.sourceToDestination(groupEntity);
            result.add(destination);
        }
        return result;
    }

}
