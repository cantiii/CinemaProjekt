package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import zoli.szakdoga.cinema.db.dao.DefaultDao;
import zoli.szakdoga.cinema.db.entity.*;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 */
public class ShowSzekekAction implements ActionListener {

    private CinemaFrame parent;
    private JTable table;
    private LoginAction logUser;
    private DefaultDao dao;

    public ShowSzekekAction(CinemaFrame parent, LoginAction logUser) {
        this.parent = parent;
        this.logUser = logUser;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow > -1) {
            int convertRowIndexToModel = table.convertRowIndexToModel(selectedRow);
            GenericTableModel model = (GenericTableModel) table.getModel();

            Terem terem = (Terem) model.getValueAt(convertRowIndexToModel, 1);
            Integer ferohely = terem.getFerohely();

            dao = new DefaultDao(Szek.class);
            Szek kezdoSzek = (Szek) model.getValueAt(convertRowIndexToModel, 3);
            Integer vegSzekId = kezdoSzek.getId() + (ferohely - 1);
            Szek vegSzek = (Szek) dao.findById(vegSzekId);

            List<Szek> szekLista = new ArrayList<>();
            for (int i = kezdoSzek.getId(); i <= vegSzek.getId(); i++) {
                Szek add = (Szek) dao.findById(i);
                if(add.getFoglalt() == true) {
                    szekLista.add(add);
                }                
            }

            dao = new DefaultDao(Foglalas.class);
            List<Foglalas> foglaltSzekLista = new ArrayList<>();
            for (int i = 0; i < szekLista.size(); i++) {
                Foglalas add = (Foglalas) dao.findFoglalasBySzek(szekLista.get(i));
                if (add != null) {
                    foglaltSzekLista.add(add);
                }
            }

            List<Foglalas> sajatSzekLista = new ArrayList<>();
            for (int i = 0; i < foglaltSzekLista.size(); i++) {
                if (Objects.equals(foglaltSzekLista.get(i).getFelhasznaloId().getId(), logUser.getCurrUser().getId())) {
                    sajatSzekLista.add(foglaltSzekLista.get(i));
                }
            }
            if (!sajatSzekLista.isEmpty()) {
                JOptionPane.showMessageDialog(parent, sajatSzekLista.toString() + " - (db:" + sajatSzekLista.size() + ")", GuiConstants.SZEK_MENU_TEXT, JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(parent, GuiConstants.NOSZEK, GuiConstants.SZEK_MENU_TEXT, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
