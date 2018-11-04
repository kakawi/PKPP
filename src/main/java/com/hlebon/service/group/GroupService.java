package com.hlebon.service.group;

import com.hlebon.gui.group.GroupModalDto;

import java.util.List;

public interface GroupService {

    List<GroupModalDto> getAll();

    List<GroupModalDto> getBySetOfGroup(long id);

    void add(GroupModalDto groupModalDto);

    void update(GroupModalDto groupModalDto);

    void delete(GroupModalDto groupModalDto);

}
