/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.indexgenesys.skydev.controller;

import com.indexgenesys.skydev.abstracts.SkyDevMethods;
import com.indexgenesys.skydev.entity.CompanyInformation;
import com.indexgenesys.skydev.entity.EntityModel;
import javax.inject.Named;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ernest
 */
@Named(value = "companyController")
@SessionScoped
public class CompanyController implements SkyDevMethods, Serializable {

    @Getter
    @Setter
    private CompanyInformation companyInformation = new CompanyInformation();
    
    public CompanyController() {
    }

    @Override
    public void saveMethod() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void clearMethod() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void editMethod(EntityModel em) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void deleteMethod(EntityModel em) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List findAll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
