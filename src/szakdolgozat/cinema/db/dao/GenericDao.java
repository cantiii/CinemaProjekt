package szakdolgozat.cinema.db.dao;

import java.util.List;
import szakdolgozat.cinema.db.entity.PersistentEntity;

/**
 *
 * @author Papp Zoltán - VMW84B
 */
public interface GenericDao<T extends PersistentEntity> {

    public void create(T entity);

    public void update(T entity);

    public void delete(T entity);

    public List<T> findAll();

    public T findById(Integer id);

}
