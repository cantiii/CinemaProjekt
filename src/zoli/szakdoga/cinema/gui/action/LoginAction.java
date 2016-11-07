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
    public String nev;
    public int jog;
    
    public LoginAction(CinemaFrame parent) {
        this.parent = parent;
        currUser = readUniqueString();
    }

    // ! ! ! be leeht jelentkezni null névvel is
    private Felhasznalo readUniqueString() {
        Felhasznalo getUser = new Felhasznalo();
        String name = null;
        dao = new DefaultDao(Felhasznalo.class);
        while (name == null) {
            name = JOptionPane.showInputDialog(parent, GuiConstants.LOGIN_BUT_TEXT, GuiConstants.LOGIN_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                if (dao.isUser(name) != null) { 
                    getUser = dao.isUser(name);
                    jog = getUser.getJog();
                    nev = getUser.getNev();
                    return getUser;
                } else {
                    JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_NO, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                }
            }
        }
        return getUser;
    }

    public int getJog() {
        return jog;
    }
 
}
