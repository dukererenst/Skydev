/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.indexgenesys.skydev.entity;

import com.indexgenesys.skydev.entity.enums.PlateRequestStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ernest
 */
@Entity
@Table(name = "PLATE_REQUEST")
@Getter
@Setter
public class PlateRequest extends EntityModel {

     @Column(name = "SUFFIX")
    private String suffix;
    
    @Column(name = "START_RANGE")
    private int startRange;

    @Column(name = "END_RANGE")
    private int endRange;

    @Column(name = "FORM_D_START")
    private int formDStart;

    @Column(name = "FORM_D_END")
    private int formDEnd;
    
     @Column(name = "REQUEST_STATUS")
     @Enumerated(EnumType.STRING)
    private PlateRequestStatus requestStatus;

}
