package zoli.szakdoga.cinema.gui.action;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import zoli.szakdoga.cinema.gui.GuiConstants;

/**
 *
 * @author pappz
 * Ez az oosztály felelős a jobb klikk opció megjelenítéért
 * Tehát itt tudjuk kiválasztani context menü akcióit
 */
public class StoryRightClickAction extends MouseAdapter {

    private ShowStoryAction showStory;
    private DelAction delItem;
    private LoginAction logUser;

    public StoryRightClickAction(ShowStoryAction showStory, DelAction delItem, LoginAction logUser) {
        this.showStory = showStory;
        this.delItem = delItem;
        this.logUser = logUser;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            Point p = e.getPoint();
            JTable table = (JTable) e.getSource();
            int rowNumber = table.rowAtPoint(p);
            ListSelectionModel model = table.getSelectionModel();
            model.setSelectionInterval(rowNumber, rowNumber);
            createPopup(e);
        }
    }

    private void createPopup(MouseEvent e) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem leiras = new JMenuItem(GuiConstants.LEIRAS_MENU_TEXT);
        JMenuItem torles = new JMenuItem(GuiConstants.TORLES_BUT_TEXT);
        //ha nem sima user, akkor nem látja a leírás menüelemet
        if (logUser.currUser.getJog() != 2) {
            leiras.setVisible(false);
        }
        //ha nem admin, nem látja a törlés menüelemet
        if (logUser.currUser.getJog() != 1) {
            torles.setVisible(false);
        }

        showStory.setTable((JTable) e.getSource());
        leiras.addActionListener(showStory);
        delItem.setTable((JTable) e.getSource());
        torles.addActionListener(delItem);

        popup.add(leiras);
        popup.add(torles);
        popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
