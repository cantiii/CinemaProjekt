/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zoli.szakdoga.cinema.db.entity;

import java.io.Serializable;
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
    @NamedQuery(name = "Tartalmaz.findById", query = "SELECT t FROM Tartalmaz t WHERE t.id = :id")})
public class Tartalmaz implements Serializable {

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
        return "zoli.szakdoga.cinema.db.entity.Tartalmaz[ id=" + id + " ]";
    }
    
}
