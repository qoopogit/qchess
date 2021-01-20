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
    protected final int fila, col; //desde 1 a 8

    /**
     * indica si es una casilla negra
     */
    private boolean negra;

    /**
     * Indica que esta casilla esta habilitada para comer al paso.
     */
    private boolean alPaso;

    public Casilla(String nombre, int columna, int fila, boolean negra) {
        this.nombre = nombre;
        this.fila = fila;
        this.col = columna;
        this.negra = negra;
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

//    public void setFila(int fila) {
//        this.fila = fila;
//    }

    public int getCol() {
        return col;
    }

//    public void setCol(int col) {
//        this.col = col;
//    }

    public boolean isAlPaso() {
        return alPaso;
    }

    public void setAlPaso(boolean alPaso) {
        this.alPaso = alPaso;
    }

    public boolean isNegra() {
        return negra;
    }

    public void setNegra(boolean negra) {
        this.negra = negra;
    }

    @Override
    public String toString() {
//        return "n=" + nombre + ", o=" + ocupada + ", p=" + pieza;
//        return pieza != null ? pieza.toString() : nombre;
//        return pieza != null ? pieza.toString() : (negra ? "  " : "XX");
        return pieza != null ? pieza.toString() : (negra ? "  " : "::");
//        return pieza != null ? pieza.toString() : (negra ? "  " : Character.toString(219)); //178,219
    }

    @Override
    public Casilla clone() {
        Casilla t = new Casilla(nombre, col, fila, negra);
        if (pieza != null) {
            t.setPieza(pieza.clone());
        }
        return t;

    }
}
