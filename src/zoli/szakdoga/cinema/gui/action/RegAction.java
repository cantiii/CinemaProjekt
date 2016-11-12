package zoli.szakdoga.cinema.gui.action;

import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.dao.DaoManager;
import zoli.szakdoga.cinema.db.dao.DefaultDao;
import zoli.szakdoga.cinema.db.entity.Felhasznalo;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author Zoli
 * Ezen osztály segítségével tudunk regisztrálni a rendszerbe
 */
public class RegAction {

    private CinemaFrame parent;
    private DefaultDao dao;

    public RegAction(CinemaFrame parent) {
        this.parent = parent;
        dao = new DefaultDao(Felhasznalo.class);
        regisztracio();
    }

    public void regisztracio() {
        //létrehozunk egy új felhasználó objektumot
        Felhasznalo user = new Felhasznalo();
        user.setNev(readString());
        //regisztrációnal mindenkit sima userként mentünk el, akit a későbbiekben kinevezhetünk majd adminná
        user.setJog(2);
        GenericTableModel<Felhasznalo> userModel = new GenericTableModel(DaoManager.getInstance().getFelhasznaloDao(), Felhasznalo.PROPERTY_NAMES);
        
        //ha sikeres volt a regisztráció, elmentjük a felhasználót a db-be
        userModel.addEntity(user);
    }

    //ez a függvény segít abban, hogy megfelelő névvel tudjunk regisztrálni
    private String readString() {
        String name = null;
        while (name == null) {
            //bekérünk egy név jelöltet
            name = JOptionPane.showInputDialog(parent, GuiConstants.USERNAME_TEXT, GuiConstants.REG_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                // hossz ellenőrzés
                if (name.length() <= 3 || name.length() > 10) {
                    JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_LENGHT, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                // ha van mnár ilyen névvel felhasználó, újra kell próbálkozni
                } else if (dao.findUser(name)) {
                    JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_TAKEN, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                // ha túljutottunk az ellenőrzéseken, elfogadjuk a nevet
                } else {
                    return name;
                }
            // mindenképp szükséges valamit megadni, hogy regisztrálni tudjunk
            } else {
                JOptionPane.showMessageDialog(parent, GuiConstants.USERNAME_REQUIRED, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                name = null;
            }
        }
        return name;
    }
}
