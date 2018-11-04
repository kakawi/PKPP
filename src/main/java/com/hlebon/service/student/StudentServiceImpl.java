package com.hlebon.service.student;

import com.hlebon.repository.dao.StudentDao;
import com.hlebon.repository.entity.StudentEntity;
import com.hlebon.student.StudentModalDto;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class StudentServiceImpl implements StudentService {

    private StudentDao studentDao = new StudentDao();
    private StudentMapper studentMapper = Mappers.getMapper(StudentMapper.class);

    @Override
    public List<StudentModalDto> getAll() {
        List<StudentEntity> studentEntities = studentDao.getAll();
        return convertList(studentEntities);
    }

    @Override
    public List<StudentModalDto> getByGroup(long id) {
        List<StudentEntity> studentEntities = studentDao.getByGroup(id);
        return convertList(studentEntities);
    }

    @Override
    public void add(StudentModalDto studentModalDto) {
        StudentEntity studentEntity = studentMapper.destinationToSource(studentModalDto);
        studentEntity.setId(null);
        studentDao.save(studentEntity);
    }

    @Override
    public void update(StudentModalDto studentModalDto) {
        StudentEntity studentEntity = studentMapper.destinationToSource(studentModalDto);
        studentDao.update(studentEntity);
    }

    @Override
    public void delete(StudentModalDto studentModalDto) {
        studentDao.delete(studentModalDto.getId());
    }

    private List<StudentModalDto> convertList(List<StudentEntity> studentEntities) {
        List<StudentModalDto> result = new ArrayList<>();
        for (StudentEntity studentEntity : studentEntities) {
            StudentModalDto destination = studentMapper.sourceToDestination(studentEntity);
            result.add(destination);
        }
        return result;
    }

}
