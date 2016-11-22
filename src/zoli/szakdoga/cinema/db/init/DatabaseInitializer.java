package zoli.szakdoga.cinema.db.init;

import zoli.szakdoga.cinema.db.dao.*;
import zoli.szakdoga.cinema.db.entity.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Zoli
 */
public class DatabaseInitializer {

    private DatabaseInitializer() {
    }

    public void init() {
        upload();
    }

    private void upload() {
        List<Felhasznalo> felhasznalok = new ArrayList<>();
        felhasznalok.add(createFelhasznalo("superuser", 0));
        felhasznalok.add(createFelhasznalo("admin", 1));
        felhasznalok.add(createFelhasznalo("Kriszti", 2));

        for (Felhasznalo felhasznalo : felhasznalok) {
            DaoManager.getInstance().getFelhasznaloDao().create(felhasznalo);
        }

        List<Film> filmek = new ArrayList<>();
        filmek.add(createFilm("A remény rabjai", "Frank Darabont", "Tim Robbins", "1994 - dráma, krimi", 140, 16));
        filmek.add(createFilm("A keresztapa", "Francis Coppola", "Marlon Brando", "1972 - bünűgy, dráma", 180, 18));
        filmek.add(createFilm("A sötét lovag", "Christopher Nolan", "Christian Bale", "2008 - akció", 155, 16));
        filmek.add(createFilm("Schindler listája", "Steven Spielberg", "Liam Neeson", "1993 - történemi", 200, 12));
        filmek.add(createFilm("Ponyvaregény", "Quentin Tarantino", "John Travolta", "1994 - dráma", 150, 18));
        filmek.add(createFilm("A Gyűrűk Ura", "Peter Jackson", "Marlon Brando", "2003 - kalanad, fantazi", 201, 12));
        filmek.add(createFilm("Harcosok klubja", "David Fincher", "Brad Pitt", "1999 - dráma", 140, 16));
        filmek.add(createFilm("Csillagok háborúja", "George Lucas", "Harrison Ford", "1977 - sci-fi, kaland", 120, 8));
        filmek.add(createFilm("Mátrix", "Wachowski Brothers", "Keanu Reeves", "1999 - akció", 155, 12));
        filmek.add(createFilm("Forrest Gump", "Robert Zemeckis", "Tom Hanks", "1994 - vígjáték, dráma", 139, 8));

        for (Film film : filmek) {
            DaoManager.getInstance().getFilmDao().create(film);
        }

        List<Mozi> mozik = new ArrayList<>();
        mozik.add(createGroup("Magyar"));
        mozik.add(createGroup("Angol"));
        mozik.add(createGroup("Spanyol"));
        mozik.add(createGroup("Olasz"));
        mozik.add(createGroup("Német"));

        for (Mozi mozi : mozik) {
            DaoManager.getInstance().getMoziDao().create(mozi);
        }

        List<Terem> termek = new ArrayList<>();
        termek.add(createTerem("Szeged", 25));
        termek.add(createTerem("Budapest", 80));
        termek.add(createTerem("Győr", 50));

        termek.add(createTerem("London", 80));

        termek.add(createTerem("Roma", 50));
        termek.add(createTerem("Torino", 50));

        termek.add(createTerem("Madrid", 25));
        termek.add(createTerem("Barcelona", 25));

        termek.add(createTerem("Berlin", 80));

        for (Terem terem : termek) {
            DaoManager.getInstance().getTeremDao().create(terem);
        }

        List<Tartalmaz> tartalmazok = new ArrayList<>();
        tartalmazok.add(createTartalmaz(mozik.get(0), termek.get(0)));
        tartalmazok.add(createTartalmaz(mozik.get(0), termek.get(1)));
        tartalmazok.add(createTartalmaz(mozik.get(0), termek.get(2)));
        tartalmazok.add(createTartalmaz(mozik.get(1), termek.get(3)));
        tartalmazok.add(createTartalmaz(mozik.get(3), termek.get(4)));
        tartalmazok.add(createTartalmaz(mozik.get(3), termek.get(5)));
        tartalmazok.add(createTartalmaz(mozik.get(2), termek.get(6)));
        tartalmazok.add(createTartalmaz(mozik.get(2), termek.get(7)));
        tartalmazok.add(createTartalmaz(mozik.get(4), termek.get(8)));

        for (Tartalmaz tartalmaz : tartalmazok) {
            DaoManager.getInstance().getTartalmazDao().create(tartalmaz);
        }

    }

    private Felhasznalo createFelhasznalo(String nev, Integer jog) {
        Felhasznalo felhasznalo = new Felhasznalo();
        felhasznalo.setNev(nev);
        felhasznalo.setJog(jog);
        return felhasznalo;
    }

    private Film createFilm(String cim, String rendezo, String szines, String leiras, Integer hossz, Integer kor) {
        Film film = new Film();
        film.setCim(cim);
        film.setRendezo(rendezo);
        film.setSzinesz(szines);
        film.setLeiras(leiras);
        film.setHossz(hossz);
        film.setKorhatar(kor);
        return film;
    }

    private Mozi createGroup(String nev) {
        Mozi mozi = new Mozi();
        mozi.setNev(nev);
        return mozi;
    }

    private Terem createTerem(String nev, Integer ferohely) {
        Terem terem = new Terem();
        terem.setNev(nev);
        terem.setFerohely(ferohely);
        return terem;
    }

    private Tartalmaz createTartalmaz(Mozi mozi, Terem terem) {
        Tartalmaz tartalmaz = new Tartalmaz();
        tartalmaz.setMoziId(mozi);
        tartalmaz.setTeremId(terem);
        return tartalmaz;
    }

    public static DatabaseInitializer getInstance() {
        return DatabaseInitializerHolder.INSTANCE;
    }

    private static class DatabaseInitializerHolder {

        private static final DatabaseInitializer INSTANCE = new DatabaseInitializer();
    }
}
