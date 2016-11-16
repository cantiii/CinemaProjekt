package zoli.szakdoga.cinema.db.dao;

import zoli.szakdoga.cinema.db.entity.*;

/**
 *
 * @author pappz
 */
public class DaoManager {

    private GenericDao<Felhasznalo> felhasznaloDao;
    private GenericDao<Film> filmDao;
    private GenericDao<Foglalas> foglalasDao;
    private GenericDao<Mozi> moziDao;
    private GenericDao<Szek> szekDao;
    private GenericDao<Tartalmaz> tartalmazDao;
    private GenericDao<Terem> teremDao;
    private GenericDao<Vetites> vetitesDao;

    public GenericDao<Felhasznalo> getFelhasznaloDao() {
        if (felhasznaloDao == null) {
            felhasznaloDao = new DefaultDao<>(Felhasznalo.class);
        }
        return felhasznaloDao;
    }

    public GenericDao<Film> getFilmDao() {
        if (filmDao == null) {
            filmDao = new DefaultDao<>(Film.class);
        }
        return filmDao;
    }

    public GenericDao<Foglalas> getFoglalasDao() {
        if (foglalasDao == null) {
            foglalasDao = new DefaultDao<>(Foglalas.class);
        }
        return foglalasDao;
    }

    public GenericDao<Mozi> getMoziDao() {
        if (moziDao == null) {
            moziDao = new DefaultDao<>(Mozi.class);
        }
        return moziDao;
    }

    public GenericDao<Szek> getSzekDao() {
        if (szekDao == null) {
            szekDao = new DefaultDao<>(Szek.class);
        }
        return szekDao;
    }

    public GenericDao<Tartalmaz> getTartalmazDao() {
        if (tartalmazDao == null) {
            tartalmazDao = new DefaultDao<>(Tartalmaz.class);
        }
        return tartalmazDao;
    }

    public GenericDao<Terem> getTeremDao() {
        if (teremDao == null) {
            teremDao = new DefaultDao<>(Terem.class);
        }
        return teremDao;
    }

    public GenericDao<Vetites> getVetitesDao() {
        if (vetitesDao == null) {
            vetitesDao = new DefaultDao<>(Vetites.class);
        }
        return vetitesDao;
    }

    private DaoManager() {
    }

    public static DaoManager getInstance() {
        return DaoManagerHolder.INSTANCE;
    }

    private static class DaoManagerHolder {

        private static final DaoManager INSTANCE = new DaoManager();
    }
}
