package zoli.szakdoga.cinema.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;

/**
 *
 * @author Zoli
 */
public class CinemaFrame extends JFrame {
    
    private final JPanel jPanel = new JPanel();
    
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
        JMenu index = new JMenu(INDEX_MENU_TEXT);
        JMenu musor = new JMenu(MUSOR_MENU_TEXT);
        JMenu film = new JMenu(FILM_MENU_TEXT);
        JMenu ar = new JMenu(AR_MENU_TEXT);
        JMenu kapcsolat = new JMenu(KAPCSOLAT_MENU_TEXT);
        JMenu admin = new JMenu(ADMIN_MENU_TEXT);
        JMenu tortenet = new JMenu(TORTENET_MENU_TEXT);
        JMenu logout = new JMenu(LOGOUT_MENU_TEXT);
        
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
        JPanel panel = new JPanel(new GridLayout(1, 1));

        panel.add(jPanel);
        add(panel, BorderLayout.CENTER);
    }
}
