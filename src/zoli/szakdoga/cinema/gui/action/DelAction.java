package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 * A törlések megvalósítását végző osztály
 */
public class DelAction implements ActionListener {

    private JFrame parent;
    private JTable table;

    public DelAction(JFrame parent) {
        this.parent = parent;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    //actionListener-t hívunk a művelet lebonyolítása miatt 
    @Override
    public void actionPerformed(ActionEvent e) {
        //egy dialóg ablak, ami rákérdez, hogy biztos töröljük-e
        int answer = JOptionPane.showConfirmDialog(parent, "Biztos törli?", GuiConstants.TORLES_BUT_TEXT, JOptionPane.YES_NO_OPTION);
        //ha a válasz igen volt, el indul a törlési folyamat
        if (answer == JOptionPane.OK_OPTION) {
            // kiválasztott sor elkérése, majd törlése
            int selectedRow = table.getSelectedRow();
            if (selectedRow > -1) {
                int convertRowIndexToModel = table.convertRowIndexToModel(selectedRow);
                GenericTableModel model = (GenericTableModel) table.getModel();
                //a GenericTableModel osztály removeEntity függvénye végzi a törlést
                model.removeEntity(convertRowIndexToModel);
            }
        }
    }

}
