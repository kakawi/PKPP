package com.hlebon.service.subject;

import com.hlebon.gui.subject.SubjectModalDto;

import java.util.Collection;
import java.util.List;

public interface SubjectService {

    List<SubjectModalDto> getAll();

    void add(SubjectModalDto subjectModalDto);

    void update(SubjectModalDto subjectModalDto);

    void delete(SubjectModalDto subjectModalDto);

    Collection<SubjectModalDto> getBySessionAndSetOfGroup(long sessionId, long setOfGroupId);
}
