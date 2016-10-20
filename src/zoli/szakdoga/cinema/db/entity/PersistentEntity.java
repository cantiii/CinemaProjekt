package zoli.szakdoga.cinema.db.entity;

import javax.persistence.MappedSuperclass;

/**
 *
 * @author pappz
 */
@MappedSuperclass
public interface PersistentEntity {
    public Integer getId();
    public Object get(int columnIndex);
    public void set(int columnIndex, Object value);
}
