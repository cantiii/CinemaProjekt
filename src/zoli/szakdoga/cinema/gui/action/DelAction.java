package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import zoli.szakdoga.cinema.db.dao.DaoManager;
import zoli.szakdoga.cinema.db.dao.DefaultDao;
import zoli.szakdoga.cinema.db.entity.*;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;


/**
 *
 * @author pappz
 * A törlések megvalósítását végző osztály
 */
public class DelAction implements ActionListener {

    private CinemaFrame parent;
    private JTable table;
    private DefaultDao dao;

    public DelAction(CinemaFrame parent) {
        this.parent = parent;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    //actionListener-t hívunk a művelet lebonyolítása miatt 
    @Override
    public void actionPerformed(ActionEvent e) {
        //egy dialóg ablak, ami rákérdez, hogy biztos töröljük-e
        int answer = JOptionPane.showConfirmDialog(parent, GuiConstants.TORLES, GuiConstants.TORLES_BUT_TEXT, JOptionPane.YES_NO_OPTION);
        //ha a válasz igen volt, el indul a törlési folyamat
        if (answer == JOptionPane.OK_OPTION) {
            // kiválasztott sor elkérése, majd törlése
            int selectedRow = table.getSelectedRow();
            if (selectedRow > -1) {
                int convertRowIndexToModel = table.convertRowIndexToModel(selectedRow);
                GenericTableModel model = (GenericTableModel) table.getModel();
                if(table == parent.getTeremTable()) {  
                    String teremNev = (String) model.getValueAt(selectedRow, 0);                    
                    
                    dao = new DefaultDao(Terem.class);
                    List<Szek> szekId = dao.findAll(dao.findTeremId(teremNev));
                    
                    dao = new DefaultDao(Szek.class);
                    List<Szek> osszesSzek = dao.findAll();
                    
                    GenericTableModel<Szek> szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);
                    
                    JOptionPane.showMessageDialog(parent, teremNev + " teremhez tartozó ("+szekId.size()+"db) is székek törlődnek!", GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                    
                    for(int i=0;i<osszesSzek.size();i++) {
                        for(int j=0;j<szekId.size();j++) {
                            if(osszesSzek.get(i).getId() == szekId.get(j).getId()) {
                                szekModel.removeEntity(szekId.get(j));
                                szekId.remove(j);                           
                            }                              
                        }
                    }
                }
                //a GenericTableModel osztály removeEntity függvénye végzi a törlést
                model.removeEntity(convertRowIndexToModel);
            }
        }
    }

}
