package zoli.szakdoga.cinema.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;

/**
 *
 * @author Zoli
 */
public class CinemaFrame extends JFrame {
    
    private final JPanel panelCont = new JPanel();
    private final JPanel panelIndex = new JPanel();
    private final JPanel panelAr = new JPanel();
    private final JPanel panelKapcsolat = new JPanel();
    private final CardLayout cl = new CardLayout();
    
    public CinemaFrame() {
        initFrame();
        
        setMenu();
        setCenter();

        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }
    
    private void initFrame() {
        setTitle(FRAME_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
    }
    
    private void setMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenuItem index = new JMenuItem(new AbstractAction(INDEX_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "1");
            }
        });
        JMenuItem musor = new JMenuItem(MUSOR_MENU_TEXT);
        JMenuItem film = new JMenuItem(FILM_MENU_TEXT);
        JMenuItem ar = new JMenuItem(new AbstractAction(AR_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "2");
            }
        });
        JMenuItem kapcsolat = new JMenuItem(new AbstractAction(KAPCSOLAT_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "3");
            }
        });
        JMenuItem admin = new JMenuItem(ADMIN_MENU_TEXT);
        JMenuItem tortenet = new JMenuItem(TORTENET_MENU_TEXT);
        JMenuItem logout = new JMenuItem(LOGOUT_MENU_TEXT);
        
        menuBar.add(index);
        menuBar.add(musor);
        menuBar.add(film);
        menuBar.add(ar);
        menuBar.add(kapcsolat);
        menuBar.add(admin);
        menuBar.add(tortenet);
        menuBar.add(logout);
        setJMenuBar(menuBar);
    }
    
    private void setCenter() {
        panelCont.setLayout(cl);
        
        panelCont.add(panelIndex, "1");
        panelAr.setBackground(Color.red);
	panelCont.add(panelAr, "2");
        panelKapcsolat.setBackground(Color.BLUE);
        panelCont.add(panelKapcsolat, "3");
	cl.show(panelCont, "1");
        
        add(panelCont, BorderLayout.CENTER);
    }
    
}
