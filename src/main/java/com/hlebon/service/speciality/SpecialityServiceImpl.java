package com.hlebon.service.speciality;

import com.hlebon.repository.dao.SpecialityDao;
import com.hlebon.repository.entity.SpecialityEntity;
import com.hlebon.gui.speciality.SpecialityModalDto;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

public class SpecialityServiceImpl implements SpecialityService {

    private SpecialityDao specialityDao = new SpecialityDao();
    private SpecialityMapper specialityMapper = Mappers.getMapper(SpecialityMapper.class);

    @Override
    public List<SpecialityModalDto> getAll() {
        List<SpecialityEntity> specialityEntities = specialityDao.getAll();
        return convertList(specialityEntities);
    }

    @Override
    public List<SpecialityModalDto> getByDepartment(long id) {
        List<SpecialityEntity> specialityEntities = specialityDao.getByDepartment(id);
        return convertList(specialityEntities);
    }

    private List<SpecialityModalDto> convertList(List<SpecialityEntity> specialityEntities) {
        List<SpecialityModalDto> result = new ArrayList<>();
        for (SpecialityEntity specialityEntity : specialityEntities) {
            SpecialityModalDto destination = specialityMapper.sourceToDestination(specialityEntity);
            result.add(destination);
        }
        return result;
    }

    @Override
    public void add(SpecialityModalDto specialityModalDto) {
        SpecialityEntity specialityEntity = specialityMapper.destinationToSource(specialityModalDto);
        specialityDao.save(specialityEntity);
    }

    @Override
    public void update(SpecialityModalDto specialityModalDto) {
        SpecialityEntity specialityEntity = specialityMapper.destinationToSource(specialityModalDto);
        specialityDao.update(specialityEntity);
    }

    @Override
    public void delete(SpecialityModalDto specialityModalDto) {
        specialityDao.delete(specialityModalDto.getId());
    }

}
