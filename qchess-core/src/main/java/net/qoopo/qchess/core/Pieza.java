/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

import java.util.List;
import net.qoopo.qchess.core.piezas.Alfil;
import net.qoopo.qchess.core.piezas.Caballo;
import net.qoopo.qchess.core.piezas.Dama;
import net.qoopo.qchess.core.piezas.Peon;
import net.qoopo.qchess.core.piezas.Rey;
import net.qoopo.qchess.core.piezas.Torre;
import net.qoopo.qchess.core.tablero.Casilla;
import net.qoopo.qchess.core.tablero.Tablero;

/**
 * Representa una pieza en el tablero
 *
 * @author alberto
 */
public abstract class Pieza {

    public static final int BLANCA = 1;
    public static final int NEGRA = -1;

    /**
     * Realiza una evaluacion de esta pieza. Calcula el numero de piezas que nos
     * atacan, el numero de piezas que nos protegen
     */
//    public abstract void evaluar();
    /**
     *
     * @param color
     * @return
     */
    public static String getColorNombre(int color) {
        return (color == Pieza.BLANCA ? "Blancas" : "Negras");
    }

    //Nombre
    protected String nombre;
    //Simbolo
    protected String simbolo;

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
    protected int vecesMovida = 0;

    @Override
    public String toString() {
//        return nombre + (color == BLANCA ? "b" : "n");
//        return simbolo + (color == BLANCA ? " " : "*");
//        return nombre + (color == BLANCA ? " " : "*");
        return " " + (color == BLANCA ? nombre : nombre.toLowerCase());
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
        return vecesMovida > 0;
    }

    public int getVecesMovida() {
        return vecesMovida;
    }

    public void setVecesMovida(int vecesMovida) {
        this.vecesMovida = vecesMovida;
    }

    public void aumentarMovs() {
        vecesMovida++;
    }

    public void restarMovs() {
        vecesMovida++;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
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
     * Validacion general para todas las piezas
     *
     * @param movimiento
     * @param tablero
     * @return
     */
    protected boolean esMovimientoValido(Movimiento movimiento, Tablero tablero) {

        //si el destino no ha sido configurado
        if (!movimiento.getDestino().isSet() || !movimiento.getOrigen().isSet()) {
            return false;
        }

        Casilla origen = movimiento.getOrigen(tablero);
        Casilla destino = movimiento.getDestino(tablero);
        if (origen == null || destino == null) {
            return false;
        }

        //si la casilla de origen y de destino son la misma, el movimiento no es valido
//        if (movimiento.getOrigen().equalsIgnoreCase(movimiento.getDestino())) {
        if (movimiento.getOrigen().equals(movimiento.getDestino())) {
            return false;
        }

        Pieza pieza = origen.getPieza();

        //si la pieza es nulo, el movimiento no es valido
        if (pieza == null) {
            return false;
        }

        //si el destino esta ocupado con una pieza del mismo color, el movimiento no es valido
        if (destino.isOcupada() && destino.getPieza().getColor() == color) {
            return false;
        }
        return true;
    }

    /**
     * Devuelve una lista de movimientos validos para el tablero indicado
     *
     * @param tablero El tablero donde se calcula los movimientos
     * @param casilla la casilla donde se encuentra la pieza
     * @param validos. Indica si obtiene solo movimientos validos
     * @return
     */
    public abstract List<Movimiento> getMovimientos(Tablero tablero, Casilla casilla, boolean validos);

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
        if (!validarPermiteJaque) {
            return false;
        }
        Tablero t = tablero.clone();
        t.setColorTurno(movimiento.getPieza(tablero).getColor());
        t.mover(movimiento);
//        t.mover(Movimiento.get(t.get(movimiento.getOrigen()), t.get(movimiento.getDestino())));
        t.calcular(false);// calcula la posicion depues del movimiento sin tomar en validar nuevamente si los movimientos posibles despues de este permite jaque
        return t.isJaque();
        //-----------
//        int antColor = tablero.getColorTurno();
//        tablero.setColorTurno(movimiento.getPieza(tablero).getColor());
//        tablero.mover(movimiento);
//        tablero.calcular(false);// calcula la posicion y movimientos pseudo-legales
//        boolean jaque = tablero.isJaque();
//        tablero.desMover(movimiento);
//        tablero.setColorTurno(antColor);
//        tablero.calcular(false);// calcula la posicion y movimientos pseudo-legales
//        return jaque;
    }

    /**
     * Devuelve el valor de la pieza
     *
     * @return
     */
    public abstract int getValor();

    public abstract Pieza clone();

    public void amenazasAdd() {
        amenazas++;
    }

    public void objetivosAdd() {
        objetivos++;
    }

    public void jugadasAdd() {
        jugadas++;
    }

    public void protectoresAdd() {
        protectores++;
    }

    public static Pieza get(String letra, int color) {
        if (letra == null) {
            return null;
        }
        switch (letra.toUpperCase()) {
            case "D" -> {
                return Dama.nuevo(color);
            }
            case "R" -> {
                return Rey.nuevo(color);
            }
            case "T" -> {
                return Torre.nuevo(color);
            }
            case "A" -> {
                return Alfil.nuevo(color);
            }
            case "C" -> {
                return Caballo.nuevo(color);
            }
            case "P" -> {
                return Peon.nuevo(color);
            }
        }
        return null;
    }
}
