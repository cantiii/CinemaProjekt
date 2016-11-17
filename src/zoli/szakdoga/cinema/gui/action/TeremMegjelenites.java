package zoli.szakdoga.cinema.gui.action;

import zoli.szakdoga.cinema.db.entity.Vetites;

/**
 *
 * @author pappz
 */
public class TeremMegjelenites {

    private final Vetites vetites;
    private final Integer jegyDarab;

    public TeremMegjelenites(Integer jegyDarab, Vetites vetites) {
        this.jegyDarab = jegyDarab;
        this.vetites = vetites;
    }

    public void rajzol() {
        // valami terem megjelenítés
    }

}
