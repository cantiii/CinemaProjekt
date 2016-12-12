package szakdolgozat.cinema.gui.action;

import javax.swing.JOptionPane;
import szakdolgozat.cinema.db.dao.DefaultDao;
import szakdolgozat.cinema.db.entity.Felhasznalo;
import szakdolgozat.cinema.gui.CinemaFrame;
import szakdolgozat.cinema.gui.GuiConstants;

/**
 *
 * @author Papp Zoltán - VMW84B
 * A bejelentkezésért felelős osztályja a programnak
 */
public class LoginAction {

    private CinemaFrame parent;
    private Felhasznalo currUser;

    public LoginAction(CinemaFrame parent) {
        this.parent = parent;
        currUser = readUniqueString();
    }

    private Felhasznalo readUniqueString() {
        Felhasznalo getUser = new Felhasznalo();
        String name = null;
        DefaultDao dao = new DefaultDao(Felhasznalo.class);
        int i = 0;
        while (name == null) {
            i++;
            name = JOptionPane.showInputDialog(parent, GuiConstants.LOGIN_BUT_TEXT, GuiConstants.LOGIN_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                //ha megtaláljuk a user nevet az adatbázisban beengedjük a felhasználót
                if (dao.isUser(name) != null) {
                    getUser = dao.isUser(name);
                    return getUser;
                    //ha nem létező felhasználó nevet adunk meg figyelmeztetést kapunk
                } else {
                    JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_NO, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                }
                //név nélkül nem lehet bejelentkezni
            } else {
                JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_REQUIRED, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                name = null;
            }
            //ha 3-szor sikertelenül probálkoztunk átírányít a rendszer a regisztrációhoz
            if (i == 3) {
                JOptionPane.showMessageDialog(parent, GuiConstants.NOMORE_TRY, GuiConstants.FAIL, JOptionPane.INFORMATION_MESSAGE);
                new RegAction(parent);
            }
        }
        return getUser;
    }

    public Felhasznalo getCurrUser() {
        return currUser;
    }
}
