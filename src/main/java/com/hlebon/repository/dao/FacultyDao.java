package com.hlebon.repository.dao;

import com.hlebon.repository.entity.FacultyEntity;
import com.hlebon.repository.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collections;
import java.util.List;

import static com.hlebon.gui.Main.ENTITY_MANAGER_FACTORY;

public class FacultyDao {

    public List<FacultyEntity> getAll() {
        List<FacultyEntity> faculties = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            faculties = manager.createQuery("SELECT s FROM FacultyEntity s", FacultyEntity.class).getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }

        return faculties;
    }

    public FacultyEntity getById(long id) {
        FacultyEntity facultyEntity = null;

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            facultyEntity = manager.createQuery("SELECT s FROM FacultyEntity s where s.id = :id", FacultyEntity.class)
                    .setParameter("id", id).getSingleResult();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException(ex);
        } finally {
            manager.close();
        }

        return facultyEntity;
    }

    public void save(FacultyEntity faculty) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.persist(faculty);

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

    public void update(FacultyEntity faculty) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            manager.merge(faculty);

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

    public void delete(long facultyId) {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            FacultyEntity facultyEntity = manager.find(FacultyEntity.class, facultyId);
            manager.remove(facultyEntity);

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
