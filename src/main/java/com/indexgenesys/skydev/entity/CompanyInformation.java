/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.indexgenesys.skydev.entity;

import com.indexgenesys.skydev.entity.enums.CompanyType;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ernest
 */
@Entity
@Table(name = "COMPANY_INFORMATION")
@Getter
@Setter
public class CompanyInformation extends EntityModel {

    @OneToMany(mappedBy = "companyInformation", fetch = FetchType.LAZY)
    private List<UserAccount> userAccounts;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "COMPANY_CODE")
    private String companyCode;
    
    @Column(name = "TELEPHONE")
    private String telephone;
    
     @Column(name = "COMPANY_TYPE")
     @Enumerated(EnumType.STRING)
    private CompanyType companyType;
}
