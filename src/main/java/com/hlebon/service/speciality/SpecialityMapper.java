package com.hlebon.service.speciality;

import com.hlebon.repository.entity.SpecialityEntity;
import com.hlebon.speciality.SpecialityModalDto;
import org.mapstruct.Mapper;

@Mapper
public interface SpecialityMapper {

    SpecialityModalDto sourceToDestination(SpecialityEntity source);

    SpecialityEntity destinationToSource(SpecialityModalDto destination);

}
