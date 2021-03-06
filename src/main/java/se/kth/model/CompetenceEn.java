/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author work
 */
@Entity
@Table(name = "competence_en")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CompetenceEn.findAll", query = "SELECT c FROM CompetenceEn c"),
    @NamedQuery(name = "CompetenceEn.findByName", query = "SELECT c FROM CompetenceEn c WHERE c.name = :name"),
    @NamedQuery(name = "CompetenceEn.findById", query = "SELECT c FROM CompetenceEn c WHERE c.id = :id")})

public class CompetenceEn implements Serializable, CompetenceLangInterface {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "competenceId", referencedColumnName = "competence_id")
    @OneToOne(optional = false)
    private Competence competenceId;

    public CompetenceEn() {
    }

    public CompetenceEn(Integer id) {
        this.id = id;
    }

    public CompetenceEn(Competence competenceId, String name) {
        this.competenceId = competenceId;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Competence getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Competence competenceId) {
        this.competenceId = competenceId;
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
        if (!(object instanceof CompetenceEn)) {
            return false;
        }
        CompetenceEn other = (CompetenceEn) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.kth.model.CompetenceEn[ id=" + id + " ]";
    }
    
}
