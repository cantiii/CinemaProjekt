/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoli.szakdoga.cinema.db.entity;

import java.io.Serializable;
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
@Table(name = "MOZI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mozi.findAll", query = "SELECT m FROM Mozi m"),
    @NamedQuery(name = "Mozi.findById", query = "SELECT m FROM Mozi m WHERE m.id = :id"),
    @NamedQuery(name = "Mozi.findByNev", query = "SELECT m FROM Mozi m WHERE m.nev = :nev")})
public class Mozi implements Serializable {

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
        return "zoli.szakdoga.cinema.db.entity.Mozi[ id=" + id + " ]";
    }
    
}
