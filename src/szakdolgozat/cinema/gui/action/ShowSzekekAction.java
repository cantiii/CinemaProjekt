package szakdolgozat.cinema.gui.action;

import szakdolgozat.cinema.db.entity.Szek;
import szakdolgozat.cinema.db.entity.Terem;
import szakdolgozat.cinema.db.entity.Foglalas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import szakdolgozat.cinema.db.dao.DefaultDao;
import szakdolgozat.cinema.gui.CinemaFrame;
import szakdolgozat.cinema.gui.GuiConstants;
import szakdolgozat.cinema.gui.model.GenericTableModel;

/**
 *
 * @author Papp Zoltán - VMW84B
 * Történet panelen mutatja meg, hogy volt-e szék foglalásunk egy adott vetítésre
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
            //terem függvényében összegyűjti a hozzá tartozó székeket
            Terem terem = (Terem) model.getValueAt(convertRowIndexToModel, 1);
            Integer ferohely = terem.getFerohely();

            dao = new DefaultDao(Szek.class);
            Szek kezdoSzek = (Szek) model.getValueAt(convertRowIndexToModel, 4);
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
            //ahol a saját id szerepel, azokat a székeket gyűjti össze
            List<Foglalas> sajatSzekLista = new ArrayList<>();
            for (int i = 0; i < foglaltSzekLista.size(); i++) {
                if (Objects.equals(foglaltSzekLista.get(i).getFelhasznaloId().getId(), logUser.getCurrUser().getId())) {
                    sajatSzekLista.add(foglaltSzekLista.get(i));
                }
            }
            //ha nem üres, akkor kiírásra kerül a lista
            if (!sajatSzekLista.isEmpty()) {
                JOptionPane.showMessageDialog(parent, sajatSzekLista.toString() + " - (db:" + sajatSzekLista.size() + ")", GuiConstants.SZEK_MENU_TEXT, JOptionPane.INFORMATION_MESSAGE);
            //ellenkező esetben tájékoztatás, hogy erre még nem volt foglalás
            } else {
                JOptionPane.showMessageDialog(parent, GuiConstants.NOSZEK, GuiConstants.SZEK_MENU_TEXT, JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}
