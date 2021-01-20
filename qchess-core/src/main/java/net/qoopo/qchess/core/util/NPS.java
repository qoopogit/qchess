/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.util;

/**
 * Contador de Nodos por segundo
 *
 * @author alberto
 */
public class NPS {

    public static int count;

    private static long tInicio;
    private static long tFin;

    public static void iniciar() {
        count = 0;
        tInicio = System.currentTimeMillis();
    }

    public static void fin() {
        tFin = System.currentTimeMillis();
    }

    public static void agregar() {
        count++;
    }

    public static long getDelta() {
        return tFin - tInicio;
    }

    public static long getNPS() {
        return getDelta() != 0 ? (count * 1000 / getDelta()) : 0;
    }
}
