package service;

import entity.Event;
import entity.Rating;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author aitor
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<Rating> getAllRatingsByUserId(Integer id) {
        return getEntityManager().createNamedQuery("getAllRatingsByUserId").setParameter("clientId", id).getResultList();
    }

    public List<Rating> getAllRatingsByEventId(Integer id) {
        return getEntityManager().createNamedQuery("getAllRatingsByEventId").setParameter("eventId", id).getResultList();
    }
}
