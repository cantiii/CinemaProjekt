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
    private DelAction delItem;
    private LoginAction logUser;
    private FoglalasAction foglalasAction;

    public StoryRightClickAction(CinemaFrame parent, ShowStoryAction showStory, DelAction delItem, LoginAction logUser, FoglalasAction foglalasAction) {
        this.parent = parent;
        this.showStory = showStory;
        this.delItem = delItem;
        this.logUser = logUser;
        this.foglalasAction = foglalasAction;
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
        JMenuItem foglalas = null;
        if (logUser.getCurrUser().getJog() == 0 || logUser.getCurrUser().getJog() == 2) {
            if(parent.getFilmTable() == (JTable) e.getSource()) {
                leiras = new JMenuItem(GuiConstants.LEIRAS_MENU_TEXT);
                showStory.setTable((JTable) e.getSource());
                leiras.addActionListener(showStory);
                popup.add(leiras);
            } else if (parent.getMusorTable() == (JTable) e.getSource()) {
                foglalas = new JMenuItem(GuiConstants.FOGLALAS_BUT_TEXT);
                foglalasAction.setTable((JTable) e.getSource());
                foglalas.addActionListener(foglalasAction);
                popup.add(foglalas);
            }
        }

        JMenuItem torles = null;
        if (logUser.getCurrUser().getJog() == 0 || logUser.getCurrUser().getJog() == 1) {
            torles = new JMenuItem(GuiConstants.TORLES_BUT_TEXT);
            delItem.setTable((JTable) e.getSource());
            torles.addActionListener(delItem);
            popup.add(torles);
        }

        popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
