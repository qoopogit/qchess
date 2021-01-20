/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.util;

/**
 * Clase usada para calcular el tiempo que toma calcular el tablero
 *
 * @author alberto
 */
public class CalculoTime {

    public static long total;
    public static long cuantos;

    public static void fin(long tInicio) {
        long tFin = System.currentTimeMillis();
        total += (tFin - tInicio);
        cuantos++;
    }

    public static void reset() {
        total = cuantos = 0;
    }

    //devuelve el promedio
    public static long getTime() {
        return cuantos != 0 ? (total / cuantos) : 0;
    }

}
