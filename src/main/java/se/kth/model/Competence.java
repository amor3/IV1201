/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author work
 */
@Entity
@Table(name = "competence")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Competence.findAll", query = "SELECT c FROM Competence c"),
    @NamedQuery(name = "Competence.findByCompetenceId", query = "SELECT c FROM Competence c WHERE c.competenceId = :competenceId")})
public class Competence implements Serializable {
    @OneToMany(mappedBy = "competenceId")
    private Collection<CompetenceProfile> competenceProfileCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "competence_id")
    private Long competenceId;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "competenceId")
    private CompetenceSv competenceSv;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "competenceId")
    private CompetenceEn competenceEn;

    public Competence() {
    }

    public Competence(Long competenceId) {
        this.competenceId = competenceId;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    public CompetenceSv getCompetenceSv() {
        return competenceSv;
    }

    public void setCompetenceSv(CompetenceSv competenceSv) {
        this.competenceSv = competenceSv;
    }

    public CompetenceEn getCompetenceEn() {
        return competenceEn;
    }

    public void setCompetenceEn(CompetenceEn competenceEn) {
        this.competenceEn = competenceEn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (competenceId != null ? competenceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Competence)) {
            return false;
        }
        Competence other = (Competence) object;
        if ((this.competenceId == null && other.competenceId != null) || (this.competenceId != null && !this.competenceId.equals(other.competenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.kth.model.Competence[ competenceId=" + competenceId + " ]";
    }

    @XmlTransient
    public Collection<CompetenceProfile> getCompetenceProfileCollection() {
        return competenceProfileCollection;
    }

    public void setCompetenceProfileCollection(Collection<CompetenceProfile> competenceProfileCollection) {
        this.competenceProfileCollection = competenceProfileCollection;
    }
    
}
