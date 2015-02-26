/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author work
 */
@Entity
@Table(name = "role")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r"),
    @NamedQuery(name = "Role.findByRoleName", query = "SELECT r FROM Role r WHERE r.roleName = :roleName")})
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "roleName")
    private String roleName;
    @JoinTable(name = "user_group", joinColumns = {
        @JoinColumn(name = "roleName", referencedColumnName = "roleName")}, inverseJoinColumns = {
        @JoinColumn(name = "email", referencedColumnName = "email")})
    @ManyToMany
    private Collection<Person> personCollection;

    public Role() {
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @XmlTransient
    public Collection<Person> getPersonCollection() {
        if (personCollection == null){
            personCollection = new ArrayList<>();
        }
        return personCollection;
    }

    public void setPersonCollection(Collection<Person> personCollection) {
        this.personCollection = personCollection;
    }
    
    public void addPerson(Person person) {
        List<Person> persons;
        persons = (List<Person>) getPersonCollection();
        persons.add(person);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleName != null ? roleName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.roleName == null && other.roleName != null) || (this.roleName != null && !this.roleName.equals(other.roleName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "se.kth.model.Role[ roleName=" + roleName + " ]";
    }
    
}
