package com.hlebon.repository.dao;

import com.hlebon.repository.entity.report.AverageMarkBySubjectForSessionEntity;
import com.hlebon.repository.entity.report.CountSubjectsInSessionEntity;
import com.hlebon.repository.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.hlebon.gui.Main.ENTITY_MANAGER_FACTORY;

public class ReportDao {

    /**
     * Average mark for subject for this session
     *
     * SELECT sub.name, AVG(m.marK) FROM university.student_mark m
     *   JOIN university.schedule sch ON sch.id = m.schedule_id
     *   JOIN university.subject sub ON sub.id = sch.subject_id
     *   -- SESSION
     *   JOIN university.session ses ON ses.id = sch.session_id
     *   WHERE ses.year_of_session = 2017
     * GROUP BY sub.name
     * ;
     * @return
     */
    public List<AverageMarkBySubjectForSessionEntity> getAverageMarkBySubjectForSession(long sessionId) {
        List<AverageMarkBySubjectForSessionEntity> result = Collections.emptyList();

        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;

        try {
            transaction = manager.getTransaction();
            transaction.begin();

            result = manager
                    .createQuery("SELECT new com.hlebon.repository.entity.report.AverageMarkBySubjectForSessionEntity(sub.name, avg(m.mark)) " +
                            "FROM StudentMarkEntity m " +
                            "join m.schedule sch " +
                            "join sch.subject sub " +
                            "where sch.session.id = :sessionId group by sub.name", AverageMarkBySubjectForSessionEntity.class)
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

        return result;
    }

    public Collection<CountSubjectsInSessionEntity> getCountSubjectsInSession() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        try {
            return manager
                    .createQuery("SELECT new com.hlebon.repository.entity.report.CountSubjectsInSessionEntity(ses.name, ses.year, count(sub.id)) " +
                            "FROM SessionEntity ses " +
                            "left join ses.schedules sch " +
                            "left join sch.subject sub " +
                            "group by ses.id", CountSubjectsInSessionEntity.class)
                    .getResultList();
        } catch (Exception ex) {
            throw new DaoException(ex);
        } finally {
            manager.close();
        }
    }

}
