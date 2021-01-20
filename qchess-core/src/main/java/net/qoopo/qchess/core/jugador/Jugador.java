/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.jugador;

import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.core.Pieza;
import net.qoopo.qchess.core.tablero.Tablero;

/**
 *
 * @author alberto
 */
public abstract class Jugador {

    /**
     * Nombre del Jugador
     */
    protected String nombre;

    /**
     * Color del jugador
     */
    protected int color;
    /**
     * Indica si el jugador enroco
     */
    protected boolean enroque;

    public abstract Movimiento mejorMovimiento(Tablero tablero);
    
    /**
     * Indica si el jugador es humano
     * @return 
     */
    public abstract boolean isHumano();

    public Jugador() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isEnroque() {
        return enroque;
    }

    public void setEnroque(boolean enroque) {
        this.enroque = enroque;
    }

    public String getColorNombre() {
        return color == Pieza.BLANCA ? "Blancos" : "Negros";
    }
    
    public abstract Jugador clone();
}
