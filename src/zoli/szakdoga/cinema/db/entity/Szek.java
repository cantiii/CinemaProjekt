package zoli.szakdoga.cinema.db.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Zoli
 */
@Entity
@Table(name = "SZEK")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Szek.findAll", query = "SELECT s FROM Szek s"),
    @NamedQuery(name = "Szek.findById", query = "SELECT s FROM Szek s WHERE s.id = :id"),
    @NamedQuery(name = "Szek.findBySzekszam", query = "SELECT s FROM Szek s WHERE s.szekszam = :szekszam"),
    @NamedQuery(name = "Szek.findByFoglalt", query = "SELECT s FROM Szek s WHERE s.foglalt = :foglalt")})
public class Szek  implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Székszám"};
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SZEKSZAM")
    private int szekszam;
    @Basic(optional = false)
    @Column(name = "FOGLALT")
    private boolean foglalt;
    @OneToMany(mappedBy = "szekId")
    private List<Foglalas> foglalasList;
    @OneToMany(mappedBy = "szekId")
    private List<Vetites> vetitesList;

    public Szek() {
    }

    public Szek(Integer id) {
        this.id = id;
    }

    public Szek(Integer id, int szekszam, boolean foglalt) {
        this.id = id;
        this.szekszam = szekszam;
        this.foglalt = foglalt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSzekszam() {
        return szekszam;
    }

    public void setSzekszam(int szekszam) {
        this.szekszam = szekszam;
    }

    public boolean getFoglalt() {
        return foglalt;
    }

    public void setFoglalt(boolean foglalt) {
        this.foglalt = foglalt;
    }

    @XmlTransient
    public List<Foglalas> getFoglalasList() {
        return foglalasList;
    }

    public void setFoglalasList(List<Foglalas> foglalasList) {
        this.foglalasList = foglalasList;
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
        if (!(object instanceof Szek)) {
            return false;
        }
        Szek other = (Szek) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + szekszam + ". szék";
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return szekszam;
            case 1:
                return foglalt;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setSzekszam((Integer) value);
                break;
            case 1:
                setFoglalt((boolean) value);
                break;
        }
    }

}
