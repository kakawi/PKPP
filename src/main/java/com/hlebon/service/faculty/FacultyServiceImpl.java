package com.hlebon.service.faculty;

import com.hlebon.faculty.FacultyModalDto;
import com.hlebon.repository.dao.FacultyDao;
import com.hlebon.repository.entity.FacultyEntity;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class FacultyServiceImpl implements FacultyService {

    private FacultyDao facultyDao = new FacultyDao();
    private FacultyMapper facultyMapper = Mappers.getMapper(FacultyMapper.class);

    @Override
    public List<FacultyModalDto> getAll() {
        List<FacultyEntity> facultyEntities = facultyDao.getAll();
        List<FacultyModalDto> result = new ArrayList<>();
        for (FacultyEntity facultyEntity : facultyEntities) {
            FacultyModalDto destination = facultyMapper.sourceToDestination(facultyEntity);
            result.add(destination);
        }
        return result;
    }

    @Override
    public void add(FacultyModalDto faculty) {
        FacultyEntity facultyEntity = facultyMapper.destinationToSource(faculty);
        facultyDao.save(facultyEntity);
    }

    @Override
    public void update(FacultyModalDto faculty) {
        FacultyEntity facultyEntity = facultyMapper.destinationToSource(faculty);
        facultyDao.update(facultyEntity);
    }

    @Override
    public void delete(FacultyModalDto faculty) {
        facultyDao.delete(faculty.getId());
    }

}
