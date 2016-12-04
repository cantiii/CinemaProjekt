package szakdolgozat.cinema.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoli
 */
@Entity
@Table(name = "VETITES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vetites.findAll", query = "SELECT v FROM Vetites v"),
    @NamedQuery(name = "Vetites.findById", query = "SELECT v FROM Vetites v WHERE v.id = :id"),
    @NamedQuery(name = "Vetites.findByMikor", query = "SELECT v FROM Vetites v WHERE v.mikor = :mikor"),
    @NamedQuery(name = "Vetites.findByIdo", query = "SELECT v FROM Vetites v WHERE v.ido = :ido"),
    @NamedQuery(name = "Vetites.findFilmInVetites", query = "SELECT v FROM Vetites v WHERE v.filmId = :filmId"),
    @NamedQuery(name = "Vetites.findTeremInVetites", query = "SELECT v FROM Vetites v WHERE v.teremId = :teremId")})
public class Vetites implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Film", "Terem", "Mikor", "Időpont"};

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MIKOR")
    private String mikor;
    @Column(name = "IDO")
    private String ido;
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Film filmId;
    @JoinColumn(name = "TEREM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Terem teremId;
    @JoinColumn(name = "SZEK_ID", referencedColumnName = "ID")
    @ManyToOne
    private Szek szekId;

    public Vetites() {
    }

    public Vetites(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMikor() {
        return mikor;
    }

    public void setMikor(String mikor) {
        this.mikor = mikor;
    }

    public String getIdo() {
        return ido;
    }

    public void setIdo(String ido) {
        this.ido = ido;
    }

    public Film getFilmId() {
        return filmId;
    }

    public void setFilmId(Film filmId) {
        this.filmId = filmId;
    }

    public Terem getTeremId() {
        return teremId;
    }

    public void setTeremId(Terem teremId) {
        this.teremId = teremId;
    }

    public Szek getSzekId() {
        return szekId;
    }

    public void setSzekId(Szek szekId) {
        this.szekId = szekId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vetites)) {
            return false;
        }
        Vetites other = (Vetites) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Film: " + filmId + ",  terem: " + teremId + ", dátum: " + mikor;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return filmId;
            case 1:
                return teremId;
            case 2:
                return mikor;
            case 3:
                return ido;
            case 4:
                return szekId;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setFilmId((Film) value);
                break;
            case 1:
                setTeremId((Terem) value);
                break;
            case 2:
                setMikor((String) value);
                break;
            case 3:
                setIdo((String) value);
                break;
        }
    }

}
