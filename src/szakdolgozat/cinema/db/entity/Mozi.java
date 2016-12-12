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
@Table(name = "MOZI")
@NamedQueries({
    @NamedQuery(name = "Mozi.findAll", query = "SELECT m FROM Mozi m"),
    @NamedQuery(name = "Mozi.findById", query = "SELECT m FROM Mozi m WHERE m.id = :id"),
    @NamedQuery(name = "Mozi.findByNev", query = "SELECT m FROM Mozi m WHERE m.nev = :nev")})
public class Mozi implements Serializable, PersistentEntity{

    public static final String PROPERTY_NAMES[] = {"Név"};

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "NEV")
    private String nev;
    @OneToMany(mappedBy = "moziId")
    private List<Tartalmaz> tartalmazList;

    public Mozi() {
    }

    public Mozi(Integer id) {
        this.id = id;
    }

    public Mozi(Integer id, String nev) {
        this.id = id;
        this.nev = nev;
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

    @XmlTransient
    public List<Tartalmaz> getTartalmazList() {
        return tartalmazList;
    }

    public void setTartalmazList(List<Tartalmaz> tartalmazList) {
        this.tartalmazList = tartalmazList;
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
        if (!(object instanceof Mozi)) {
            return false;
        }
        Mozi other = (Mozi) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nev;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return nev;
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
        }
    }

}
