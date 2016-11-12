package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 * Ennek az osztálynak a segítségével tudjuk a film leírását előhozni a felületre
 */
public class ShowStoryAction implements ActionListener{

    private CinemaFrame parent;
    private JTable table;

    public ShowStoryAction(CinemaFrame parent) {
        this.parent = parent;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        //leírás csak a FIlm táblában van, így ezt meg kell vizsgálni
        if(table == parent.getFilmTable()) {
            if (selectedRow > -1) {
                int convertRowIndexToModel = table.convertRowIndexToModel(selectedRow);
                int convertColIndexToModel = 5;
                //elkérjük a táblánk modeljét, hogy az, illetve sor és oszlopszám alapján megtaláljuk a leírást
                GenericTableModel model = (GenericTableModel) table.getModel();
                JOptionPane.showMessageDialog(parent, model.getValueAt(convertRowIndexToModel, convertColIndexToModel), GuiConstants.LEIRAS_MENU_TEXT, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
}
