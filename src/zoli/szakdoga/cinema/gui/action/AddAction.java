package zoli.szakdoga.cinema.gui.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 */
public class AddAction implements ActionListener {

    private CinemaFrame parent;
    private DefaultDao dao;

    public AddAction(CinemaFrame parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case FELVITEL_MUSOR_TEXT:
                Film valaszFilm = readFilm();
                if (valaszFilm != null) {
                    Terem valaszTerem = readTerem();
                    if (valaszTerem != null) {
                        Vetites vetites = new Vetites();
                        vetites.setFilmId(valaszFilm);
                        vetites.setTeremId(valaszTerem);
                        vetites.setMikor(readDate(FELVITEL_DATUM_TEXT));

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
                    Terem valaszTerem = readTerem(valaszMozi);
                    if (valaszTerem != null) {
                        Tartalmaz tartalmaz = new Tartalmaz();
                        tartalmaz.setMoziId(valaszMozi);
                        tartalmaz.setTeremId(valaszTerem);

                        GenericTableModel tartalmazModel = (GenericTableModel) parent.getTartalmazTable().getModel();
                        tartalmazModel.addEntity(tartalmaz);
                    } else {
                        JOptionPane.showMessageDialog(parent, " másik mozi PLS !", GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
        }
    }

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

    private Integer readNumber(String label) {
        Integer number = null;
        do {
            String str = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            try {
                number = Integer.parseInt(str);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(parent, GuiConstants.INVALID_NUMBER, GuiConstants.INPUT_ERROR, JOptionPane.ERROR_MESSAGE);
            }
        } while (number == null);
        return number;
    }

    private String readDate(String label) {
        String name = null;
        while (name == null) {
            name = JOptionPane.showInputDialog(parent, label, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.INFORMATION_MESSAGE);
            if (name != null && !name.trim().equals("")) {
                if (!dateFormat(name)) {
                    name = null;
                } else {
                    return name;
                }
            } else {
                name = "KÉSŐBB!";
            }
        }
        return name;
    }

    public boolean dateFormat(String dateIn) {
        if (dateIn == null) {
            JOptionPane.showMessageDialog(parent, GuiConstants.FORMAT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        SimpleDateFormat sample = new SimpleDateFormat("yyyy/MM/dd");
        sample.setLenient(false);

        Date nextDay = new Date();
        Date nextCDay = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(nextDay);
        c.add(Calendar.DATE, 1);
        nextDay = c.getTime();

        c.add(Calendar.DATE, 100);
        nextCDay = c.getTime();

        try {
            //ha nem valid dátum formátum, akkor ParseException
            Date date = sample.parse(dateIn);
            if (date.after(nextCDay) || date.before(nextDay)) {
                JOptionPane.showMessageDialog(parent, GuiConstants.DATE_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(parent, GuiConstants.FORMAT_ERROR, GuiConstants.FAIL, JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private Mozi readMozi() {
        Object[] mozik = DaoManager.getInstance().getMoziDao().findAll().toArray();
        Mozi mozi = (Mozi) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, mozik, mozik[0]);
        return mozi;
    }

    private Terem readTerem() {
        Object[] termek = DaoManager.getInstance().getTeremDao().findAll().toArray();
        Terem terem = (Terem) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, termek, termek[0]);
        return terem;
    }

    //csak azokat a termeket kellene felhozni, amik még nincsenek mozihoz adva
    private Terem readTerem(Mozi mozi) {
        dao = new DefaultDao(Terem.class);
        List<Terem> osszTerem = dao.findAll();
        List<Terem> segedTerem = osszTerem;

        dao = new DefaultDao(Tartalmaz.class);
        List<Tartalmaz> idInTartalmaz = dao.findAll(mozi);

        List<Terem> tartalmazottTerem = new ArrayList<>();
        for (int i = 0; i < idInTartalmaz.size(); i++) {
            tartalmazottTerem.add(idInTartalmaz.get(i).getTeremId());
        }
        
        if(tartalmazottTerem.size()==osszTerem.size()) {
            return null;
        }
        
        for (int i = 0; i < osszTerem.size(); i++) {
            for (int j = 0; j < tartalmazottTerem.size(); j++) {
                if (osszTerem.get(i).getId() == tartalmazottTerem.get(j).getId()) {
                    segedTerem.remove(i);
                }
            }
        }

        Object[] kellMegTerem = segedTerem.toArray();

        Terem terem = (Terem) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, kellMegTerem, kellMegTerem[0]);
        return terem;
    }

    private Film readFilm() {
        Object[] filmek = DaoManager.getInstance().getFilmDao().findAll().toArray();
        Film film = (Film) JOptionPane.showInputDialog(parent, GuiConstants.VALASZTO_TEXT, GuiConstants.FELVITEL_BUT_TEXT, JOptionPane.QUESTION_MESSAGE, null, filmek, filmek[0]);
        return film;
    }
}
