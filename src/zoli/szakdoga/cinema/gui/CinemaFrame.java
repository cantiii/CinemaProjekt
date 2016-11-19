package zoli.szakdoga.cinema.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import zoli.szakdoga.cinema.db.dao.DaoManager;
import zoli.szakdoga.cinema.db.entity.*;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;
import zoli.szakdoga.cinema.gui.action.*;
import zoli.szakdoga.cinema.gui.model.*;

/**
 *
 * @author Zoli
 */
public class CinemaFrame extends JFrame {

    private final JPanel panelCont = new JPanel();
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
    private final JTable szekTable = new JTable();
    private final JPanel panelFelhasznaloA = new JPanel();
    private final JTable felhasznaloTable = new JTable();
    private final JPanel panelTartalmazA = new JPanel();
    private final JTable tartalmazTable = new JTable();

    private final JPanel panelTortenet = new JPanel();
    private final JTable tortenetTable = new JTable();

    private MouseAdapter rightClickAction;
    private ShowStoryAction showStory;
    private AddAction addAction;
    private DelAction delAction;

    private FoglalasAction foglalasAction;

    private final JButton addMusorButton = new JButton(FELVITEL_MUSOR_TEXT);
    private final JButton addFilmButton = new JButton(FELVITEL_FILM_TEXT);
    private final JButton addMoziButton = new JButton(FELVITEL_MOZI_TEXT);
    private final JButton addTeremButton = new JButton(FELVITEL_TEREM_TEXT);

    private LoginAction logIn;
    private RegAction regIn;

    Integer selectedRow = null;
    private final static Integer[] JOGOK = {1, 2};

    public CinemaFrame() {
        initFrame();

        setStart();
        setButtons();

        pack();
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
    }

    // Regisztráció, vagy Bejelentkezés választó dialog ablak
    private void setStart() {
        Object[] options = {LOGIN_BUT_TEXT, REG_BUT_TEXT};
        int n = JOptionPane.showOptionDialog(panelCont, LOGIN_BUT_TEXT + " vagy " + REG_BUT_TEXT + "?", FRAME_TITLE, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (n == 0) {
            logIn = new LoginAction(this);
        } else {
            regIn = new RegAction(this);
            logIn = new LoginAction(this);
        }
        setMenu();
        setCenter();
        setSouth();
        setActionListeners();
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

    public JTable getSzekTable() {
        return szekTable;
    }

    public JTable getTartalmazTable() {
        return tartalmazTable;
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

    //Létrehozzuk a menü struktúrát, a hozzájuk tartozó műveletekkel
    private void setMenu() {
        JMenuBar menuBar = new JMenuBar();
        //Müsor menü elem, panel megjelenítés, valamit a loadMusorPanel (kifejtve lentebb)
        JMenuItem musor = new JMenuItem(new AbstractAction(MUSOR_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "2");
                setNorth(MUSOR_MENU_TEXT);
                loadMusorPanel();
            }
        });
        JMenuItem film = new JMenuItem(new AbstractAction(FILM_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "3");
                setNorth(FILM_MENU_TEXT);
                loadFilmPanel();
            }
        });
        JMenuItem ar = new JMenuItem(new AbstractAction(AR_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "4");
                setNorth(AR_MENU_TEXT);
                loadArPanel();
            }
        });
        JMenuItem kapcsolat = new JMenuItem(new AbstractAction(KAPCSOLAT_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(panelCont, "5");
                setNorth(KAPCSOLAT_MENU_TEXT);
                loadKapcsolatPanel();
            }
        });

        JMenu admin = null;
        if (logIn.getCurrUser().getJog() == 1) {
            admin = new JMenu(ADMIN_MENU_TEXT);
            setNorth(ADMIN_MENU_TEXT);
            //admin menüben vannak almenüpontok, ezek hozzáadása itt történik
            JMenuItem subMusor = new JMenuItem(new AbstractAction(MUSOR_MENU_TEXT) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelCont, "6");
                    adminMusorPanel();
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
            JMenuItem subTartalmaz = new JMenuItem(new AbstractAction(HOZZARENDELES_TEXT) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelCont, "10");
                    adminTartalmazPanel();
                }
            });
            admin.add(subTartalmaz);
            JMenuItem subFelhasznalo = new JMenuItem(new AbstractAction(FELHASZNALO_MENU_TEXT) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelCont, "11");
                    adminFelhasznaloPanel();
                }
            });
            admin.add(subFelhasznalo);
        }

        //aktuális user rendelési története
        JMenuItem tortenet = null;
        if (logIn.getCurrUser().getJog() == 2) {
            tortenet = new JMenuItem(new AbstractAction(TORTENET_MENU_TEXT) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelCont, "12");
                    setNorth(TORTENET_MENU_TEXT);
                }
            });
        }

        JMenuItem logout = new JMenuItem(new AbstractAction(LOGOUT_MENU_TEXT) {
            @Override
            public void actionPerformed(ActionEvent e) {
                setStart();
            }
        });

        menuBar.add(musor);
        menuBar.add(film);
        menuBar.add(ar);
        menuBar.add(kapcsolat);
        if (logIn.getCurrUser().getJog() == 1) {
            menuBar.add(admin);
        }
        if (logIn.getCurrUser().getJog() == 2) {
            menuBar.add(tortenet);
        }
        menuBar.add(logout);
        setJMenuBar(menuBar);
    }

    private void setCenter() {
        // cardLayout kialaítása
        panelCont.setLayout(cl);

        panelCont.add(panelMusor, "2");
        panelCont.add(panelFilm, "3");
        panelCont.add(panelAr, "4");
        panelCont.add(panelKapcsolat, "5");

        panelCont.add(panelMusorA, "6");
        panelCont.add(panelFilmA, "7");
        panelCont.add(panelMoziA, "8");
        panelCont.add(panelTeremA, "9");
        panelCont.add(panelTartalmazA, "10");
        panelCont.add(panelFelhasznaloA, "11");
        panelCont.add(panelTortenet, "12");

        cl.show(panelCont, "5");

        add(panelCont, BorderLayout.CENTER);
    }

    private void setNorth(String label) {
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel elem = new JLabel(label);

        elem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        northPanel.add(elem);
        northPanel.setBackground(Color.GRAY);
        add(northPanel, BorderLayout.NORTH);
    }

    private void setSouth() {
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel udv = new JLabel("ÜDV:");
        JLabel nev = new JLabel(logIn.getCurrUser().getNev());
        JLabel jogszoveg = new JLabel("JOGOSULTSÁGOD:");
        Integer jogInt = logIn.getCurrUser().getJog();
        String jogosultsag = null;
        if (jogInt == 1) {
            jogosultsag = "adminisztrátor";
        } else if (jogInt == 2) {
            jogosultsag = "felhasználó";
        }
        JLabel jog = new JLabel(jogosultsag);

        nev.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        jog.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));

        southPanel.add(udv);
        southPanel.add(nev);
        southPanel.add(jogszoveg);
        southPanel.add(jog);
        southPanel.setBackground(Color.GRAY);
        add(southPanel, BorderLayout.SOUTH);
    }

    //a Müsor/Vetítés adatbázis táblát itt jelenítjük meg a felületre
    public void loadMusorPanel() {
        panelMusor.removeAll();
        // táblamodel létrehozása és meghatározása
        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        musorTable.setModel(model);
        musorTable.setEnabled(false);

        // törlésre kiürül, akkor még ott marad a sorter hiba
        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Vetites>> sorter = new TableRowSorter<>(model);
            //régi dátum kiszűrés, hogy a felhasználót ne zavarják, amár amúgy sem aktuális vetítések
            RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
                public boolean include(RowFilter.Entry entry) {
                    Date today = new Date();
                    String myDate = (String) entry.getValue(2);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                    Date date = null;
                    try {
                        date = format.parse(myDate);
                    } catch (ParseException ex) {
                    }
                    // ha régebbi, mint a mai nap nem kerül ki a felületre
                    return date.after(today);
                }
            };
            //fenti szűrő beállítása
            sorter.setRowFilter(filter);
            musorTable.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            int datumOszlop = 2;
            sortKeys.add(new RowSorter.SortKey(datumOszlop, SortOrder.DESCENDING));
            sorter.setSortKeys(sortKeys);
        }

        musorTable.addMouseListener(rightClickAction);
        panelMusor.add(MUSOR_MENU_TEXT, new JScrollPane(musorTable));
    }

    //ez az admin menübe való Müsor/Vetítés tábla
    public void adminMusorPanel() {
        panelMusorA.removeAll();
        panelMusorA.add(addMusorButton);

        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        musorTable.setModel(model);
        musorTable.getColumnModel().getColumn(2).setCellEditor(new MyDateCell()); // jó dátum és dátum forma

        //A filmet és a termet combobox-ból választhatjuk ki
        setComboColumn(musorTable, 0, DaoManager.getInstance().getFilmDao().findAll().toArray());
        setComboColumn(musorTable, 1, DaoManager.getInstance().getTeremDao().findAll().toArray());

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Vetites>> sorter = new TableRowSorter<>(model);
            musorTable.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            int datumOszlop = 2;
            sortKeys.add(new RowSorter.SortKey(datumOszlop, SortOrder.DESCENDING));
            sorter.setSortKeys(sortKeys);
        }

        musorTable.addMouseListener(rightClickAction);
        panelMusorA.add(MUSOR_MENU_TEXT, new JScrollPane(musorTable));
    }

    public void loadFilmPanel() {
        panelFilm.removeAll();

        GenericTableModel<Film> model = new GenericTableModel(DaoManager.getInstance().getFilmDao(), Film.PROPERTY_NAMES);
        filmTable.setModel(model);
        filmTable.setEnabled(false);

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Film>> sorter = new TableRowSorter<>(model);
            filmTable.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            int filmCimOszlop = 0;
            sortKeys.add(new RowSorter.SortKey(filmCimOszlop, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
        }

        if (logIn.getCurrUser().getJog() == 2) {
            filmTable.addMouseListener(rightClickAction);
        }
        panelFilm.add(FILM_MENU_TEXT, new JScrollPane(filmTable));
    }

    public void adminFilmPanel() {
        panelFilmA.removeAll();
        panelFilmA.add(addFilmButton);

        GenericTableModel<Film> model = new GenericTableModel(DaoManager.getInstance().getFilmDao(), Film.FULL_PROPERTY_NAMES);
        filmTable.setModel(model);
        filmTable.setEnabled(true);

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Film>> sorter = new TableRowSorter<>(model);
            filmTable.setRowSorter(sorter);

            List<RowSorter.SortKey> sortKeys = new ArrayList<>();
            int filmCimOszlop = 0;
            sortKeys.add(new RowSorter.SortKey(filmCimOszlop, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);
        }

        filmTable.addMouseListener(rightClickAction);
        panelFilmA.add(FILM_MENU_TEXT, new JScrollPane(filmTable));
    }

    public void adminMoziPanel() {
        panelMoziA.removeAll();
        panelMoziA.add(addMoziButton);

        GenericTableModel<Mozi> model = new GenericTableModel(DaoManager.getInstance().getMoziDao(), Mozi.PROPERTY_NAMES);
        moziTable.setModel(model);
        moziTable.setEnabled(true);

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Mozi>> sorter = new TableRowSorter<>(model);
            moziTable.setRowSorter(sorter);
        }

        moziTable.addMouseListener(rightClickAction);
        panelMoziA.add(MOZI_MENU_TEXT, new JScrollPane(moziTable));
    }

    public void adminTeremPanel() {
        panelTeremA.removeAll();
        panelTeremA.add(addTeremButton);

        GenericTableModel<Terem> model = new GenericTableModel(DaoManager.getInstance().getTeremDao(), Terem.PROPERTY_NAMES);
        teremTable.setModel(model);
        teremTable.setEnabled(true);

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Terem>> sorter = new TableRowSorter<>(model);
            teremTable.setRowSorter(sorter);
        }

        teremTable.addMouseListener(rightClickAction);
        panelTeremA.add(TEREM_MENU_TEXT, new JScrollPane(teremTable));
    }

    public void adminTartalmazPanel() {
        panelTartalmazA.removeAll();

        GenericTableModel<Tartalmaz> model = new GenericTableModel(DaoManager.getInstance().getTartalmazDao(), Tartalmaz.PROPERTY_NAMES);
        tartalmazTable.setModel(model);

        setComboColumn(tartalmazTable, 0, DaoManager.getInstance().getMoziDao().findAll().toArray());
        //mozi függvényében kellene felhozni a termeket
        setComboColumn(tartalmazTable, 1, DaoManager.getInstance().getTeremDao().findAll().toArray());

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Tartalmaz>> sorter = new TableRowSorter<>(model);
            tartalmazTable.setRowSorter(sorter);
        }

        tartalmazTable.addMouseListener(rightClickAction);
        panelTartalmazA.add(HOZZARENDELES_TEXT, new JScrollPane(tartalmazTable));
    }

    public void adminFelhasznaloPanel() {
        panelFelhasznaloA.removeAll();

        GenericTableModel<Felhasznalo> model = new GenericTableModel(DaoManager.getInstance().getFelhasznaloDao(), Felhasznalo.PROPERTY_NAMES);
        felhasznaloTable.setModel(model);
        felhasznaloTable.setEnabled(true);

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Felhasznalo>> sorter = new TableRowSorter<>(model);
            felhasznaloTable.setRowSorter(sorter);
        }

        //a felhasználói jogok combobox-szal módosíthatok
        setComboColumn(felhasznaloTable, 1, JOGOK);

        felhasznaloTable.addMouseListener(rightClickAction);
        panelFelhasznaloA.add(FELHASZNALO_MENU_TEXT, new JScrollPane(felhasznaloTable));
    }

    public void loadArPanel() {
        panelAr.removeAll();

        JEditorPane editorPane = new JEditorPane();

        editorPane.setContentType("text/html");
        File file = new File("src/html/arInfo.html");
        try {
            editorPane.setPage(file.toURI().toURL());
        } catch (Exception ex) {
        }
        editorPane.setEditable(false);

        panelAr.add(editorPane);
    }

    public void loadKapcsolatPanel() {
        panelKapcsolat.removeAll();

        JEditorPane editorPane = new JEditorPane();

        editorPane.setContentType("text/html");
        File file = new File("src/html/kapcsolatInfo.html");
        try {
            editorPane.setPage(file.toURI().toURL());
        } catch (Exception ex) {
        }
        editorPane.setEditable(false);

        panelKapcsolat.add(editorPane);
    }

    //Combobox-rt felelös függvény
    private void setComboColumn(JTable table, int index, Object[] values) {
        JComboBox comboBox = new JComboBox(values);
        TableColumn column = table.getColumnModel().getColumn(index);
        column.setCellEditor(new DefaultCellEditor(comboBox));
        column.setCellRenderer(new DefaultTableCellRenderer());
    }

    //actionListener-ek aktiválása
    private void setActionListeners() {
        showStory = new ShowStoryAction(this);
        addAction = new AddAction(this);
        delAction = new DelAction(this);
        foglalasAction = new FoglalasAction(this);
        rightClickAction = new StoryRightClickAction(this, showStory, delAction, logIn, foglalasAction);
    }

    // gombok aktíválása
    private void setButtons() {
        addMusorButton.addActionListener(addAction);
        addFilmButton.addActionListener(addAction);
        addMoziButton.addActionListener(addAction);
        addTeremButton.addActionListener(addAction);
    }

}
