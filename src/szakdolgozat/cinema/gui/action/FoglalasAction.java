package szakdolgozat.cinema.gui.action;

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
import szakdolgozat.cinema.db.dao.DaoManager;
import szakdolgozat.cinema.db.entity.Foglalas;
import szakdolgozat.cinema.db.entity.Szek;
import szakdolgozat.cinema.gui.CinemaFrame;
import szakdolgozat.cinema.gui.GuiConstants;
import szakdolgozat.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 */
public class FoglalasAction extends MouseAdapter {

    private CinemaFrame parent;
    private Szek szek;
    private JLabel szekLabel;
    private Integer jegyDarab;
    private LoginAction logUser;
    private static int foglalDarab;
    private static List<Szek> kijeloltSzekLista;
    private static List<JLabel> labelLista;

    public FoglalasAction(CinemaFrame parent, Szek szek, JLabel szekLabel, Integer jegyDarab, LoginAction logUser) {
        this.parent = parent;
        this.szek = szek;
        this.szekLabel = szekLabel;
        this.jegyDarab = jegyDarab;
        this.logUser = logUser;

        foglalDarab = 0;
        kijeloltSzekLista = new ArrayList<>();
        labelLista = new ArrayList<>();
    }

    @Override
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
                    JOptionPane.showMessageDialog(null, GuiConstants.FOGLALT_SZEK, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                } else {
                    ImageIcon xIcon = new ImageIcon(xSzek);
                    szekLabel.setIcon(xIcon);
                    labelLista.add(szekLabel);
                    foglalDarab = foglalDarab + 1;
                    kijeloltSzekLista.add(szek);
                    if (jegyDarab == foglalDarab) {
                        veglegesit();
                    }
                }
            }
            if (SwingUtilities.isRightMouseButton(e)) {
                if (kijeloltSzekLista.contains(szek)) {
                    int answer = JOptionPane.showConfirmDialog(null, GuiConstants.TORLES, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.OK_OPTION) {
                        ImageIcon szIcon = new ImageIcon(szSzek);
                        szekLabel.setIcon(szIcon);
                        labelLista.remove(szekLabel);
                        foglalDarab = foglalDarab - 1;
                        kijeloltSzekLista.remove(szek);
                    }
                }
            }
        }
    }

    public void veglegesit() {
        int answer = JOptionPane.showConfirmDialog(null, GuiConstants.FOGLALAS_MENTES, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.OK_OPTION) {
            GenericTableModel<Szek> szekModel = null;
            GenericTableModel<Foglalas> foglalasModel = null;
            for (int i = 0; i < kijeloltSzekLista.size(); i++) {
                szekModel = new GenericTableModel(DaoManager.getInstance().getSzekDao(), Szek.PROPERTY_NAMES);
                kijeloltSzekLista.get(i).setFoglalt(true);
                szekModel.updateSzek(kijeloltSzekLista.get(i));

                foglalasModel = new GenericTableModel(DaoManager.getInstance().getFoglalasDao(), Foglalas.PROPERTY_NAMES);
                Foglalas foglalas = new Foglalas();
                foglalas.setFelhasznaloId(logUser.getCurrUser());
                foglalas.setSzekId(kijeloltSzekLista.get(i));
                foglalasModel.addEntity(foglalas);
            }

            BufferedImage fSzek = null;
            try {
                fSzek = ImageIO.read(new File("src/pic/szfoglalt.png"));
            } catch (IOException ex) {
            }
            ImageIcon fIcon = new ImageIcon(fSzek);
            for (int i = 0; i < labelLista.size(); i++) {
                labelLista.get(i).setIcon(fIcon);
                labelLista.get(i).revalidate();
            }

            JOptionPane.showMessageDialog(null, GuiConstants.FOGLALAS_VEGE, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            kijeloltSzekLista.clear();
            foglalDarab = 0;
            jegyDarab = 0;
            parent.removeElrendezes();
        } else if (answer == JOptionPane.NO_OPTION) {
            BufferedImage szSzek = null;
            try {
                szSzek = ImageIO.read(new File("src/pic/szszabad.png"));
            } catch (IOException ex) {
            }
            ImageIcon szIcon = new ImageIcon(szSzek);

            for (int i = 0; i < labelLista.size(); i++) {
                labelLista.get(i).setIcon(szIcon);
                labelLista.get(i).revalidate();
            }

            labelLista.clear();
            foglalDarab = 0;
            kijeloltSzekLista.clear();
        }
    }
}
