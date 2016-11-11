package zoli.szakdoga.cinema.gui.action;

import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.dao.DefaultDao;
import zoli.szakdoga.cinema.db.entity.Felhasznalo;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;

/**
 *
 * @author Zoli
 */
public class LoginAction {

    private CinemaFrame parent;
    private DefaultDao dao;
    public Felhasznalo currUser;
    private RegAction regAct;

    public LoginAction(CinemaFrame parent) {
        this.parent = parent;
        currUser = readUniqueString();
    }

    private Felhasznalo readUniqueString() {
        Felhasznalo getUser = new Felhasznalo();
        String name = null;
        dao = new DefaultDao(Felhasznalo.class);
        int i = 0;
        while (name == null) {
            i++;
            name = JOptionPane.showInputDialog(parent, GuiConstants.LOGIN_BUT_TEXT, GuiConstants.LOGIN_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                if (dao.isUser(name) != null) {
                    getUser = dao.isUser(name);
                    return getUser;
                } else {
                    JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_NO, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                }
            } else {
                JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_REQUIRED, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                name = null;
            }
            if (i == 3) {
                JOptionPane.showMessageDialog(parent, GuiConstants.NOMORE_TRY, GuiConstants.FAIL, JOptionPane.INFORMATION_MESSAGE);
                regAct = new RegAction(parent);
            }
        }
        return getUser;
    }
}
