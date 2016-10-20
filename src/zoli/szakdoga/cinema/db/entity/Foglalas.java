package zoli.szakdoga.cinema.db.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoli
 */
@Entity
@Table(name = "FOGLALAS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Foglalas.findAll", query = "SELECT f FROM Foglalas f"),
    @NamedQuery(name = "Foglalas.findById", query = "SELECT f FROM Foglalas f WHERE f.id = :id"),
    @NamedQuery(name = "Foglalas.findByMikor", query = "SELECT f FROM Foglalas f WHERE f.mikor = :mikor")})
public class Foglalas  implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Felhasználó", "Mikor", "Szék"};

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MIKOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mikor;
    @JoinColumn(name = "FELHASZNALO_ID", referencedColumnName = "ID")
    @ManyToOne
    private Felhasznalo felhasznaloId;
    @JoinColumn(name = "SZEK_ID", referencedColumnName = "ID")
    @ManyToOne
    private Szek szekId;

    public Foglalas() {
    }

    public Foglalas(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getMikor() {
        return mikor;
    }

    public void setMikor(Date mikor) {
        this.mikor = mikor;
    }

    public Felhasznalo getFelhasznaloId() {
        return felhasznaloId;
    }

    public void setFelhasznaloId(Felhasznalo felhasznaloId) {
        this.felhasznaloId = felhasznaloId;
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
        if (!(object instanceof Foglalas)) {
            return false;
        }
        Foglalas other = (Foglalas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return felhasznaloId + " " + mikor + " " + szekId;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return felhasznaloId;
            case 1:
                return mikor;
            case 2:
                return szekId;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setFelhasznaloId((Felhasznalo) value);
                break;
            case 1:
                setMikor((Date) value);
                break;
            case 2:
                setSzekId((Szek) value);
                break;
        }
    }

}
