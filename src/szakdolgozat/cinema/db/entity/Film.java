package szakdolgozat.cinema.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Papp Zoltán - VMW84B
 */
@Entity
@Table(name = "FILM")
@NamedQueries({
    @NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f"),
    @NamedQuery(name = "Film.findById", query = "SELECT f FROM Film f WHERE f.id = :id"),
    @NamedQuery(name = "Film.findByNev", query = "SELECT f FROM Film f WHERE f.cim = :nev"),
    @NamedQuery(name = "Film.findByRendezo", query = "SELECT f FROM Film f WHERE f.rendezo = :rendezo"),
    @NamedQuery(name = "Film.findBySzinesz", query = "SELECT f FROM Film f WHERE f.szinesz = :szinesz"),
    @NamedQuery(name = "Film.findByLeiras", query = "SELECT f FROM Film f WHERE f.leiras = :leiras"),
    @NamedQuery(name = "Film.findByHossz", query = "SELECT f FROM Film f WHERE f.hossz = :hossz"),
    @NamedQuery(name = "Film.findByKorhatar", query = "SELECT f FROM Film f WHERE f.korhatar = :korhatar")})
public class Film implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Cím", "Rendező", "Színész", "Hossz", "Korhatár"};
    public static final String FULL_PROPERTY_NAMES[] = {"Cím", "Rendező", "Színész", "Hossz", "Korhatár", "Leírás"};
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "CIM")
    private String cim;
    @Basic(optional = false)
    @Column(name = "RENDEZO")
    private String rendezo;
    @Basic(optional = false)
    @Column(name = "SZINESZ")
    private String szinesz;
    @Basic(optional = false)
    @Column(name = "LEIRAS")
    private String leiras;
    @Basic(optional = false)
    @Column(name = "HOSSZ")
    private int hossz;
    @Basic(optional = false)
    @Column(name = "KORHATAR")
    private int korhatar;
    @OneToMany(mappedBy = "filmId")
    private List<Vetites> vetitesList;

    public Film() {
    }

    public Film(Integer id) {
        this.id = id;
    }

    public Film(Integer id, String cim, String rendezo, String szinesz, String leiras, int hossz, int korhatar) {
        this.id = id;
        this.cim = cim;
        this.rendezo = rendezo;
        this.szinesz = szinesz;
        this.leiras = leiras;
        this.hossz = hossz;
        this.korhatar = korhatar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getRendezo() {
        return rendezo;
    }

    public void setRendezo(String rendezo) {
        this.rendezo = rendezo;
    }

    public String getSzinesz() {
        return szinesz;
    }

    public void setSzinesz(String szinesz) {
        this.szinesz = szinesz;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public int getHossz() {
        return hossz;
    }

    public void setHossz(int hossz) {
        this.hossz = hossz;
    }

    public int getKorhatar() {
        return korhatar;
    }

    public void setKorhatar(int korhatar) {
        this.korhatar = korhatar;
    }

    @XmlTransient
    public List<Vetites> getVetitesList() {
        return vetitesList;
    }

    public void setVetitesList(List<Vetites> vetitesList) {
        this.vetitesList = vetitesList;
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
        if (!(object instanceof Film)) {
            return false;
        }
        Film other = (Film) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return cim;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return cim;
            case 1:
                return rendezo;
            case 2:
                return szinesz;
            case 3:
                return hossz;
            case 4:
                return korhatar;
            case 5:
                return leiras;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setCim((String) value);
                break;
            case 1:
                setRendezo((String) value);
                break;
            case 2:
                setSzinesz((String) value);
                break;
            case 3:
                setHossz((Integer) value);
                break;
            case 4:
                setKorhatar((Integer) value);
                break;
            case 5:
                setLeiras((String) value);
                break;
        }
    }

}
