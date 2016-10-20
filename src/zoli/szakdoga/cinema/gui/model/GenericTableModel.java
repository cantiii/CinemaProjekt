package zoli.szakdoga.cinema.gui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import zoli.szakdoga.cinema.db.dao.GenericDao;
import zoli.szakdoga.cinema.db.entity.PersistentEntity;

/**
 *
 * @author Zoli
 */
public class GenericTableModel<T extends PersistentEntity> extends AbstractTableModel {

    private List<T> items;
    private final GenericDao<T> DAO;
    private final String PROPERTY_NAMES[];

    public GenericTableModel(final GenericDao<T> DAO, final String[] PROPERTY_NAMES) {
        this.DAO = DAO;
        this.items = new ArrayList<>();
        this.PROPERTY_NAMES = PROPERTY_NAMES;
    }

    @Override
    public int getRowCount() {
        items = DAO.findAll();
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return PROPERTY_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return items.get(rowIndex).get(columnIndex);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (getRowCount() > rowIndex) {
            T item = items.get(rowIndex);
            item.set(columnIndex, aValue);
            updateEntity(item, rowIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() > 0) {
            return getValueAt(0, columnIndex).getClass();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return PROPERTY_NAMES[column];
    }

    public void addEntity(T item) {
        DAO.create(item);
        fireTableDataChanged();
    }

    public void removeEntity(int rowIndex) {
        T entity = items.get(rowIndex);
        DAO.delete(entity);
        fireTableDataChanged();
    }
    
    public void updateEntity(T item, int rowIndex) {
        T entity = items.get(rowIndex);
        DAO.update(entity);
        fireTableDataChanged();
    }

}
