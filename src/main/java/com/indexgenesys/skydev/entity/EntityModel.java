/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.indexgenesys.skydev.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Ernest
 */
@Setter
@Getter
@MappedSuperclass
public class EntityModel extends Object implements Serializable {

     
    private static final long serialVersionUID = 1L;
   
   

    @JsonIgnore
    @Id
    @Column(name = "ID")
    private String id;

    @JsonIgnore
    @Column(name = "DELETED")
    private boolean deleted;

    @JsonIgnore
    @Column(name = "LAST_MODIFIED_BY")
    private String lastModifiedBy;
    
    @JsonIgnore
    @Column(name = "LAST_MODIFIED_AT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

    public EntityModel() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + (this.deleted ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.lastModifiedBy);
        hash = 37 * hash + Objects.hashCode(this.lastModifiedAt);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntityModel other = (EntityModel) obj;
        if (this.deleted != other.deleted) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.lastModifiedBy, other.lastModifiedBy)) {
            return false;
        }
        return Objects.equals(this.lastModifiedAt, other.lastModifiedAt);
    }
    
 
    @PreUpdate
    public void updateDate()
    {
        lastModifiedAt = new Date();
      
    }
}
