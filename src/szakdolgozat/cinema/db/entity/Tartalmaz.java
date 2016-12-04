package szakdolgozat.cinema.db.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Zoli
 */
@Entity
@Table(name = "TARTALMAZ")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tartalmaz.findAll", query = "SELECT t FROM Tartalmaz t"),
    @NamedQuery(name = "Tartalmaz.findById", query = "SELECT t FROM Tartalmaz t WHERE t.id = :id"),
    @NamedQuery(name = "Tartalmaz.findMoziById", query = "SELECT t FROM Tartalmaz t WHERE t.moziId = :moziId"),
    @NamedQuery(name = "Tartalmaz.findMoziByTerem", query = "SELECT t FROM Tartalmaz t WHERE t.teremId = :teremId")})
public class Tartalmaz implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Mozi", "Terem"};
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @JoinColumn(name = "MOZI_ID", referencedColumnName = "ID")
    @ManyToOne
    private Mozi moziId;
    @JoinColumn(name = "TEREM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Terem teremId;

    public Tartalmaz() {
    }

    public Tartalmaz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mozi getMoziId() {
        return moziId;
    }

    public void setMoziId(Mozi moziId) {
        this.moziId = moziId;
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
        if (!(object instanceof Tartalmaz)) {
            return false;
        }
        Tartalmaz other = (Tartalmaz) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return moziId + " " + teremId;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return moziId;
            case 1:
                return teremId;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setMoziId((Mozi) value);
                break;
            case 1:
                setTeremId((Terem) value);
                break;
        }
    }

}
