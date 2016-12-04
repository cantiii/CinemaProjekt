package szakdolgozat.cinema;

import javax.swing.SwingUtilities;
import szakdolgozat.cinema.db.init.DatabaseInitializer;
import szakdolgozat.cinema.gui.CinemaFrame;

/**
 *
 * @author Zoli
 */
public class CinemaProjekt {
    
    private static boolean kellInit = false;
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if(kellInit) {
                    DatabaseInitializer.getInstance().init();
                }
                new CinemaFrame().setVisible(true);
            }
        });
    }
}
