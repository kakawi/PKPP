package com.hlebon.service.studentMark;

import com.hlebon.gui.studentMark.StudentMarkModalDto;
import com.hlebon.repository.dao.StudentMarkDao;
import com.hlebon.repository.entity.StudentMarkEntity;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class StudentMarkServiceImpl implements StudentMarkService {

    private StudentMarkDao studentMarkDao = new StudentMarkDao();
    private StudentMarkMapper studentMarkMapper = Mappers.getMapper(StudentMarkMapper.class);

    @Override
    public List<StudentMarkModalDto> getAll() {
        List<StudentMarkEntity> studentMarkEntities = studentMarkDao.getAll();
        return convertList(studentMarkEntities);
    }

    @Override
    public void add(StudentMarkModalDto studentMark) {
        StudentMarkEntity studentMarkEntity = studentMarkMapper.destinationToSource(studentMark);
        studentMarkEntity.setId(null);
        studentMarkDao.save(studentMarkEntity);
    }

    @Override
    public void update(StudentMarkModalDto studentMark) {
        StudentMarkEntity studentMarkEntity = studentMarkMapper.destinationToSource(studentMark);
        studentMarkDao.update(studentMarkEntity);
    }

    @Override
    public void delete(StudentMarkModalDto studentMark) {
        studentMarkDao.delete(studentMark.getId());
    }

    private List<StudentMarkModalDto> convertList(List<StudentMarkEntity> studentMarkEntities) {
        List<StudentMarkModalDto> result = new ArrayList<>();
        for (StudentMarkEntity studentMarkEntity : studentMarkEntities) {
            StudentMarkModalDto destination = studentMarkMapper.sourceToDestination(studentMarkEntity);
            result.add(destination);
        }
        return result;
    }

}
