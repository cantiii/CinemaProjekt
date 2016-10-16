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
 */
public class ShowStoryAction implements ActionListener{

    private JFrame parent;
    private JTable table;

    public ShowStoryAction(JFrame parent) {
        this.parent = parent;
    }

    public void setTable(JTable table) {
        this.table = table;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
            if (selectedRow > -1) {
                int convertRowIndexToModel = table.convertRowIndexToModel(selectedRow);
                //oszlop elkérésre valami szebb módszer !   
                int convertColIndexToModel = 5;
                GenericTableModel model = (GenericTableModel) table.getModel();
                JOptionPane.showMessageDialog(parent, model.getValueAt(convertRowIndexToModel, convertColIndexToModel), GuiConstants.LEIRAS_MENU_TEXT, JOptionPane.INFORMATION_MESSAGE);
            }
        
    }
    
}
