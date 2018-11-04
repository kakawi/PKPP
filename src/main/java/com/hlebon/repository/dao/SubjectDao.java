package com.hlebon.repository.dao;

import com.hlebon.repository.entity.SubjectEntity;
import com.hlebon.repository.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.hlebon.Main.ENTITY_MANAGER_FACTORY;

public class SubjectDao {

    public List<SubjectEntity> getAll() {
        List<SubjectEntity> subjects = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            subjects = manager
                    .createQuery("SELECT s FROM SubjectEntity s", SubjectEntity.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }

        return subjects;
    }

    public void save(SubjectEntity subjectEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.persist(subjectEntity);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }
    }

    public void update(SubjectEntity subjectEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.merge(subjectEntity);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }
    }

    public void delete(long subjectId) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            SubjectEntity subjectEntity = manager.find(SubjectEntity.class, subjectId);
            manager.remove(subjectEntity);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }
    }

    public Collection<SubjectEntity> getBySessionAndSetOfGroup(long sessionId, long setOfGroupId) {
        List<SubjectEntity> subjects = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            subjects = manager
                    .createQuery(
                            "SELECT distinct s FROM ScheduleEntity sch" +
                                    " join sch.subject s" +
                                    " where sch.setOfGroup.id = :setOfGroupId" +
                                    " and sch.session.id = :sessionId",
                            SubjectEntity.class)
                    .setParameter("setOfGroupId", setOfGroupId)
                    .setParameter("sessionId", sessionId)
                    .getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }

        return subjects;
    }
}
