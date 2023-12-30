/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.indexgenesys.skydev.entity;

import com.indexgenesys.skydev.entity.enums.PlateRequestStatus;
import com.indexgenesys.skydev.entity.enums.PlateStatus;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author ernest
 */
@Entity
@Table(name = "PLATE_POOL")
@Getter
@Setter
public class PlatePool extends EntityModel {

    @Column(name = "PLATE_NUMBER")
    private String plateNumber;

    @Column(name = "FORM_D")
    private String formD;

    @Column(name = "SERIAL_NO")
    private String serial;

    @Column(name = "CHECK_SUM")
    private String checkSum;

    @JoinColumn(name = "PLATE_REQUEST")
    @ManyToOne
    private PlateRequest platePlate;

    @Column(name = "PLATE_STATUS")
    @Enumerated(EnumType.STRING)
    private PlateStatus plateStatus;
}
