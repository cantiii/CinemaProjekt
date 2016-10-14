package zoli.szakdoga.cinema.db.entity;

import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Vetites.findByMikor", query = "SELECT v FROM Vetites v WHERE v.mikor = :mikor")})
public class Vetites extends PersistentEntity {

    public static final String PROPERTY_NAMES[] = {"Film", "Mikor", "Terem"};
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "MIKOR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mikor;
    @JoinColumn(name = "FILM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Film filmId;
    @JoinColumn(name = "TEREM_ID", referencedColumnName = "ID")
    @ManyToOne
    private Terem teremId;

    public Vetites() {
    }

    public Vetites(Integer id) {
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
        return filmId + " " + mikor + " " + teremId;
    }

    @Override
    public Object get(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return filmId;
            case 1:
                return mikor;
            case 2:
                return teremId;
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
                setMikor((Date) value);
                break;
            case 2:
                setTeremId((Terem) value);
                break;
        }
    }
    
}
