package zoli.szakdoga.cinema.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import zoli.szakdoga.cinema.db.dao.DaoManager;
import zoli.szakdoga.cinema.db.entity.*;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;
import zoli.szakdoga.cinema.gui.action.*;
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

    private final JPanel panelMusorA = new JPanel();
    private final JPanel panelFilmA = new JPanel();
    private final JPanel panelMoziA = new JPanel();
    private final JTable moziTable = new JTable();
    private final JPanel panelTeremA = new JPanel();
    private final JTable teremTable = new JTable();
    private final JPanel panelFelhasznaloA = new JPanel();
    private final JTable felhasznaloTable = new JTable();

    private final JPanel panelTortenet = new JPanel();
    private final JTable tortenetTable = new JTable();

    private MouseAdapter rightClickAction;
    private ShowStoryAction showStory;
    private AddAction addAction;
    private DelAction delAction;
    //private final JButton addButton = new JButton(FELVITEL_BUT_TEXT);
    //private final JButton delButton = new JButton(FELVITEL_BUT_TEXT);
    private final JButton addMusorButton = new JButton(FELVITEL_MUSOR_TEXT);
    private final JButton addFilmButton = new JButton(FELVITEL_FILM_TEXT);
    private final JButton addMoziButton = new JButton(FELVITEL_MOZI_TEXT);
    private final JButton addTeremButton = new JButton(FELVITEL_TEREM_TEXT);
    private final JButton addFelhaszButton = new JButton(FELVITEL_FELHASZNALO_TEXT);

    private final JButton loginButton = new JButton(LOGIN_BUT_TEXT);
    private final JButton regButton = new JButton(REG_BUT_TEXT);
    private ActionListener loginAction;
    private ActionListener regAction;

    public CinemaFrame() {
        initFrame();
        setActionListeners();
        setButtons();

        setMenu();
        setCenter();

        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    public JTable getMusorTable() {
        return musorTable;
    }

    public JTable getFilmTable() {
        return filmTable;
    }

    public JTable getMoziTable() {
        return moziTable;
    }

    public JTable getTeremTable() {
        return teremTable;
    }

    public JTable getFelhasznaloTable() {
        return felhasznaloTable;
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
        JMenuItem subMusor = new JMenuItem(new AbstractAction(MUSOR_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "6");
                loadMusorPanel();
            }
        });
        admin.add(subMusor);
        JMenuItem subFilm = new JMenuItem(new AbstractAction(FILM_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "7");
                adminFilmPanel();
            }
        });
        admin.add(subFilm);
        JMenuItem subMozi = new JMenuItem(new AbstractAction(MOZI_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "8");
                adminMoziPanel();
            }
        });
        admin.add(subMozi);
        JMenuItem subTerem = new JMenuItem(new AbstractAction(TEREM_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "9");
                adminTeremPanel();
            }
        });
        admin.add(subTerem);
        JMenuItem subFelhasznalo = new JMenuItem(new AbstractAction(FELHASZNALO_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "10");
                felhasznaloListaPanel();
            }
        });
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
        panelCont.setLayout(cl); // felület > mindig új layout-ot pakol ki? (kis pontok a gui-n)

        panelCont.add(panelIndex, "1");
        panelIndex.add(loginButton);
        panelIndex.add(regButton);
        panelCont.add(panelMusor, "2");
        panelCont.add(panelFilm, "3");
        panelCont.add(panelAr, "4");
        panelCont.add(panelKapcsolat, "5");

        panelCont.add(panelMusorA, "6");
        panelMusorA.add(addMusorButton);
        panelCont.add(panelFilmA, "7");
        panelFilmA.add(addFilmButton);
        panelCont.add(panelMoziA, "8");
        panelMoziA.add(addMoziButton);
        panelCont.add(panelTeremA, "9");
        panelTeremA.add(addTeremButton);
        panelCont.add(panelFelhasznaloA, "10");
        panelFelhasznaloA.add(addFelhaszButton);

        //panelCont.add(panelAdmin, "6");
        panelCont.add(panelTortenet, "11");
        cl.show(panelCont, "1");

        add(panelCont, BorderLayout.CENTER);
    }

    public void loadMusorPanel() {
        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Vetites>> sorter = new TableRowSorter<>(model);

        musorTable.setModel(model);
        musorTable.setRowSorter(sorter);
        musorTable.setEnabled(false);
        
        panelMusor.add(MUSOR_MENU_TEXT, new JScrollPane(musorTable));
    }
    
    public void adminMusorPanel() {
        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Vetites>> sorter = new TableRowSorter<>(model);

        musorTable.setModel(model);
        musorTable.setRowSorter(sorter);
        musorTable.setEnabled(true);

        musorTable.addMouseListener(rightClickAction);
        panelMusorA.add(MUSOR_MENU_TEXT, new JScrollPane(musorTable));
    }

    public void loadFilmPanel() {
        GenericTableModel<Film> model = new GenericTableModel(DaoManager.getInstance().getFilmDao(), Film.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Film>> sorter = new TableRowSorter<>(model);

        filmTable.setModel(model);
        filmTable.setRowSorter(sorter);
        filmTable.setEnabled(false);

        panelFilm.add(FILM_MENU_TEXT, new JScrollPane(filmTable));
    }
    
    public void adminFilmPanel() {
        GenericTableModel<Film> model = new GenericTableModel(DaoManager.getInstance().getFilmDao(), Film.FULL_PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Film>> sorter = new TableRowSorter<>(model);

        filmTable.setModel(model);
        filmTable.setRowSorter(sorter);
        filmTable.setEnabled(true);

        filmTable.addMouseListener(rightClickAction);
        panelFilmA.add(FILM_MENU_TEXT, new JScrollPane(filmTable));
    }

    public void adminMoziPanel() {
        GenericTableModel<Mozi> model = new GenericTableModel(DaoManager.getInstance().getMoziDao(), Mozi.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Mozi>> sorter = new TableRowSorter<>(model);

        moziTable.setModel(model);
        moziTable.setRowSorter(sorter);
        moziTable.setEnabled(true);

        moziTable.addMouseListener(rightClickAction);
        panelMoziA.add(MOZI_MENU_TEXT, new JScrollPane(moziTable));
    }

    public void adminTeremPanel() {
        GenericTableModel<Terem> model = new GenericTableModel(DaoManager.getInstance().getTeremDao(), Terem.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Terem>> sorter = new TableRowSorter<>(model);

        teremTable.setModel(model);
        teremTable.setRowSorter(sorter);
        teremTable.setEnabled(true);

        teremTable.addMouseListener(rightClickAction);
        panelTeremA.add(TEREM_MENU_TEXT, new JScrollPane(teremTable));
    }

    public void felhasznaloListaPanel() {
        GenericTableModel<Felhasznalo> model = new GenericTableModel(DaoManager.getInstance().getFelhasznaloDao(), Felhasznalo.PROPERTY_NAMES);
        TableRowSorter<GenericTableModel<Felhasznalo>> sorter = new TableRowSorter<>(model);

        felhasznaloTable.setModel(model);
        felhasznaloTable.setRowSorter(sorter);
        felhasznaloTable.setEnabled(true);

        felhasznaloTable.addMouseListener(rightClickAction);
        panelFelhasznaloA.add(FELHASZNALO_MENU_TEXT, new JScrollPane(felhasznaloTable));
    }

    private void setActionListeners() {
        showStory = new ShowStoryAction(this);
        addAction = new AddAction(this);
        delAction = new DelAction(this);
        rightClickAction = new StoryRightClickAction(showStory, delAction);

        loginAction = new LoginAction();
        regAction = new RegAction(this);
    }

    private void setButtons() {
        loginButton.addActionListener(loginAction);
        regButton.addActionListener(regAction);
        //addButton.addActionListener(addAction);
        //delButton.addActionListener(delAction);
        addMusorButton.addActionListener(addAction);
        addFilmButton.addActionListener(addAction);
        addMoziButton.addActionListener(addAction);
        addTeremButton.addActionListener(addAction);
        addFelhaszButton.addActionListener(addAction);
    }
    /*
    private void setActionButtons(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        panel.add(addButton);
        panel.add(delButton);
        add(panel, BorderLayout.PAGE_END);
    }*/
}
