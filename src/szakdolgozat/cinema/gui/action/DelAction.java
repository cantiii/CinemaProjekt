package szakdolgozat.cinema.gui.action;

import szakdolgozat.cinema.db.entity.Szek;
import szakdolgozat.cinema.db.entity.Vetites;
import szakdolgozat.cinema.db.entity.Terem;
import szakdolgozat.cinema.db.entity.Tartalmaz;
import szakdolgozat.cinema.db.entity.Felhasznalo;
import szakdolgozat.cinema.db.entity.Film;
import szakdolgozat.cinema.db.entity.Mozi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import szakdolgozat.cinema.db.dao.DaoManager;
import szakdolgozat.cinema.db.dao.DefaultDao;
import szakdolgozat.cinema.gui.CinemaFrame;
import szakdolgozat.cinema.gui.GuiConstants;
import szakdolgozat.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz A törlések megvalósítását végző osztály
 */
public class DelAction implements ActionListener {

    private CinemaFrame parent;
    private JTable table;
    private DefaultDao dao;
    private LoginAction logUser;

    public DelAction(CinemaFrame parent, LoginAction logUser) {
        this.parent = parent;
        this.logUser = logUser;
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
                if (table == parent.getMusorATable()) {
                    musorTorles(model, selectedRow);
                } else if (table == parent.getFilmATable()) {
                    filmTorles(model, selectedRow);
                } else if (table == parent.getTeremTable()) {
                    teremTorles(model, selectedRow);
                } else if (table == parent.getMoziTable()) {
                    moziTorles(model, selectedRow);
                } else if (table == parent.getFelhasznaloTable()) {
                    Felhasznalo felhasznalo = (Felhasznalo) model.getRowValue(selectedRow);
                    if (felhasznalo.getId() == logUser.getCurrUser().getId()) {
                        JOptionPane.showMessageDialog(parent, GuiConstants.TORLESFAIL, GuiConstants.TORLES_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                //a GenericTableModel osztály removeEntity függvénye végzi a törlést
                model.removeEntity(convertRowIndexToModel);
            }
        }
    }

    public void musorTorles(GenericTableModel model, int selectedRow) {
        Terem terem = (Terem) model.getValueAt(selectedRow, 1);
        Integer ferohely = terem.getFerohely();

        dao = new DefaultDao(Szek.class);
        Szek kezdoSzek = (Szek) model.getValueAt(selectedRow, 4);
        Integer vegSzekId = kezdoSzek.getId() + (ferohely - 1);
        Szek vegSzek = (Szek) dao.findById(vegSzekId);

        GenericTableModel<Szek> szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);

        for (int i = kezdoSzek.getId(); i <= vegSzek.getId(); i++) {
            szekModel.removeEntity((Szek) dao.findById(i));
        }
        JOptionPane.showMessageDialog(parent, terem.getNev() + " teremhez tartozó (" + ferohely + "db) is székek törlődnek!", GuiConstants.TORLES_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
    }

    public void filmTorles(GenericTableModel model, int selectedRow) {
        Film film = (Film) model.getRowValue(selectedRow);

        dao = new DefaultDao(Vetites.class);
        List<Vetites> vetites = dao.findFilmInVetites(film);

        GenericTableModel<Vetites> vetitesModel = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);

        for (int i = 0; i < vetites.size(); i++) {
            Szek kezdoSzek = vetites.get(i).getSzekId();
            Integer ferohely = vetites.get(i).getTeremId().getFerohely();
            Integer vegSzekId = kezdoSzek.getId() + (ferohely - 1);
            dao = new DefaultDao(Szek.class);
            Szek vegSzek = (Szek) dao.findById(vegSzekId);

            GenericTableModel<Szek> szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);
            for (int j = kezdoSzek.getId(); j <= vegSzek.getId(); j++) {
                szekModel.removeEntity((Szek) dao.findById(j));
            }
            JOptionPane.showMessageDialog(parent, vetites.get(i).getTeremId().getNev() + " teremhez tartozó (" + ferohely + "db) is székek törlődnek!", GuiConstants.TORLES_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);

            vetitesModel.removeEntity(vetites.get(i));
        }
    }

    public void teremTorles(GenericTableModel model, int selectedRow) {
        Terem terem = (Terem) model.getRowValue(selectedRow);

        dao = new DefaultDao(Vetites.class);
        List<Vetites> vetites = dao.findTeremInVetites(terem);
        if (vetites.size() > 0) {
            GenericTableModel<Vetites> vetitesModel = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);

            for (int i = 0; i < vetites.size(); i++) {
                Szek kezdoSzek = vetites.get(i).getSzekId();
                Integer ferohely = vetites.get(i).getTeremId().getFerohely();
                Integer vegSzekId = kezdoSzek.getId() + (ferohely - 1);
                dao = new DefaultDao(Szek.class);
                Szek vegSzek = (Szek) dao.findById(vegSzekId);

                GenericTableModel<Szek> szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);
                for (int j = kezdoSzek.getId(); j <= vegSzek.getId(); j++) {
                    szekModel.removeEntity((Szek) dao.findById(j));
                }
                JOptionPane.showMessageDialog(parent, vetites.get(i).getTeremId().getNev() + " teremhez tartozó (" + ferohely + "db) is székek törlődnek!", GuiConstants.TORLES_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);

                vetitesModel.removeEntity(vetites.get(i));
            }
        }
    }

    public void moziTorles(GenericTableModel model, int selectedRow) {
        Mozi mozi = (Mozi) model.getRowValue(selectedRow);

        GenericTableModel<Terem> teremModel = new GenericTableModel(DaoManager.getInstance().getTeremDao(), Terem.PROPERTY_NAMES);
        dao = new DefaultDao(Terem.class);
        List<Tartalmaz> teremIdInTartalmaz = dao.findAll(mozi);

        List<Terem> terem = new ArrayList<>();
        for (int i = 0; i < teremIdInTartalmaz.size(); i++) {
            terem.add(teremIdInTartalmaz.get(i).getTeremId());
        }

        for (int t = 0; t < terem.size(); t++) {
            dao = new DefaultDao(Vetites.class);
            List<Vetites> vetites = dao.findTeremInVetites(terem.get(t));

            GenericTableModel<Vetites> vetitesModel = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);

            for (int i = 0; i < vetites.size(); i++) {
                Szek kezdoSzek = vetites.get(i).getSzekId();
                Integer ferohely = vetites.get(i).getTeremId().getFerohely();
                Integer vegSzekId = kezdoSzek.getId() + (ferohely - 1);
                dao = new DefaultDao(Szek.class);
                Szek vegSzek = (Szek) dao.findById(vegSzekId);

                GenericTableModel<Szek> szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);
                for (int j = kezdoSzek.getId(); j <= vegSzek.getId(); j++) {
                    szekModel.removeEntity((Szek) dao.findById(j));
                }
                JOptionPane.showMessageDialog(parent, vetites.get(i).getTeremId().getNev() + " teremhez tartozó (" + ferohely + "db) is székek törlődnek!", GuiConstants.TORLES_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);

                vetitesModel.removeEntity(vetites.get(i));
            }
            teremModel.removeEntity(terem.get(t));
        }
    }
}
