package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import zoli.szakdoga.cinema.db.dao.*;
import zoli.szakdoga.cinema.db.entity.*;
import zoli.szakdoga.cinema.gui.*;
import static zoli.szakdoga.cinema.gui.GuiConstants.*;
import zoli.szakdoga.cinema.gui.model.GenericTableModel;

/**
 *
 * @author pappz
 * A különböző felvitelekért felelős osztály
 */
public class AddAction implements ActionListener {

    private CinemaFrame parent;
    private DefaultDao dao;

    public AddAction(CinemaFrame parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // egy switch segítségével döntjük el, hogy milyen típusú felvitel lesz
        switch (e.getActionCommand()) {
            case FELVITEL_MUSOR_TEXT:
                //bekérünk egy időpontot
                String date = readDate(FELVITEL_DATUM_TEXT);
                // a dátum függvényében listázzuk a termeket
                Terem valaszTerem = readTerem(date);
                if (valaszTerem != null) {
                    //ha választottunk termet, bekérjük a db-ben lévő filmeket
                    Film valaszFilm = readFilm();
                    if (valaszFilm != null) {
                        //ha minden elemet rendben találtunk:
                        //létrehozunk egy vetites objektumot és elmentjük az adatokat
                        Vetites vetites = new Vetites();
                        vetites.setFilmId(valaszFilm);
                        vetites.setTeremId(valaszTerem);
                        vetites.setMikor(date);

                        GenericTableModel vetitesModel = (GenericTableModel) parent.getMusorTable().getModel();
                        vetitesModel.addEntity(vetites);
                    }
                }
                break;
            case FELVITEL_FILM_TEXT:
                Film film = new Film();
                film.setCim(readUniqueString(FELVITEL_FILM_TEXT));
                film.setRendezo(readString(FELVITEL_FILMRENDEZO_TEXT));
                film.setSzinesz(readString(FELVITEL_FILMSZINESZ_TEXT));
                film.setLeiras(readString(FELVITEL_FILMLEIRAS_TEXT));
                film.setHossz(readNumber(FELVITEL_FILMHOSSZ_TEXT));
                film.setKorhatar(readNumber(FELVITEL_FILMKOR_TEXT));

                GenericTableModel filmModel = (GenericTableModel) parent.getFilmTable().getModel();
                filmModel.addEntity(film);
                break;
            case FELVITEL_MOZI_TEXT:
                Mozi mozi = new Mozi();
                // mozi létrehozásnál csak arra kell figyelni, hogy ne legyen név egyezés
                mozi.setNev(readUniqueString(FELVITEL_MOZI_TEXT));

                GenericTableModel moziModel = (GenericTableModel) parent.getMoziTable().getModel();
                moziModel.addEntity(mozi);
                break;
            case FELVITEL_TEREM_TEXT:
                Terem terem = new Terem();
                terem.setNev(readUniqueString(FELVITEL_TEREM_TEXT));
                terem.setFerohely(readNumber(FELVITEL_TEREMHELY_TEXT));

                GenericTableModel teremModel = (GenericTableModel) parent.getTeremTable().getModel();
                teremModel.addEntity(terem);
                break;
            case MOZI_TEREM_TEXT:
                Mozi valaszMozi = readMozi();
                if (valaszMozi != null) {
                    valaszTerem = readTerem();
                    if (valaszTerem != null) {
                        Tartalmaz tartalmaz = new Tartalmaz();
                        tartalmaz.setMoziId(valaszMozi);
                        tartalmaz.setTeremId(valaszTerem);

                        GenericTableModel tartalmazModel = (GenericTableModel) parent.getTartalmazTable().getModel();
                        tartalmazModel.addEntity(tartalmaz);
                    }
                }
                break;
        }
    }
    /**
     * Eggy egyszerű string beolvasás, csak annyi a lényeg, hogy ne legyen üres
     * valamint, hogy a hossza rendben legyen
     * ha ezek megvannak továbbítjuk
     */
    private String readString(String label) {
        String name = null;
        while (name == null) {
            name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                if (name.length() <= 3 || name.length() > 20) {
                    JOptionPane.showMessageDialog(parent, GuiConstants.LENGHT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    name = null;
                } else {
                    return name;
                }
            }
        }
        return name;
    }
    /**
     * 
     * @param label -
     * @return 
     */
    private String readUniqueString(String label) {
        String name = null;
        switch (label) {
            case FELVITEL_MOZI_TEXT:
                dao = new DefaultDao(Mozi.class);
                while (name == null) {
                    name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                    if (name != null && !name.trim().equals("")) {
                        if (name.length() <= 3 || name.length() > 20) {
                            JOptionPane.showMessageDialog(parent, GuiConstants.LENGHT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                            name = null;
                        } else if (dao.findMozi(name)) {
                            JOptionPane.showMessageDialog(parent, GuiConstants.UNIQUE_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                            name = null;
                        } else {
                            return name;
                        }
                    }
                }
                break;
            case FELVITEL_TEREM_TEXT:
                dao = new DefaultDao(Terem.class);
                while (name == null) {
                    name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                    if (name != null && !name.trim().equals("")) {
                        if (name.length() <= 3 || name.length() > 20) {
                            JOptionPane.showMessageDialog(parent, GuiConstants.LENGHT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                            name = null;
                        } else if (dao.findTerem(name)) {
                            JOptionPane.showMessageDialog(parent, GuiConstants.UNIQUE_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                            name = null;
                        } else {
                            return name;
                        }
                    }
                }
                break;
            case FELVITEL_FILM_TEXT:
                dao = new DefaultDao(Film.class);
                while (name == null) {
                    name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
                    if (name != null && !name.trim().equals("")) {
                        if (name.length() <= 3 || name.length() > 20) {
                            JOptionPane.showMessageDialog(parent, GuiConstants.LENGHT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                            name = null;
                        } else if (dao.findFilm(name)) {
                            JOptionPane.showMessageDialog(parent, GuiConstants.UNIQUE_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                            name = null;
                        } else {
                            return name;
                        }
                    }
                }
                break;
        }
        return name;
    }

    // szám típus ellenőrzés
    private Integer readNumber(String label) {
        Integer number = null;
        do {
            String str = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            try {
                //megpróbáljuk a stringet int-é parszolni
                number = Integer.parseInt(str);
            } catch (Exception ex) {
                //ha ez nem volt sikeres értesítjük a usert
                JOptionPane.showMessageDialog(parent, GuiConstants.INVALID_NUMBER, GuiConstants.INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            }
            //addig ismételjük míg sikeres eredményt nem kapunk
        } while (number == null);
        return number;
    }

    //Dátum beolvasáshoz tartozó függvény
    private String readDate(String label) {
        String name = null;
        while (name == null) {
            name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                if (!dateFormat(name)) {
                    //ha hamis eredményt kapunk(vagyis hiba volt) újra kell próbálni
                    name = null;
                } else {
                    return name;
                }
            } else {
                name = null; // nem lehet default value
            }
        }
        return name;
    }

    //Itt döntjük el, hogy jó-e a megadott dátum
    public boolean dateFormat(String dateIn) {
        //ha az null volt, akkor egyértelműen hiba
        if (dateIn == null) {
            JOptionPane.showMessageDialog(parent, GuiConstants.FORMAT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        /**
         * ha nem üres a mező akkor jöhetnek az ellenőrzések
         * meghatározzuk az intervallumut, amiben a vetítás szerepelhet
         * múltbeli, illetve 60napnál előrébb mutató nem lehet
         */
        SimpleDateFormat sample = new SimpleDateFormat("yyyy/MM/dd");
        sample.setLenient(false);

        Date nextDay = new Date();
        Date nextCDay = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(nextDay);
        c.add(Calendar.DATE, 1);
        nextDay = c.getTime();

        c.add(Calendar.DATE, 60);
        nextCDay = c.getTime();

        try {
            //ha nem valid dátum formátum, akkor ParseException
            //ha rendben van akkor kilép és felülírja a régi dátumot
            Date date = sample.parse(dateIn);
            if (date.after(nextCDay) || date.before(nextDay)) {
                JOptionPane.showMessageDialog(parent, GuiConstants.DATE_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            // ha nem jó dátum formátum hibaüzenetet kap a user
            JOptionPane.showMessageDialog(parent, GuiConstants.FORMAT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     *  Mozi entitások beolvasása és megjelenításe
     * @return - a legördülő listából kiválasztott mozi elem
     */
    private Mozi readMozi() {
        Object[] mozik = DaoManager.getInstance().getMoziDao().findAll().toArray();
        Mozi mozi = (Mozi) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, mozik, mozik[0]);
        return mozi;
    }

    /**
     * Csak azokat a termeket kellene felhozni, amik még nincsenek mozihoz adva
     * @return - kiválasztott Terem entitás
     */
    private Terem readTerem() {
        dao = new DefaultDao(Terem.class);
        // lekérjük az összes termet
        List<Terem> osszTerem = dao.findAll();
        List<Terem> segedTerem = new ArrayList<>(osszTerem);

        dao = new DefaultDao(Tartalmaz.class);
        //lekérjük a már Mozihoz rendelt termeket, itt még Tartalmaz entitásként
        List<Tartalmaz> idInTartalmaz = dao.findAll();

        //Ezeket átalakitjuk Terem listávvá
        List<Terem> tartalmazottTerem = new ArrayList<>();
        for (int i = 0; i < idInTartalmaz.size(); i++) {
            tartalmazottTerem.add(idInTartalmaz.get(i).getTeremId());
        }
        
        //ha már nincs több terem, amit használhatnánk hibaüzenet
        if (tartalmazottTerem.size() == osszTerem.size()) {
            JOptionPane.showMessageDialog(parent, GuiConstants.NOMORE_ROOM_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        //A segegTerem listából kitöröljük a már használt termeket
        Collection<Terem> torlendoTermek = new ArrayList(tartalmazottTerem);
        segedTerem.removeAll(torlendoTermek);

        //így csak azok maradnak amiket fel tudunk használni
        //de ezeket Object-é kell alakítani
        Object[] kellMegTerem = segedTerem.toArray();

        Terem terem = (Terem) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, kellMegTerem, kellMegTerem[0]);
        return terem;
    }

    /**
     * Terem lista az időpont függvényében
     * vetítés >> dátum a prio, mert az nap 1 terem csak 1x használható
     * @param date - A vetítés dátuma, ez szűri le a termeket
     * @return - kiválasztott terem entitás
     */
    private Terem readTerem(String date) {
        dao = new DefaultDao(Terem.class);
        // lekérjük az összes termet
        List<Terem> osszTerem = dao.findAll();
        List<Terem> segedTerem = new ArrayList<>(osszTerem);

        dao = new DefaultDao(Vetites.class);
        //lekérjük azokat a termeket, amikben az adotnap már van vetítés, itt még Vetites entitásként
        List<Vetites> idInVetites = dao.findUsedTerem(date);

        //Ezeket átalakitjuk Terem listávvá
        List<Terem> hasznaltTerem = new ArrayList<>();
        for (int i = 0; i < idInVetites.size(); i++) {
            hasznaltTerem.add(idInVetites.get(i).getTeremId());
        }

        //ha már nincs több terem, amit használhatnánk hibaüzenet
        if (hasznaltTerem.size() == osszTerem.size()) {
            JOptionPane.showMessageDialog(parent, GuiConstants.NOMORE_ROOM_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            return null;
        }

        //A segegTerem listából kitöröljük a már használt termeket
        Collection<Terem> torlendoTermek = new ArrayList(hasznaltTerem);
        segedTerem.removeAll(torlendoTermek);

        //így csak azok maradnak amiket fel tudunk használni
        //de ezeket Object-é kell alakítani
        Object[] kellMegTerem = segedTerem.toArray();

        Terem terem = (Terem) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, kellMegTerem, kellMegTerem[0]);
        return terem;
    }

    /**
     *  Film entitások beolvasása és megjelenításe
     * @return - a legördülő listából kiválasztott film elem
     */
    private Film readFilm() {
        Object[] filmek = DaoManager.getInstance().getFilmDao().findAll().toArray();
        Film film = (Film) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, filmek, filmek[0]);
        return film;
    }
}
