package com.hlebon.repository.dao;

import com.hlebon.repository.entity.SessionEntity;
import com.hlebon.repository.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.hlebon.gui.Main.ENTITY_MANAGER_FACTORY;

public class SessionDao {

    public List<SessionEntity> getAll() {
        List<SessionEntity> sessions = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            sessions = manager
                    .createQuery("SELECT s FROM SessionEntity s", SessionEntity.class)
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

        return sessions;
    }

    public void save(SessionEntity sessionEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.persist(sessionEntity);

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

    public void update(SessionEntity sessionEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.merge(sessionEntity);

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

    public void delete(long sessionId) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            SessionEntity sessionEntity = manager.find(SessionEntity.class, sessionId);
            manager.remove(sessionEntity);

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


    public Collection<SessionEntity> getBySetOfGroup(long setOfGroupId) {
        List<SessionEntity> sessions = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            sessions = manager
                    .createQuery("SELECT distinct s FROM SessionEntity s " +
                            " join s.schedules sch" +
                            " join sch.setOfGroup setOfGroup" +
                            " where setOfGroup.id = :setOfGroupId" +
                            " and s.isFinished = false", SessionEntity.class)
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

        return sessions;
    }

}
