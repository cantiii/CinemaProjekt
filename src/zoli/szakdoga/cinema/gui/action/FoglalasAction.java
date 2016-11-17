package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
public class FoglalasAction implements ActionListener {

    private static Object[] JEGYEK = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private CinemaFrame parent;
    private JTable table;
    private DefaultDao dao;
    private TeremMegjelenites teremMegjelenetes;

    public FoglalasAction(CinemaFrame parent) {
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
            //elkérjük a táblánk modeljét, hogy az, illetve sor és oszlopszám alapján megtaláljuk a leírást
            GenericTableModel model = (GenericTableModel) table.getModel();
            Vetites valasztottVetites = (Vetites) model.getRowValue(convertRowIndexToModel);
            String adatok = "Választott vetítés adatai:"
                    + "\nFILM: "
                    + valasztottVetites.getFilmId()
                    + "\nTEREM: "
                    + valasztottVetites.getTeremId()
                    + "\nDÁTUM: "
                    + valasztottVetites.getMikor();
            int answer = JOptionPane.showConfirmDialog(parent, adatok, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.OK_OPTION) {
                Szek kezdoSzek = valasztottVetites.getSzekId();
                Integer ferohely = valasztottVetites.getTeremId().getFerohely();
                Integer vegSzekId = kezdoSzek.getId() + (ferohely - 1);
                dao = new DefaultDao(Szek.class);
                Szek vegSzek = (Szek) dao.findById(vegSzekId);

                List<Szek> szekLista = new ArrayList<>();
                for (int i = kezdoSzek.getId(); i <= vegSzek.getId(); i++) {
                    Szek add = (Szek) dao.findById(i);
                    szekLista.add(add);
                }

                int szabadHely = 0;
                List<Integer> jegyAkt = new ArrayList<>();
                for (int j = 0; j < szekLista.size(); j++) {
                    if (szekLista.get(j).getFoglalt() == false) {
                        szabadHely++;
                        jegyAkt.add(szabadHely);
                    }
                }
                Integer jegyDarab = null;
                if (szabadHely < JEGYEK.length) {
                    JEGYEK = jegyAkt.toArray();
                    jegyDarab = (Integer) JOptionPane.showInputDialog(parent, GuiConstants.JEGY_DB, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, JEGYEK, JEGYEK[0]);
                    teremMegjelenetes = new TeremMegjelenites(jegyDarab, valasztottVetites);
                } else {
                    jegyDarab = (Integer) JOptionPane.showInputDialog(parent, GuiConstants.JEGY_DB, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, JEGYEK, JEGYEK[0]);
                    teremMegjelenetes = new TeremMegjelenites(jegyDarab, valasztottVetites);
                }
            }
        }
    }
}
