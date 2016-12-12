package szakdolgozat.cinema;

import szakdolgozat.cinema.db.init.DatabaseInitializer;
import szakdolgozat.cinema.gui.CinemaFrame;

/**
 *
 * @author Papp Zoltán - VMW84B
 * Szakdolgozat
 * Grafikus felületű jegyfoglaló és kezelő rendszer Java programozási nyelven
 * Budapest, 2016
 */
public class CinemaProjekt {

    private static boolean kellInit = false;

    public static void main(String[] args) {
        if (kellInit) {
            DatabaseInitializer.getInstance().init();
        }
        new CinemaFrame().setVisible(true);
    }
}
