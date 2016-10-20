package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.dao.DefaultDao;
import zoli.szakdoga.cinema.db.entity.Felhasznalo;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author Zoli
 */
public class RegAction implements ActionListener {

    private CinemaFrame parent;
    private DefaultDao dao;

    public RegAction(CinemaFrame parent) {
        this.parent = parent;
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        parent.felhasznaloListaPanel();
        Felhasznalo user = new Felhasznalo();
        user.setNev(readString());
        user.setJog(2);

        GenericTableModel userModel = (GenericTableModel) parent.getFelhasznaloTable().getModel();
        userModel.addEntity(user);
    }

    private String readString() {
        String name = null;
        while (name == null) {
            name = JOptionPane.showInputDialog(parent, GuiConstants.USERNAME_TEXT, GuiConstants.REG_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                if (name.length() <= 3 || name.length() > 10) {
                    JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_LENGHT, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                } else {
                    if(dao.findByName(name) != null) {
                        JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_TAKEN, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                        name = null;
                        // TODO :: ELLENŐRIZNI, hogy VAN-e már ilyen user a DB-ben !
                    } else {
                        return name;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_REQUIRED, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            }
        }
        return name;
    }
}
