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
@Table(name = "TEREM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Terem.findAll", query = "SELECT t FROM Terem t"),
    @NamedQuery(name = "Terem.findById", query = "SELECT t FROM Terem t WHERE t.id = :id"),
    @NamedQuery(name = "Terem.findByNev", query = "SELECT t FROM Terem t WHERE t.nev = :nev"),
    @NamedQuery(name = "Terem.findByFerohely", query = "SELECT t FROM Terem t WHERE t.ferohely = :ferohely")})
public class Terem  implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Név", "Férőhely"};

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
    @Column(name = "FEROHELY")
    private int ferohely;
    @OneToMany(mappedBy = "teremId")
    private List<Tartalmaz> tartalmazList;
    @OneToMany(mappedBy = "teremId")
    private List<Vetites> vetitesList;
    @OneToMany(mappedBy = "teremId")
    private List<Szekterem> szekteremList;

    public Terem() {
    }

    public Terem(Integer id) {
        this.id = id;
    }

    public Terem(Integer id, String nev, int ferohely) {
        this.id = id;
        this.nev = nev;
        this.ferohely = ferohely;
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

    public int getFerohely() {
        return ferohely;
    }

    public void setFerohely(int ferohely) {
        this.ferohely = ferohely;
    }

    @XmlTransient
    public List<Tartalmaz> getTartalmazList() {
        return tartalmazList;
    }

    public void setTartalmazList(List<Tartalmaz> tartalmazList) {
        this.tartalmazList = tartalmazList;
    }

    @XmlTransient
    public List<Vetites> getVetitesList() {
        return vetitesList;
    }

    public void setVetitesList(List<Vetites> vetitesList) {
        this.vetitesList = vetitesList;
    }

    @XmlTransient
    public List<Szekterem> getSzekteremList() {
        return szekteremList;
    }

    public void setSzekteremList(List<Szekterem> szekteremList) {
        this.szekteremList = szekteremList;
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
        if (!(object instanceof Terem)) {
            return false;
        }
        Terem other = (Terem) object;
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
            case 1:
                return ferohely;
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
            case 1:
                setFerohely((Integer) value);
                break;
        }
    }

}
