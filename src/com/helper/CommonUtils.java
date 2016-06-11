/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helper;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author italo
 * Helper Class to help on getting the current time
 */
public class CommonUtils {
    public static Timestamp getTimestamp(){
        Date date =  new Date();
        return new Timestamp(date.getTime());
    }
}
