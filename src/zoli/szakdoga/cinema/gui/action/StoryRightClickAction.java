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
 */
public class StoryRightClickAction extends MouseAdapter {

    private ShowStoryAction showStory;
    private AddAction addItem;
    private DelAction delItem;

    public StoryRightClickAction() {
    }

    public StoryRightClickAction(ShowStoryAction showStory, AddAction addItem, DelAction delItem) {
        this.showStory = showStory;
        this.addItem = addItem;
        this.delItem = delItem;
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
        JMenuItem felvitel = new JMenuItem(GuiConstants.FELVITEL_BUT_TEXT);
        JMenuItem torles = new JMenuItem(GuiConstants.TORLES_BUT_TEXT);

        showStory.setTable((JTable) e.getSource());
        leiras.addActionListener(showStory);
        addItem.setTable((JTable) e.getSource());
        felvitel.addActionListener(addItem);
        delItem.setTable((JTable) e.getSource());
        torles.addActionListener(delItem);

        popup.add(leiras);
        popup.add(felvitel);
        popup.add(torles);
        popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
