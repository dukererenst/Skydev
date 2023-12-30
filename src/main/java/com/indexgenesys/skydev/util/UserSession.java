/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.indexgenesys.skydev.util;


import com.indexgenesys.skydev.entity.UserAccount;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Ernest
 */
@Named
@SessionScoped
public class UserSession implements Serializable {

    @Getter
    @Setter
    private UserAccount userAccountUR = null;

   

   

   

}
