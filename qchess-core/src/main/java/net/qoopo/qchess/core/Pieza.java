/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

import java.util.List;
import net.qoopo.qchess.core.tablero.Casilla;

/**
 * Representa una pieza en el tablero
 *
 * @author alberto
 */
public abstract class Pieza {

    public static final int BLANCA = 1;
    public static final int NEGRA = -1;

    //Nombre
    protected String nombre;

    // Numero de piezas que amenazan estas pieza
    protected int amenazas;
    // Numero de piezas que protegen esta pieza
    protected int protectores;
    // Numero de piezas que podemos atacar
    protected int objetivos;
    // numero de jugadas que podemos realizar para esta pieza
    protected int jugadas;

    // color
    protected int color;

//    private Tablero tablero;
    // Indica si la pieza ya fue movida
    protected boolean movida = false;

    /**
     * Realiza una evaluacion de esta pieza. Calcula el numero de piezas que nos
     * atacan, el numero de piezas que nos protegen
     */
//    public abstract void evaluar();

    public static String getColorNombre(int color) {
        return (color == Pieza.BLANCA ? "Blancas" : "Negras");
    }

    @Override
    public String toString() {
        return nombre + (color == BLANCA ? "b" : "n");
    }

    public String getNombre() {
        return nombre;
    }

    public Pieza setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public int getAmenazas() {
        return amenazas;
    }

    public Pieza setAmenazas(int amenazas) {
        this.amenazas = amenazas;
        return this;
    }

    public int getProtectores() {
        return protectores;
    }

    public Pieza setProtectores(int protectores) {
        this.protectores = protectores;
        return this;
    }

    public int getObjetivos() {
        return objetivos;
    }

    public Pieza setObjetivos(int objetivos) {
        this.objetivos = objetivos;
        return this;
    }

    public int getJugadas() {
        return jugadas;
    }

    public void setJugadas(int jugadas) {
        this.jugadas = jugadas;
    }

    public int getColor() {
        return color;
    }

    public Pieza setColor(int color) {
        this.color = color;
        return this;
    }

    public boolean isMovida() {
        return movida;
    }

    public void setMovida(boolean movida) {
        this.movida = movida;
    }

    /**
     * Valida si el movimiento sugerido es valido dentro de la regla de cada
     * pieza.No valida si la casilla destino esta ocupada o no.
     *
     * @param tablero
     * @param movimiento
     * @param validarPermiteJaque (Realiza un calculo del tablero para este
     * movimiento)
     * @return
     */
    public abstract boolean esMovimientoValido(Tablero tablero, Movimiento movimiento, boolean validarPermiteJaque);

    /**
     * Devuelve una lista de movimientos validos para el tablero indicado
     *
     * @param tablero El tablero donde se calcula los movimientos
     * @param casilla la casilla donde se encuentra la pieza
     * @param validarPermiteJaque
     * @return
     */
    public abstract List<Movimiento> getMovimientosValidos(Tablero tablero, Casilla casilla, boolean validarPermiteJaque);

    public void limpiarValores() {
        this.amenazas = this.objetivos = this.protectores = this.jugadas = 0;
    }

    /**
     * Valida si el movimiento permite un Jaque del rey.
     *
     * @param tablero
     * @param movimiento
     * @param validarPermiteJaque. si es FALSE, no realiza la validacion. Se usa
     * para no volver a calcular si permite un Jaque para los movimientos
     * despues de este.
     * @return
     */
    protected boolean permiteJaque(Tablero tablero, Movimiento movimiento, boolean validarPermiteJaque) {
//        return false;
        if (!validarPermiteJaque) {
            return false;
        }
        Tablero t = tablero.clone();
        t.setColorTurno(movimiento.getPieza().getColor());
        t.mover(Movimiento.get(t.get(movimiento.getOrigen().getNombre()), t.get(movimiento.getDestino().getNombre())));
        t.calcular(false);// calcula la posicion depues del movimiento sin tomar en validar nuevamente si los movimientos posibles despues de este permite jaque
        return t.isJaque();
    }

    /**
     * Devuelve el valor de la pieza
     *
     * @return
     */
    public abstract int getValor();

    public abstract Pieza clone();
}
