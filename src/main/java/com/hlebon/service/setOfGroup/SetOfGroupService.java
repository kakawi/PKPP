package com.hlebon.service.setOfGroup;

import com.hlebon.gui.setOfGroup.SetOfGroupModalDto;

import java.util.List;

public interface SetOfGroupService {

    List<SetOfGroupModalDto> getAll();

    List<SetOfGroupModalDto> getBySpeciality(long id);

    void add(SetOfGroupModalDto faculty);

    void update(SetOfGroupModalDto faculty);

    void delete(SetOfGroupModalDto faculty);
}
