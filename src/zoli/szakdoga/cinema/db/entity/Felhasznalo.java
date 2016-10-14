package zoli.szakdoga.cinema.db.entity;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zoli
 */
@Entity
@Table(name = "FELHASZNALO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Felhasznalo.findAll", query = "SELECT f FROM Felhasznalo f"),
    @NamedQuery(name = "Felhasznalo.findById", query = "SELECT f FROM Felhasznalo f WHERE f.id = :id"),
    @NamedQuery(name = "Felhasznalo.findByNev", query = "SELECT f FROM Felhasznalo f WHERE f.nev = :nev"),
    @NamedQuery(name = "Felhasznalo.findByJog", query = "SELECT f FROM Felhasznalo f WHERE f.jog = :jog")})
public class Felhasznalo extends PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Név", "Jog"};

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NEV")
    private String nev;
    @Basic(optional = false)
    @Column(name = "JOG")
    private int jog;
    @OneToMany(mappedBy = "felhasznaloId")
    private List<Foglalas> foglalasList;

    public Felhasznalo() {
    }

    public Felhasznalo(Integer id) {
        this.id = id;
    }

    public Felhasznalo(Integer id, String nev, int jog) {
        this.id = id;
        this.nev = nev;
        this.jog = jog;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public int getJog() {
        return jog;
    }

    public void setJog(int jog) {
        this.jog = jog;
    }

    @XmlTransient
    public List<Foglalas> getFoglalasList() {
        return foglalasList;
    }

    public void setFoglalasList(List<Foglalas> foglalasList) {
        this.foglalasList = foglalasList;
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
        if (!(object instanceof Felhasznalo)) {
            return false;
        }
        Felhasznalo other = (Felhasznalo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nev + " " + jog;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return nev;
            case 1:
                return jog;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setNev((String) value);
                break;
            case 3:
                setJog((Integer) value);
                break;
        }
    }
}
