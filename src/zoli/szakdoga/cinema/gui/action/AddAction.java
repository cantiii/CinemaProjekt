package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import zoli.szakdoga.cinema.db.entity.*;
import zoli.szakdoga.cinema.gui.*;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 */
public class AddAction implements ActionListener {

    private CinemaFrame parent;
    //private JTable table;

    public AddAction(CinemaFrame parent) {
        this.parent = parent;
    }

    /*public void setTable(JTable table) {
        this.table = table;
    }*/
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case FELVITEL_MUSOR_TEXT:
                JOptionPane.showMessageDialog(parent, "musor");
                break;
            case FELVITEL_FILM_TEXT:
                Film film = new Film();
                film.setCim(readString(FELVITEL_FILM_TEXT));
                film.setRendezo(readString(FELVITEL_FILMRENDEZO_TEXT));
                film.setSzinesz(readString(FELVITEL_FILMSZINESZ_TEXT));
                film.setLeiras(readString(FELVITEL_FILMLEIRAS_TEXT));
                film.setHossz(readNumber(FELVITEL_FILMHOSSZ_TEXT));
                film.setKorhatar(readNumber(FELVITEL_FILMKOR_TEXT));
                      
                GenericTableModel filmModel = (GenericTableModel) parent.getFilmTable().getModel();
                filmModel.addEntity(film);
                break;
            case FELVITEL_MOZI_TEXT:
                Mozi mozi = new Mozi();
                mozi.setNev(readString(FELVITEL_MOZI_TEXT));

                GenericTableModel moziModel = (GenericTableModel) parent.getMoziTable().getModel();
                moziModel.addEntity(mozi);
                break;
            case FELVITEL_TEREM_TEXT:
                Terem terem = new Terem();
                terem.setNev(readString(FELVITEL_TEREM_TEXT));
                terem.setFerohely(readNumber(FELVITEL_TEREMHELY_TEXT));

                GenericTableModel teremModel = (GenericTableModel) parent.getTeremTable().getModel();
                teremModel.addEntity(terem);
                break;
            case FELVITEL_FELHASZNALO_TEXT:
                JOptionPane.showMessageDialog(parent, "felhasznalo");
                break;
        }
    }
/*
        if (table == parent.getMusorTable()) {
            JOptionPane.showMessageDialog(parent, "musor");
        } else if (table == parent.getFilmTable()) {
            JOptionPane.showMessageDialog(parent, "film");
        } else if (table == parent.getMoziTable()) {
            Mozi mozi = new Mozi();
            mozi.setNev(readString());

            GenericTableModel userModel = (GenericTableModel) parent.getMoziTable().getModel();
            userModel.addEntity(mozi);
        } else if (table == parent.getTeremTable()) {
            JOptionPane.showMessageDialog(parent, "terem");
        } else if (table == parent.getFelhasznaloTable()) {
            JOptionPane.showMessageDialog(parent, "felhasznalo");
        } else {
            JOptionPane.showMessageDialog(parent, "valaminemoké");
        }
    }
*/
    private String readString(String label) {
        String name = null;
        while (name == null) {
            name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                return name;
            }
        }
        return name;
    }
    
    private Integer readNumber(String label) {
        Integer number = null;
        do {
            String str = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            try {
                number = Integer.parseInt(str);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, GuiConstants.INVALID_NUMBER, GuiConstants.INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } while (number == null);
        return number;
    }

}
