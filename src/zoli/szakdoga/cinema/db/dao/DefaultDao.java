package zoli.szakdoga.cinema.db.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.criteria.CriteriaQuery;
import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.entity.*;
import zoli.szakdoga.cinema.gui.GuiConstants;

/**
 *
 * @author pappz
 */
public class DefaultDao<T extends PersistentEntity> implements GenericDao<T> {

    private final Class<T> CLASS;
    private final EntityManagerFactory EMF;

    public DefaultDao(Class<T> CLASS) {
        this.CLASS = CLASS;
        this.EMF = Persistence.createEntityManagerFactory("CinemaProjektPU");
    }

    @Override
    public void create(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    @Override
    public void update(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(entity);
        try {
            entityManager.getTransaction().commit();
        } catch (RollbackException e) {
            JOptionPane.showMessageDialog(null, GuiConstants.UNIQUE_DATA_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete(T entity) {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        T delEntity = entityManager.getReference(CLASS, entity.getId());
        entityManager.remove(delEntity);
        entityManager.getTransaction().commit();
    }

    @Override
    public List<T> findAll() {
        return findEntities(true, -1, -1);
    }

    @Override
    public T findById(Integer id) {
        return getEntityManager().find(CLASS, id);
    }

    public List<T> findAll(Mozi id) {
        List<T> result = null;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Tartalmaz.findMoziById")
                    .setParameter("moziId", id);
            result = (List<T>) query.getResultList();
        } catch (NoResultException e) {
        }
        return result;
    }

    public boolean findMozi(String name) {
        T result;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Mozi.findByNev")
                    .setParameter("nev", name);
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean findTerem(String name) {
        T result;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Terem.findByNev")
                    .setParameter("nev", name);
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    public List<Vetites> findUsedTerem(String date) {
        List<Vetites> result;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Vetites.findByMikor")
                    .setParameter("mikor", date);
            result = (List<Vetites>) query.getResultList();
        } catch (NoResultException e) {
            result = null;
        }
        if (result == null) {
            return null;
        } else {
            return result;
        }
    }

    public boolean findFilm(String name) {
        T result;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Film.findByNev")
                    .setParameter("nev", name);
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean findUser(String name) {
        T result;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Felhasznalo.findByNev")
                    .setParameter("nev", name);
            result = (T) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        if (result == null) {
            return false;
        } else {
            return true;
        }
    }

    public Felhasznalo isUser(String name) {
        Felhasznalo result;
        try {
            Query query = getEntityManager()
                    .createNamedQuery("Felhasznalo.findByNev")
                    .setParameter("nev", name);
            result = (Felhasznalo) query.getSingleResult();
        } catch (NoResultException e) {
            result = null;
        }
        if (result == null) {
            return null;
        } else {
            return result;
        }
    }

    private EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    private List<T> findEntities(boolean all, int firstResult, int maxResult) {
        EntityManager entityManager = getEntityManager();
        CriteriaQuery criteriaQuery = entityManager.getCriteriaBuilder().createQuery();
        criteriaQuery.select(criteriaQuery.from(CLASS));
        Query query = entityManager.createQuery(criteriaQuery);
        if (!all) {
            query.setFirstResult(firstResult);
            query.setMaxResults(maxResult);
        }
        return query.getResultList();
    }

}
