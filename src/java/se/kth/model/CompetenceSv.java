/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AMore
 */
@Entity
@Table(name = "competence_sv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceSv.findAll", query = "SELECT c FROM CompetenceSv c"),
    @NamedQuery(name = "CompetenceSv.findByName", query = "SELECT c FROM CompetenceSv c WHERE c.name = :name"),
    @NamedQuery(name = "CompetenceSv.findByCompetenceId", query = "SELECT c FROM CompetenceSv c WHERE c.competenceId = :competenceId")})
public class CompetenceSv implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "competenceId")
    private Long competenceId;
    @JoinColumn(name = "competenceId", referencedColumnName = "competence_id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Competence competence;

    public CompetenceSv() {
    }

    public CompetenceSv(Long competenceId) {
        this.competenceId = competenceId;
    }

    public CompetenceSv(Long competenceId, String name) {
        this.competenceId = competenceId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    public Competence getCompetence() {
        return competence;
    }

    public void setCompetence(Competence competence) {
        this.competence = competence;
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
        if (!(object instanceof CompetenceSv)) {
            return false;
        }
        CompetenceSv other = (CompetenceSv) object;
        if ((this.competenceId == null && other.competenceId != null) || (this.competenceId != null && !this.competenceId.equals(other.competenceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.kth.model.CompetenceSv[ competenceId=" + competenceId + " ]";
    }
    
}