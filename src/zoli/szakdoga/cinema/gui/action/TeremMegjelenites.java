package zoli.szakdoga.cinema.gui.action;

import zoli.szakdoga.cinema.db.entity.Vetites;

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
    }

    public void rajzol() {
        // valami terem megjelenítés
    }

}
