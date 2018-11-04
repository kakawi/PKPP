package com.hlebon.service.subject;

import com.hlebon.subject.SubjectModalDto;

import java.util.List;

public interface SubjectService {

    List<SubjectModalDto> getAll();

    void add(SubjectModalDto subjectModalDto);

    void update(SubjectModalDto subjectModalDto);

    void delete(SubjectModalDto subjectModalDto);
}
