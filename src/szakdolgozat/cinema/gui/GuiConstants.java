package szakdolgozat.cinema.gui;

/**
 *
 * @author Papp Zoltán - VMW84B
 * Felületen megtalálható gombok, menüpontok konstansai,
 * valamint tájékoztató-, hibaüzenetek szövegei.
 */
public class GuiConstants {

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public static final String FRAME_TITLE = "Cinema Projekt";
    public static final String MUSOR_MENU_TEXT = "Műsor";
    public static final String FILM_MENU_TEXT = "Filmek";
    public static final String AR_MENU_TEXT = "Árak";
    public static final String KAPCSOLAT_MENU_TEXT = "Kapcsolat";
    public static final String ADMIN_MENU_TEXT = "Admin";
    public static final String TORTENET_MENU_TEXT = "Történet";
    public static final String LOGOUT_MENU_TEXT = "Logout";
    public static final String EXIT = "Kilépés, az alkalmazás leáll...";

    public static final String MOZI_MENU_TEXT = "Mozi";
    public static final String TEREM_MENU_TEXT = "Terem";
    public static final String HOZZARENDELES_TEXT = "Hozzárendelés";
    public static final String FELHASZNALO_MENU_TEXT = "Felhasználó";

    public static final String LEIRAS_MENU_TEXT = "Leírás";

    public static final String LOGIN_BUT_TEXT = "Bejelentkezés";
    public static final String REG_BUT_TEXT = "Regisztráció";
    public static final String KERES_BUT_TEXT = "Keresés";
    public static final String PDF_BUT_TEXT = "PDF";
    public static final String NODATA = "Nincs megjeleníthető adat!";

    public static final String FOGLALAS_BUT_TEXT = "FOGLALÁS";
    public static final String TEREM_MEGTELT = "Sajnáljuk, a vetítés megtelt!";
    public static final String JEGY_DB = "Jegyek darabszámát(szabadhely: ";
    public static final String JEGY_DIAK_DB = "Ebből a diák:";
    public static final String FOGLALAS_FAIL = "Előbb válasszon ki egy vetítést!";
    public static final String JEGY_FAIL = "Nem adott meg jegy mennyiséget!";
    public static final String FOGLALT_SZEK = "Szék már foglalt!";
    public static final String FOGLALAS_MENTES = "Véglegesíti a foglalást?";
    public static final String FOGLALAS_VEGE = "A szekék lefoglalva!";
    public static final String KOSZONET_TEXT = "Köszönjük!";
    
    public static final String SZEK_MENU_TEXT = "Székeim";
    public static final String NOSZEK = "Nincs erre a vetítésre foglalásom!";

    public static final String FELVITEL_BUT_TEXT = "Felvitel";
    public static final String TORLES_BUT_TEXT = "TÖRLÉS";
    public static final String FELVITEL_MUSOR_TEXT = "Műsor felvitel";
    public static final String TORLES_MUSOR_TEXT = "Műsor TÖRLÉS";
    public static final String FELVITEL_DATUM_TEXT = "Dátum felvitel";
    public static final String FELVITEL_IDO_TEXT = "Időpont felvitel";
    public static final String FELVITEL_FILM_TEXT = "Film felvitel";
    public static final String FELVITEL_FILMRENDEZO_TEXT = "Rendező felvitel";
    public static final String FELVITEL_FILMSZINESZ_TEXT = "Színész felvitel";
    public static final String FELVITEL_FILMLEIRAS_TEXT = "Leírás felvitel";
    public static final String FELVITEL_FILMHOSSZ_TEXT = "Hossz felvitel";
    public static final String FELVITEL_FILMKOR_TEXT = "Korhatár felvitel";
    public static final String TORLES_FILM_TEXT = "Film TÖRLÉS";
    public static final String FELVITEL_MOZI_TEXT = "Mozi felvitel";
    public static final String TORLES_MOZI_TEXT = "Mozi TÖRLÉS";
    public static final String FELVITEL_TEREM_TEXT = "Terem felvitel";
    public static final String FELVITEL_TEREMHELY_TEXT = "Terem hely felvitel";
    public static final String NOMOZI = "Nincs még mozi, előbb azt vigyen fel!";
    public static final String NOFILM = "Nincs még film, előbb azt vigyen fel!";
    public static final String TORLES_TEREM_TEXT = "Terem TÖRLÉS";
    public static final String SZEKRENDELES = " darab széket beraktunk a terembe!";
    public static final String FELVITEL_FELHASZNALO_TEXT = "Felhasznaló felvitel";
    public static final String TORLES_FELHASZNALO_TEXT = "Felhasznaló TÖRLÉS";
    public static final String MOZI_TEREM_TEXT = "Terem hozzárendelés";
    public static final String TORLES = "Biztos törli?";
    public static final String TORLESFAIL = "Önmagát nem törölheti ki!";

    public static final String VALASZTO_TEXT = "Válaszd ki a megfelelő elemet!";

    public static final String LENGHT_ERROR = "Nem megfelelő hosszúság!";
    public static final String FORMAT_ERROR = "Nem jó formátum, helyesen: ÉÉÉÉ/HH/NN";
    public static final String DATE_ERROR = "Nem jó időintervallum!\n(MIN: +1nap, MAX: +60 nap)";
    public static final String UNIQUE_ERROR = "Nem egyedi adat!";
    public static final String DB_DATA_ERROR = "Nem helyes adat, érvénytelenített szerkesztés!";
    public static final String INVALID_NUMBER = "Érvénytelen szám formátum.";
    public static final String INPUT_ERROR = "Beviteli hiba";
    public static final String NOMORE_ROOM_ERROR = "Nincs használható terem!";

    public static final String USERNAME_TEXT = "Felhasználónév:";
    public static final String USERNAME_LENGHT = "Min hossz: 3 betű, max: 10";
    public static final String FAIL = "HIBA!";
    public static final String USERNAME_REQUIRED = "Név megadása kötelező!";
    public static final String USERNAME_TAKEN = "A felhasznalónév már foglalt!";
    public static final String USERNAME_NO = "Nincs ilyen felhasznaló az adatbázisban!";
    public static final String NOMORE_TRY = "Átírányítás a regisztrációhoz!\nLejárt a 3 próbálkozás lehetősége.";
}
