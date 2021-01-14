/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.jugador;

import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.core.Tablero;

/**
 *
 * @author alberto
 */
public class Humano extends Jugador {

    public Humano() {
        this.setNombre("Humano");
    }

    public Humano(String nombre) {
        this.setNombre(nombre);
    }

    public Humano(String nombre, int color) {
        this.setNombre(nombre);
        this.setColor(color);
    }

    @Override
    public Movimiento mejorMovimiento(Tablero tablero) {
        return null;
    }

    @Override
    public boolean isHumano() {
        return true;
    }

    @Override
    public Jugador clone() {
        return new Humano(nombre, color);
    }

}
