package zoli.szakdoga.cinema.gui.action;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import zoli.szakdoga.cinema.gui.CinemaFrame;
import zoli.szakdoga.cinema.gui.GuiConstants;

/**
 *
 * @author pappz
 * Ez az osztály felelős a jobb klikk opció megjelenítésért
 * Tehát itt tudjuk kiválasztani context menü akcióit
 */
public class StoryRightClickAction extends MouseAdapter {

    private CinemaFrame parent;
    private ShowStoryAction showStory;
    private ShowSzekekAction showSzekek;
    private DelAction delItem;
    private LoginAction logUser;

    public StoryRightClickAction(CinemaFrame parent, ShowStoryAction showStory, ShowSzekekAction showSzekek, DelAction delItem, LoginAction logUser) {
        this.parent = parent;
        this.showStory = showStory;
        this.showSzekek = showSzekek;
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

        JMenuItem leiras = null;       
        if (logUser.getCurrUser().getJog() == 0 || logUser.getCurrUser().getJog() == 2) {
            if(parent.getFilmTable() == (JTable) e.getSource()) {
                leiras = new JMenuItem(GuiConstants.LEIRAS_MENU_TEXT);
                showStory.setTable((JTable) e.getSource());
                leiras.addActionListener(showStory);
                popup.add(leiras);
            }
        }
        
        JMenuItem szekek = null;
        if(parent.getTortenetTable()== (JTable) e.getSource()) {
                szekek = new JMenuItem(GuiConstants.SZEK_MENU_TEXT);
                showSzekek.setTable((JTable) e.getSource());
                szekek.addActionListener(showSzekek);
                popup.add(szekek);
            }

        JMenuItem torles = null;
        if (logUser.getCurrUser().getJog() == 0 || logUser.getCurrUser().getJog() == 1 && !(parent.getMusorTable() == (JTable) e.getSource())) {
            torles = new JMenuItem(GuiConstants.TORLES_BUT_TEXT);
            delItem.setTable((JTable) e.getSource());
            torles.addActionListener(delItem);
            popup.add(torles);
        }

        popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
