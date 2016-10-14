package zoli.szakdoga.cinema.db.dao;

import java.util.List;
import zoli.szakdoga.cinema.db.entity.PersistentEntity;

/**
 *
 * @author pappz
 */
public interface GenericDao<T extends PersistentEntity> {

    public void create(T entity);

    public void update(T entity);

    public void delete(T entity);

    public List<T> findAll();

    public T findById(Integer id);

}
