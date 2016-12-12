package szakdolgozat.cinema.db.entity;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author Papp Zoltán - VMW84B
 */
@MappedSuperclass
public interface PersistentEntity {
    public Integer getId();
    public Object get(int columnIndex);
    public void set(int columnIndex, Object value);
}
