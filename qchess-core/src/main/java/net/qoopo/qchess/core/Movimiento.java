/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

import net.qoopo.qchess.core.piezas.Rey;
import net.qoopo.qchess.core.tablero.Casilla;

/**
 *
 * @author alberto
 */
public class Movimiento {

    public static Movimiento get(Casilla origen, Casilla destino) {
        return new Movimiento(origen, destino);
    }

//    public static Movimiento get(Pieza pieza, Casilla origen, Casilla destino) {
//        return new Movimiento(pieza, origen, destino);
//    }
    private Pieza pieza;
    private Casilla origen;
    private Casilla destino;
    private Integer valor; //score
    private Pieza piezaCapturada;

    public Movimiento() {
    }

//    public Movimiento(Pieza pieza, Casilla origen, Casilla destino) {
//        this.pieza = pieza;
//        this.origen = origen;
//        this.destino = destino;
//    }
    private Movimiento(Casilla origen, Casilla destino) {
        this.pieza = origen.getPieza();
        this.origen = origen;
        this.destino = destino;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Casilla getOrigen() {
        return origen;
    }

    public void setOrigen(Casilla origen) {
        this.origen = origen;
    }

    public Casilla getDestino() {
        return destino;
    }

    public void setDestino(Casilla destino) {
        this.destino = destino;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public Pieza getPiezaCapturada() {
        return piezaCapturada;
    }

    public void setPiezaCapturada(Pieza piezaCapturada) {
        this.piezaCapturada = piezaCapturada;
    }

    @Override
    public String toString() {
        return "Movimiento{" + "pieza=" + pieza + ", origen=" + (origen != null ? origen.getNombre() : "null") + ", destino=" + (destino != null ? destino.getNombre() : "null") + ", piezaCapturada=" + piezaCapturada + '}';
    }

    public boolean esEnroque() {
        return getPieza() != null && getPieza() instanceof Rey && origen != null && destino != null && Math.abs(origen.getColumna() - destino.getColumna()) == 2;
    }

    /**
     * Devuelve el movimiento en notacion e2e4
     *
     * @return
     */
    public String getNotacion() {
        return this.getOrigen().getNombre() + this.getDestino().getNombre();
    }
}
