/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.tablero;

import net.qoopo.qchess.core.Pieza;

/**
 *
 * @author alberto
 */
public class Casilla {

    private String nombre;
    private Pieza pieza;
    private int fila, columna; //desde 1 a 8

    /**
     * Indica que esta casilla esta habilitada para comer al paso.
     */
    private boolean alPaso;

    public Casilla(String nombre, int columna, int fila) {
        this.nombre = nombre;
        this.fila = fila;
        this.columna = columna;
    }

    public boolean isOcupada() {
        return pieza != null;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isAlPaso() {
        return alPaso;
    }

    public void setAlPaso(boolean alPaso) {
        this.alPaso = alPaso;
    }

    @Override
    public String toString() {
//        return "n=" + nombre + ", o=" + ocupada + ", p=" + pieza;
//        return pieza != null ? pieza.toString() : nombre;
        return pieza != null ? pieza.toString() : "  ";
    }

    @Override
    public Casilla clone() {
        Casilla t = new Casilla(nombre, columna, fila);
        if (pieza != null) {
            t.setPieza(pieza.clone());
        }
        return t;

    }
}
