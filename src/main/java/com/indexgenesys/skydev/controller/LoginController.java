/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.indexgenesys.skydev.controller;

import com.indexgenesys.skydev.entity.UserAccount;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Faces;

/**
 *
 * @author ernest
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {
 @Getter
    @Setter
    private String username = null;
    @Getter
    @Setter
    private String password = null;
    @Getter
    @Setter
    private String resetpassword = null;
    @Getter
    @Setter
    private String Confirmpassword = null;
    @Getter
    @Setter
    private boolean enableChangePassword = false;
    
     @Getter
    @Setter
     private UserAccount userAccount = null;
    
    
    public LoginController() {
    }
    
     public void loginUser() {
         Faces.redirect("dashboard.xhtml"); 
     }
     
     public void logout()
     {
         
     }
}
