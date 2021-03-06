package zoli.szakdoga.cinema.gui;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import zoli.szakdoga.cinema.gui.checker.MyDateCell;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    private final JPanel panelWelcome = new JPanel();
    private final JPanel panelMusorA = new JPanel();
    private final JTable musorATable = new JTable();
    private final JPanel panelFilmA = new JPanel();
    private final JTable filmATable = new JTable();
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

    private final JTextField filterText = new JTextField();
    private final JButton keresoButton = new JButton(KERES_BUT_TEXT);
    private final JButton pdfButton = new JButton(PDF_BUT_TEXT);

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
    private final static Integer[] BOJOGOK = {0, 1, 2};

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
        loadWelcomePanel();
    }

    public JTable getMusorTable() {
        return musorTable;
    }

    public JTable getFilmTable() {
        return filmTable;
    }

    public JTable getMusorATable() {
        return musorATable;
    }

    public JTable getFilmATable() {
        return filmATable;
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
        if (logIn.getCurrUser().getJog() == 0 || logIn.getCurrUser().getJog() == 1) {
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
        if (logIn.getCurrUser().getJog() == 0 || logIn.getCurrUser().getJog() == 2) {
            tortenet = new JMenuItem(new AbstractAction(TORTENET_MENU_TEXT) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cl.show(panelCont, "12");
                    setNorth(TORTENET_MENU_TEXT);
                    loadTortenetPanel();
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
        if (logIn.getCurrUser().getJog() == 0 || logIn.getCurrUser().getJog() == 1) {
            menuBar.add(admin);
        }
        if (logIn.getCurrUser().getJog() == 0 || logIn.getCurrUser().getJog() == 2) {
            menuBar.add(tortenet);
        }
        menuBar.add(logout);
        setJMenuBar(menuBar);
    }

    private void setCenter() {
        // cardLayout kialaítása
        panelCont.setLayout(cl);

        panelCont.add(panelWelcome, "1");
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

        cl.show(panelCont, "1");

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
        switch (jogInt) {
            case 0:
                jogosultsag = "superuser";
                break;
            case 1:
                jogosultsag = "adminisztrátor";
                break;
            case 2:
                jogosultsag = "felhasználó";
                break;
            default:
                break;
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

    public void loadWelcomePanel() {
        panelWelcome.removeAll();

        JEditorPane editorPane = new JEditorPane();

        editorPane.setContentType("text/html");
        File file = new File("src/html/welcome.html");
        try {
            editorPane.setPage(file.toURI().toURL());
        } catch (Exception ex) {
        }
        editorPane.setEditable(false);

        panelWelcome.add(editorPane);
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
            sortKeys.add(new RowSorter.SortKey(datumOszlop, SortOrder.ASCENDING));
            sorter.setSortKeys(sortKeys);

            filterText.setText(MUSOR_MENU_TEXT);
            keresoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String text = filterText.getText();
                    if (text.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    }
                }
            });

            pdfButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int count = musorTable.getRowCount();
                        Document dok = new Document();
                        PdfWriter.getInstance(dok, new FileOutputStream("musor.pdf"));
                        dok.open();
                        PdfPTable tab = new PdfPTable(3);
                        tab.addCell("FILM");
                        tab.addCell("TEREM");
                        tab.addCell("DÁTUM");
                        for (int i = 0; i < count; i++) {
                            Object obj1 = musorTable.getModel().getValueAt(i, 0);
                            Object obj2 = musorTable.getModel().getValueAt(i, 1);
                            Object obj3 = musorTable.getModel().getValueAt(i, 2);

                            String value1 = obj1.toString();
                            String value2 = obj2.toString();
                            String value3 = obj3.toString();
                            tab.addCell(value1);
                            tab.addCell(value2);
                            tab.addCell(value3);
                        }
                        dok.add(tab);
                        dok.close();
                    } catch (FileNotFoundException | DocumentException ex) {
                    }
                }
            });

            Box keresoPanel = new Box(BoxLayout.Y_AXIS);
            keresoPanel.add(filterText);
            keresoPanel.add(keresoButton);
            keresoPanel.add(pdfButton);
            panelMusor.add(keresoPanel, BorderLayout.WEST);
        }

        musorTable.addMouseListener(rightClickAction);
        panelMusor.add(MUSOR_MENU_TEXT, new JScrollPane(musorTable));
    }

    //ez az admin menübe való Müsor/Vetítés tábla
    public void adminMusorPanel() {
        panelMusorA.removeAll();
        panelMusorA.add(addMusorButton);

        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        musorATable.setModel(model);
        musorATable.getColumnModel().getColumn(2).setCellEditor(new MyDateCell()); // jó dátum és dátum forma

        //A filmet és a termet combobox-ból választhatjuk ki
        setComboColumn(musorATable, 0, DaoManager.getInstance().getFilmDao().findAll().toArray());
        setComboColumn(musorATable, 1, DaoManager.getInstance().getTeremDao().findAll().toArray());

        musorATable.addMouseListener(rightClickAction);
        panelMusorA.add(MUSOR_MENU_TEXT, new JScrollPane(musorATable));
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

            filterText.setText(FILM_MENU_TEXT);
            keresoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String keresText = filterText.getText();
                    if (keresText.length() == 0) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter(keresText));
                    }
                }
            });

            pdfButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int count = filmTable.getRowCount();
                        Document dok = new Document();
                        PdfWriter.getInstance(dok, new FileOutputStream("filmek.pdf"));
                        dok.open();
                        PdfPTable tab = new PdfPTable(6);
                        tab.addCell("CIM");
                        tab.addCell("RENDEZO");
                        tab.addCell("SZINESZ");
                        tab.addCell("HOSSZ");
                        tab.addCell("KORHATAR");
                        tab.addCell("LEIRAS");
                        for (int i = 0; i < count; i++) {
                            Object obj1 = filmTable.getModel().getValueAt(i, 0);
                            Object obj2 = filmTable.getModel().getValueAt(i, 1);
                            Object obj3 = filmTable.getModel().getValueAt(i, 2);
                            Object obj4 = filmTable.getModel().getValueAt(i, 3);
                            Object obj5 = filmTable.getModel().getValueAt(i, 4);
                            Object obj6 = filmTable.getModel().getValueAt(i, 5);

                            String value1 = obj1.toString();
                            String value2 = obj2.toString();
                            String value3 = obj3.toString();
                            String value4 = obj4.toString();
                            String value5 = obj5.toString();
                            String value6 = obj6.toString();
                            tab.addCell(value1);
                            tab.addCell(value2);
                            tab.addCell(value3);
                            tab.addCell(value4);
                            tab.addCell(value5);
                            tab.addCell(value6);
                        }
                        dok.add(tab);
                        dok.close();
                    } catch (FileNotFoundException | DocumentException ex) {
                    }
                }
            });

            Box keresoPanel = new Box(BoxLayout.Y_AXIS);
            keresoPanel.add(filterText);
            keresoPanel.add(keresoButton);
            keresoPanel.add(pdfButton);
            panelFilm.add(keresoPanel, BorderLayout.WEST);
        }

        if (logIn.getCurrUser().getJog() == 0 || logIn.getCurrUser().getJog() == 2) {
            filmTable.addMouseListener(rightClickAction);
        }
        panelFilm.add(FILM_MENU_TEXT, new JScrollPane(filmTable));
    }

    public void adminFilmPanel() {
        panelFilmA.removeAll();
        panelFilmA.add(addFilmButton);

        GenericTableModel<Film> model = new GenericTableModel(DaoManager.getInstance().getFilmDao(), Film.FULL_PROPERTY_NAMES);
        filmATable.setModel(model);
        filmATable.setEnabled(true);

        filmATable.addMouseListener(rightClickAction);
        panelFilmA.add(FILM_MENU_TEXT, new JScrollPane(filmATable));
    }

    public void adminMoziPanel() {
        panelMoziA.removeAll();
        panelMoziA.add(addMoziButton);

        GenericTableModel<Mozi> model = new GenericTableModel(DaoManager.getInstance().getMoziDao(), Mozi.PROPERTY_NAMES);
        moziTable.setModel(model);
        moziTable.setEnabled(true);

        moziTable.addMouseListener(rightClickAction);
        panelMoziA.add(MOZI_MENU_TEXT, new JScrollPane(moziTable));
    }

    public void adminTeremPanel() {
        panelTeremA.removeAll();
        panelTeremA.add(addTeremButton);

        GenericTableModel<Terem> model = new GenericTableModel(DaoManager.getInstance().getTeremDao(), Terem.PROPERTY_NAMES);
        teremTable.setModel(model);
        teremTable.setEnabled(true);

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

        panelTartalmazA.add(HOZZARENDELES_TEXT, new JScrollPane(tartalmazTable));
    }

    public void adminFelhasznaloPanel() {
        panelFelhasznaloA.removeAll();

        GenericTableModel<Felhasznalo> model = new GenericTableModel(DaoManager.getInstance().getFelhasznaloDao(), Felhasznalo.PROPERTY_NAMES);
        felhasznaloTable.setModel(model);
        felhasznaloTable.setEnabled(true);

        //a felhasználói jogok combobox-szal módosíthatok
        if (logIn.getCurrUser().getJog() == 0) {
            setComboColumn(felhasznaloTable, 1, BOJOGOK);
        } else if (logIn.getCurrUser().getJog() == 1) {
            setComboColumn(felhasznaloTable, 1, JOGOK);
        }

        felhasznaloTable.addMouseListener(rightClickAction);
        panelFelhasznaloA.add(FELHASZNALO_MENU_TEXT, new JScrollPane(felhasznaloTable));
    }

    public void loadTortenetPanel() {
        panelTortenet.removeAll();

        GenericTableModel<Vetites> model = new GenericTableModel(DaoManager.getInstance().getVetitesDao(), Vetites.PROPERTY_NAMES);
        tortenetTable.setModel(model);

        if (model.getRowCount() != 0) {
            TableRowSorter<GenericTableModel<Vetites>> sorter = new TableRowSorter<>(model);
            tortenetTable.setRowSorter(sorter);
        }
        panelTortenet.add(MUSOR_MENU_TEXT, new JScrollPane(tortenetTable));
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
        delAction = new DelAction(this, logIn);
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
