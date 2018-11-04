package com.hlebon.service.department;

import com.hlebon.department.DepartmentModalDto;
import com.hlebon.repository.dao.DepartmentDao;
import com.hlebon.repository.entity.DepartmentEntity;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentDao departmentDao = new DepartmentDao();
    private DepartmentMapper departmentMapper = Mappers.getMapper(DepartmentMapper.class);

    @Override
    public List<DepartmentModalDto> getAll() {
        List<DepartmentEntity> departmentEntities = departmentDao.getAll();
        return convertList(departmentEntities);
    }

    @Override
    public List<DepartmentModalDto> getByFaculty(long id) {
        List<DepartmentEntity> departmentEntities = departmentDao.getByFaculty(id);
        return convertList(departmentEntities);
    }

    @Override
    public void add(DepartmentModalDto department) {
        DepartmentEntity departmentEntity = departmentMapper.destinationToSource(department);
        departmentEntity.setId(null);
        departmentDao.save(departmentEntity);
    }

    @Override
    public void update(DepartmentModalDto department) {
        DepartmentEntity departmentEntity = departmentMapper.destinationToSource(department);
        departmentDao.update(departmentEntity);
    }

    @Override
    public void delete(DepartmentModalDto department) {
        departmentDao.delete(department.getId());
    }

    private List<DepartmentModalDto> convertList(List<DepartmentEntity> departmentEntities) {
        List<DepartmentModalDto> result = new ArrayList<>();
        for (DepartmentEntity departmentEntity : departmentEntities) {
            DepartmentModalDto destination = departmentMapper.sourceToDestination(departmentEntity);
            result.add(destination);
        }
        return result;
    }

}
