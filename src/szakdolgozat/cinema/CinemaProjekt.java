package szakdolgozat.cinema;

import szakdolgozat.cinema.db.init.DatabaseInitializer;
import szakdolgozat.cinema.gui.CinemaFrame;

/**
 *
 * @author Zoli
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
