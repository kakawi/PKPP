package com.hlebon.repository.dao;

import com.hlebon.repository.entity.ScheduleEntity;
import com.hlebon.repository.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

import static com.hlebon.gui.Main.ENTITY_MANAGER_FACTORY;

public class ScheduleDao {

    public List<ScheduleEntity> getAll() {
        List<ScheduleEntity> schedules = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            schedules = manager
                    .createQuery("SELECT s FROM ScheduleEntity s", ScheduleEntity.class)
                    .getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            ex.printStackTrace();
        } finally {
            manager.close();
        }

        return schedules;
    }

    public List<ScheduleEntity> getBySetOfGroup(long setOfGroupId) {
        List<ScheduleEntity> schedules = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            schedules = manager
                    .createQuery("SELECT s FROM ScheduleEntity s where s.setOfGroup.id = :setOfGroupId", ScheduleEntity.class)
                    .setParameter("setOfGroupId", setOfGroupId)
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

        return schedules;
    }

    public void save(ScheduleEntity scheduleEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.persist(scheduleEntity);

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

    public void update(ScheduleEntity scheduleEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.merge(scheduleEntity);

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

    public void delete(long scheduleId) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            ScheduleEntity scheduleEntity = manager.find(ScheduleEntity.class, scheduleId);
            manager.remove(scheduleEntity);

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

    public ScheduleEntity getBySessionAndSetOfGroupAndSubject(long sessionId, long setOfGroupId, long subjectId) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();

        try {
            return manager
                    .createQuery("SELECT s FROM ScheduleEntity s" +
                            " where s.session.id = :session" +
                            " and s.setOfGroup.id = :setOfGroupId" +
                            " and s.subject.id = :subjectId", ScheduleEntity.class)
                    .setParameter("session", sessionId)
                    .setParameter("setOfGroupId", setOfGroupId)
                    .setParameter("subjectId", subjectId)
                    .getSingleResult();

        } catch (Exception ex) {
            throw new DaoException(ex);
        } finally {
            manager.close();
        }
    }
}
