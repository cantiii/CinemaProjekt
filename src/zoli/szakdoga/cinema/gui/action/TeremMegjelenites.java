package zoli.szakdoga.cinema.gui.action;

import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.entity.Vetites;
import zoli.szakdoga.cinema.gui.GuiConstants;

/**
 *
 * @author pappz
 */
public class TeremMegjelenites {

    private final Vetites vetites;
    private final Integer jegyDarab;
    private final Integer jegyDiak;

    public TeremMegjelenites(Integer jegyDarab, Integer jegyDiak, Vetites vetites) {
        this.jegyDarab = jegyDarab;
        this.jegyDiak = jegyDiak;
        this.vetites = vetites;
        JOptionPane.showConfirmDialog(null, jegyDarab + " di: " + jegyDiak, GuiConstants.FOGLALAS_BUT_TEXT, JOptionPane.YES_NO_OPTION);
    }

    public void rajzol() {
        // valami terem megjelenítés
    }

}
