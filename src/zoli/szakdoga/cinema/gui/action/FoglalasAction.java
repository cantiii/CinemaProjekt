package zoli.szakdoga.cinema.gui.action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import zoli.szakdoga.cinema.db.dao.DaoManager;
import zoli.szakdoga.cinema.db.entity.Foglalas;
import zoli.szakdoga.cinema.db.entity.Szek;
import zoli.szakdoga.cinema.gui.GuiConstants;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 */
public class FoglalasAction extends MouseAdapter {

    private Szek szek;
    private JLabel szekLabel;
    private Integer jegyDarab;
    private static int foglalDarab = 0;
    private static List<Szek> kijeloltSzekLista = new ArrayList<>();
    private LoginAction logUser;

    public FoglalasAction(Szek szek, JLabel szekLabel, Integer jegyDarab, LoginAction logUser) {
        this.szek = szek;
        this.szekLabel = szekLabel;
        this.jegyDarab = jegyDarab;
        this.logUser = logUser;
    }

    public void mousePressed(MouseEvent e) {
        if (jegyDarab != foglalDarab) {
            BufferedImage xSzek = null;
            BufferedImage szSzek = null;
            try {
                xSzek = ImageIO.read(new File("src/pic/szx.png"));
                szSzek = ImageIO.read(new File("src/pic/szszabad.png"));
            } catch (IOException ex) {
            }

            if (SwingUtilities.isLeftMouseButton(e)) {
                if (kijeloltSzekLista.contains(szek)) {
                    JOptionPane.showMessageDialog(null, "foglalt már", GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ImageIcon xIcon = new ImageIcon(xSzek);
                    szekLabel.setIcon(xIcon);
                    foglalDarab = foglalDarab + 1;
                    kijeloltSzekLista.add(szek);
                    if (jegyDarab == foglalDarab) {
                        veglegesit();
                    }
                }
            }
//            if (SwingUtilities.isRightMouseButton(e)) {
//                int answer = JOptionPane.showConfirmDialog(null, GuiConstants.TORLES, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.YES_NO_OPTION);
//                if (answer == JOptionPane.OK_OPTION) {
//                    ImageIcon szIcon = new ImageIcon(szSzek);
//                    szekLabel.setIcon(szIcon);
//                    foglalDarab = foglalDarab - 1;
//                }
//            }
        }
    }

    public void veglegesit() {
        int answer = JOptionPane.showConfirmDialog(null, "Mentés?", GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.YES_NO_OPTION);
        GenericTableModel<Szek> szekModel = null;
        GenericTableModel<Foglalas> foglalasModel = null;
        if (answer == JOptionPane.OK_OPTION) {
            for (int i = 0; i < kijeloltSzekLista.size(); i++) {
                szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);               
                kijeloltSzekLista.get(i).setFoglalt(true);
                szekModel.updateSzek(kijeloltSzekLista.get(i));
                
                foglalasModel = new GenericTableModel(DaoManager.getInstance().getFoglalasDao(), Foglalas.PROPERTY_NAMES);
                Foglalas foglalas = new Foglalas();
                foglalas.setFelhasznaloId(logUser.getCurrUser());
                foglalas.setSzekId(szek);
                foglalasModel.addEntity(foglalas);
            }
            JOptionPane.showMessageDialog(null, "mentve", GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            kijeloltSzekLista.clear();
            foglalDarab = 0;
        }
    }
}
