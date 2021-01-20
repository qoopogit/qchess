/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

import net.qoopo.qchess.core.piezas.Peon;
import net.qoopo.qchess.core.piezas.Rey;
import net.qoopo.qchess.core.tablero.Casilla;
import net.qoopo.qchess.core.tablero.Tablero;

/**
 * Representa un movimiento en el motor
 *
 * @author alberto
 */
public class Movimiento {

    public static Movimiento get(Casilla origen, Casilla destino) {
        return new Movimiento(origen, destino);
    }

//    public static Movimiento get(String origen, String destino) {
//        return new Movimiento(origen, destino);
//    }
    public static Movimiento get(Casilla origen, Casilla destino, Pieza piezaPromocion) {
        return new Movimiento(origen, destino, piezaPromocion);
    }

//    public static Movimiento get(String origen, String destino, Pieza piezaPromocion) {
//        return new Movimiento(origen, destino, piezaPromocion);
//    }
    private Position origen = new Position();
    private Position destino = new Position();
    private Integer valor; //score
    private String piezaCapturada;
    // la pieza que se escoje para promocionar
    private Pieza piezaPromocion;
    // variables temporales para la validacion de enroque y promocion
    private boolean enroque, promocion;

    private String origenStr, destinoStr;

    public Movimiento() {
    }

    private Movimiento(Casilla origen, Casilla destino) {
        if (origen != null) {
            origenStr = origen.getNombre();
            this.origen.col = origen.getCol();
            this.origen.row = origen.getFila();
        }
        if (destino != null) {
            destinoStr = destino.getNombre();
            this.destino.col = destino.getCol();
            this.destino.row = destino.getFila();
        }

    }

    private Movimiento(Casilla origen, Casilla destino, Pieza piezaPromocion) {
        if (origen != null) {
            this.origen.col = origen.getCol();
            this.origen.row = origen.getFila();
        }
        if (destino != null) {
            this.destino.col = destino.getCol();
            this.destino.row = destino.getFila();
        }
        this.piezaPromocion = piezaPromocion;
    }

    public Position getOrigen() {
        return origen;
    }

    private void setOrigen(Position origen) {
        this.origen = origen;
    }

    public Position getDestino() {
        return destino;
    }

    private void setDestino(Position destino) {
        this.destino = destino;
    }

    public Pieza getPieza(Tablero tablero) {
        return tablero.get(origen).getPieza();
    }

    public Casilla getOrigen(Tablero tablero) {
        return tablero.get(origen);
    }

    public Casilla getDestino(Tablero tablero) {
        return tablero.get(destino);
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getPiezaCapturada() {
        return piezaCapturada;
    }

    public void setPiezaCapturada(String piezaCapturada) {
        this.piezaCapturada = piezaCapturada;
    }

    public Pieza getPiezaPromocion() {
        return piezaPromocion;
    }

    public void setPiezaPromocion(Pieza piezaPromocion) {
        this.piezaPromocion = piezaPromocion;
    }

    @Override
    public String toString() {
//        return "Movimiento{ origen=" + origen + ", destino=" + destino + ", piezaCapturada=" + piezaCapturada + '}';
        return "Movimiento{ origen=" + origenStr + ", destino=" + destinoStr + ", piezaCapturada=" + piezaCapturada + '}';
    }

    public boolean isEnroque(Tablero tablero, Pieza pieza) {
        if (!enroque) {
//            enroque = pieza != null && pieza instanceof Rey && origen != null && destino != null && Math.abs(tablero.get(origen).getCol() - tablero.get(destino).getCol()) == 2;
            enroque = pieza != null && pieza instanceof Rey && origen.isSet() && destino.isSet() && Math.abs(origen.col - destino.col) == 2;
        }
        return enroque;
    }

    public boolean isPromocion(Tablero tablero, Pieza pieza) {
        if (!promocion) {
//            promocion = pieza != null && pieza instanceof Peon && origen != null && destino != null && (pieza.getColor() == Pieza.BLANCA ? (tablero.get(destino).getFila() == 8) : (tablero.get(destino).getFila() == 1));
            promocion = pieza != null && pieza instanceof Peon && origen.isSet() && destino.isSet() && (pieza.getColor() == Pieza.BLANCA ? (destino.row == 8) : (destino.row == 1));
        }
        return promocion;
    }

    /**
     * Devuelve el movimiento en notacion e2e4
     *
     * @return
     */
    public String getNotacion() {
//        return this.getOrigen().getNombre() + this.getDestino().getNombre() + (isPromocion() ? (getPiezaPromocion() != null ? getPiezaPromocion().getNombre().toLowerCase() : "?") : "");
//        return this.getOrigen() + this.getDestino() + (isPromocion() ? (getPiezaPromocion() != null ? getPiezaPromocion().getNombre().toLowerCase() : "?") : "");
//        return this.getOrigen() + this.getDestino() + (getPiezaPromocion() != null ? getPiezaPromocion().getNombre().toLowerCase() : "");
        return this.origenStr + this.destinoStr + (getPiezaPromocion() != null ? getPiezaPromocion().getNombre().toLowerCase() : "");

    }

    public Movimiento clone() {
        Movimiento t = new Movimiento();
        t.setDestino(destino);
        t.setOrigen(origen);
        t.origenStr = origenStr;
        t.destinoStr = destinoStr;
        t.setPiezaCapturada(piezaCapturada);
        t.setPiezaPromocion(piezaPromocion);
        return t;
    }
}
