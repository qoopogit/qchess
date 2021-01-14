/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.util;

import net.qoopo.qchess.core.Constantes;

/**
 *
 * @author alberto
 */
public class LenguajeUtil {

    /**
     * Convierte la letra de la pieza a espa;ol, que es el utilizado por el
     * engine
     *
     * @param pieza
     * @param lenguaje en el que se encuentra el valor de 'pieza'
     * @return Devuelve la letra de la pieza en el lenguaje del siste (espa;ol)
     */
    public static String getLetraPieza(String pieza, int lenguaje) {
        switch (lenguaje) {
            case Constantes.LENGUAJE_INGLES -> {
                switch (pieza.toUpperCase()) {
                    case "R" -> {
                        return "T";
                    }
                    case "K" -> {
                        return "R";
                    }
                    case "Q" -> {
                        return "D";
                    }
                    case "B" -> {
                        return "A";
                    }
                    case "N" -> {
                        return "C";
                    }
                    case "P" -> {
                        return "P";
                    }
                }
            }
        }
        return pieza;
    }

    /**
     * Convierte la letra de la pieza a espa;ol, que es el utilizado por el
     * engine, pero conservando mayusculas y minusculas
     *
     * @param pieza
     * @param lenguaje
     * @return
     */
    public static String getLetraPiezaFEN(String pieza, int lenguaje) {
        switch (lenguaje) {
            case Constantes.LENGUAJE_INGLES -> {
                switch (pieza) {
                    case "R" -> {
                        return "T";
                    }
                    case "K" -> {
                        return "R";
                    }
                    case "Q" -> {
                        return "D";
                    }
                    case "B" -> {
                        return "A";
                    }
                    case "N" -> {
                        return "C";
                    }
                    case "P" -> {
                        return "P";
                    }
                    case "r" -> {
                        return "t";
                    }
                    case "k" -> {
                        return "r";
                    }
                    case "q" -> {
                        return "d";
                    }
                    case "b" -> {
                        return "a";
                    }
                    case "n" -> {
                        return "c";
                    }
                    case "p" -> {
                        return "p";
                    }
                }
            }
        }
        return pieza;
    }
}
