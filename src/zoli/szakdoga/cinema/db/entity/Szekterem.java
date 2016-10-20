package zoli.szakdoga.cinema.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoli
 */
@Entity
@Table(name = "SZEKTEREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Szekterem.findAll", query = "SELECT s FROM Szekterem s"),
    @NamedQuery(name = "Szekterem.findById", query = "SELECT s FROM Szekterem s WHERE s.id = :id")})
public class Szekterem  implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Terem", "Szék"};

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "SZEK_ID", referencedColumnName = "ID")
    @ManyToOne
    private Szek szekId;
    @JoinColumn(name = "TEREM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Terem teremId;

    public Szekterem() {
    }

    public Szekterem(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Szek getSzekId() {
        return szekId;
    }

    public void setSzekId(Szek szekId) {
        this.szekId = szekId;
    }

    public Terem getTeremId() {
        return teremId;
    }

    public void setTeremId(Terem teremId) {
        this.teremId = teremId;
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
        if (!(object instanceof Szekterem)) {
            return false;
        }
        Szekterem other = (Szekterem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return teremId + " " + szekId;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return teremId;
            case 1:
                return szekId;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setTeremId((Terem) value);
                break;
            case 1:
                setSzekId((Szek) value);
                break;
        }
    }

}
