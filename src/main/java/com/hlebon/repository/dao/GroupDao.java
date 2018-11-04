package com.hlebon.repository.dao;

import com.hlebon.repository.entity.GroupEntity;
import com.hlebon.repository.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

import static com.hlebon.gui.Main.ENTITY_MANAGER_FACTORY;

public class GroupDao {

    public List<GroupEntity> getAll() {
        List<GroupEntity> groups = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            groups = manager
                    .createQuery("SELECT s FROM GroupEntity s", GroupEntity.class)
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

        return groups;
    }

    public List<GroupEntity> getBySetOfGroupId(long setOfGroupId) {
        List<GroupEntity> groups = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            groups = manager
                    .createQuery("SELECT s FROM GroupEntity s where s.setOfGroup.id = :setOfGroupId", GroupEntity.class)
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

        return groups;
    }

    public void save(GroupEntity groupEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.persist(groupEntity);

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

    public void update(GroupEntity groupEntity) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.merge(groupEntity);

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

    public void delete(long groupId) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            GroupEntity groupEntity = manager.find(GroupEntity.class, groupId);
            manager.remove(groupEntity);

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


}
