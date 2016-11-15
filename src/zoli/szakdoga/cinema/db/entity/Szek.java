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
    @NamedQuery(name = "Szek.findBySor", query = "SELECT s FROM Szek s WHERE s.sor = :sor"),
    @NamedQuery(name = "Szek.findByOszlop", query = "SELECT s FROM Szek s WHERE s.oszlop = :oszlop")})
public class Szek  implements Serializable, PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Sor"};
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "SOR")
    private int sor;
    @Basic(optional = false)
    @Column(name = "OSZLOP")
    private int oszlop;
    @OneToMany(mappedBy = "szekId")
    private List<Foglalas> foglalasList;
    @OneToMany(mappedBy = "szekId")
    private List<Szekterem> szekteremList;

    public Szek() {
    }

    public Szek(Integer id) {
        this.id = id;
    }

    public Szek(Integer id, int sor, int oszlop) {
        this.id = id;
        this.sor = sor;
        this.oszlop = oszlop;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSor() {
        return sor;
    }

    public void setSor(int sor) {
        this.sor = sor;
    }

    public int getOszlop() {
        return oszlop;
    }

    public void setOszlop(int oszlop) {
        this.oszlop = oszlop;
    }

    @XmlTransient
    public List<Foglalas> getFoglalasList() {
        return foglalasList;
    }

    public void setFoglalasList(List<Foglalas> foglalasList) {
        this.foglalasList = foglalasList;
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
        return sor + ". sor, " + oszlop + ". oszlop";
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return sor;
            case 1:
                return oszlop;
            default:
                return null;
        }
    }

    @Override
    public void set(int columnIndex, Object value) {
        switch (columnIndex) {
            case 0:
                setSor((Integer) value);
                break;
            case 1:
                setOszlop((Integer) value);
                break;
        }
    }

}
