package zoli.szakdoga.cinema.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import zoli.szakdoga.cinema.db.dao.DaoManager;
import zoli.szakdoga.cinema.db.entity.*;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;
import zoli.szakdoga.cinema.gui.action.ShowStoryAction;
import zoli.szakdoga.cinema.gui.action.StoryRightClickAction;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

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

    private final JPanel panelMusor = new JPanel();
    private final JTable musorTable = new JTable();
    private final JPanel panelFilm = new JPanel();
    private final JTable filmTable = new JTable();
    //private final JPanel panelAdmin = new JPanel();
    //private final JTable adminTable = new JTable();
    private final JPanel panelTortenet = new JPanel();
    private final JTable tortenetTable = new JTable();
    
    private MouseAdapter rightClickAction;
    private ShowStoryAction showStory;

    public CinemaFrame() {
        initFrame();
        linkActionListeners();

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
        JMenuItem musor = new JMenuItem(new AbstractAction(MUSOR_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "2");
                loadMusorPanel();
            }
        });

        JMenuItem film = new JMenuItem(new AbstractAction(FILM_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "3");
                loadFilmPanel();
            }
        });
        JMenuItem ar = new JMenuItem(new AbstractAction(AR_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "4");
            }
        });
        JMenuItem kapcsolat = new JMenuItem(new AbstractAction(KAPCSOLAT_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "5");
            }
        });
        JMenu admin = new JMenu(ADMIN_MENU_TEXT);
        JMenuItem subMusor = new JMenuItem(MUSOR_MENU_TEXT);
        admin.add(subMusor);
        JMenuItem subFilm = new JMenuItem(FILM_MENU_TEXT);
        admin.add(subFilm);
        JMenuItem subMozi = new JMenuItem(MOZI_MENU_TEXT);
        admin.add(subMozi);
        JMenuItem subTerem = new JMenuItem(TEREM_MENU_TEXT);
        admin.add(subTerem);
        JMenuItem subFelhasznalo = new JMenuItem(FELHASZNALO_MENU_TEXT);
        admin.add(subFelhasznalo);
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
        panelCont.add(panelMusor, "2");
        panelCont.add(panelFilm, "3");
        panelCont.add(panelAr, "4");
        panelCont.add(panelKapcsolat, "5");
        //panelCont.add(panelAdmin, "6");
        panelCont.add(panelTortenet, "7");
        cl.show(panelCont, "1");

        add(panelCont, BorderLayout.CENTER);
    }

    public void loadMusorPanel() {
        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Vetites>> sorter = new TableRowSorter<>(model);

        musorTable.setModel(model);
        musorTable.setRowSorter(sorter);

        panelMusor.add(MUSOR_MENU_TEXT, new JScrollPane(musorTable));
    }

    public void loadFilmPanel() {
        GenericTableModel<Film> model = new GenericTableModel(DaoManager.getInstance().getFilmDao(), Film.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Film>> sorter = new TableRowSorter<>(model);

        filmTable.setModel(model);
        filmTable.setRowSorter(sorter);

        filmTable.addMouseListener(rightClickAction);
        panelFilm.add(FILM_MENU_TEXT, new JScrollPane(filmTable));
    }
    
    private void linkActionListeners() {
        showStory = new ShowStoryAction(this);
        rightClickAction = new StoryRightClickAction(showStory);
    }
}
