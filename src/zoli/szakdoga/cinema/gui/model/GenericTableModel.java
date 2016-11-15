package zoli.szakdoga.cinema.gui.model;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import zoli.szakdoga.cinema.db.dao.DefaultDao;
import zoli.szakdoga.cinema.db.dao.GenericDao;
import zoli.szakdoga.cinema.db.entity.*;

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
            T item = items.get(rowIndex); // meg van az entitás minden eleme a sor miatt
            item.set(columnIndex, aValue); // az entitás columnIndex. oszlopában megváltoztatja az értéket
            updateEntity(item, rowIndex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !PROPERTY_NAMES[columnIndex].equals("Férőhely"); // jobb megoldás ?
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

    /**
     * 
     * @param item - egy generikus entitás, amit hozzá szeretnénk adni az adatbázishoz 
     */
    public void addEntity(T item) {
        DAO.create(item);
        fireTableDataChanged();
    }

    /**
     * 
     * @param rowIndex - egy sornak a száma, amiből meghatározzuk a pontos entitást, amit törölni szeretnénk
     */
    public void removeEntity(int rowIndex) {
        T entity = items.get(rowIndex);
        items.remove(rowIndex);
        fireTableDataChanged();
        DAO.delete(entity);
    }
    
    public void removeEntity(T item) {
        DefaultDao dao = new DefaultDao(Szek.class);
        DAO.delete(item);
        fireTableDataChanged();       
    }
    
    /**
     * 
     * @param item - az szerkesztett/módosított entitás
     * @param rowIndex - az update helye
     */
    public void updateEntity(T item, int rowIndex) {
        T entity = items.get(rowIndex);
        DAO.update(entity);
        fireTableDataChanged();
    }

}
