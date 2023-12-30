/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.indexgenesys.skydev.entity;

import com.indexgenesys.skydev.entity.enums.UserStatus;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Ernest
 */
@Entity
@Table(name = "USER_ACCOUNT")
@Getter
@Setter
public class UserAccount extends EntityModel {

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;
    
     @JoinColumn(name = "COMPANY_INFORMATION")
    @ManyToOne
    private CompanyInformation companyInformation;

   
    @Column(name = "USER_STATUS")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    
    @Column(name = "USER_PASSWORD")
    private String userPassword;
    
    @Column(name = "RESET_PASSWORD")
    private boolean resetPassword;
    
  
    
    @Column(name = "PASSWORD_EXPIRY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordExpiry;
    
    @Transient
    private String userPasswordText;

    @Override
    public String toString() {
        return  fullName ;
    }

    
    
}
