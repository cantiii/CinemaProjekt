/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    public StoryRightClickAction(ShowStoryAction showStory) {
        this.showStory = showStory;
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
        
        showStory.setTable((JTable) e.getSource());
        leiras.addActionListener(showStory);
        popup.add(leiras);
        popup.show(e.getComponent(), e.getX(), e.getY());
    }
}
