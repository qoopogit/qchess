/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.util;

/**
 *
 * @author alberto
 */
public class TimeUtil {

    public static String getTimeFormater(long diferencia) {
        if (diferencia > 1000) {
            return diferencia / 1000 + "s " + diferencia % 1000 + "ms";
        }
        return diferencia + "ms";
    }

    public static String getTime(long inicial) {
        return "(" + getTimeFormater(System.currentTimeMillis() - inicial) + ")";
    }
}
