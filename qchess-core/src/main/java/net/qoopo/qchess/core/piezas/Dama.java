/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.piezas;

import java.util.ArrayList;
import java.util.List;
import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.core.Pieza;
import net.qoopo.qchess.core.Tablero;
import net.qoopo.qchess.core.tablero.Casilla;

/**
 *
 * @author alberto
 */
public class Dama extends Pieza {

    public static Dama nuevo(int color) {
        Dama pieza = new Dama();
        pieza.setColor(color);
        return pieza;
    }

    public Dama() {
        this.nombre = "D";
    }

    @Override
    public boolean esMovimientoValido(Tablero tablero, Movimiento movimiento, boolean validarPermiteJaque) {
        if (movimiento.getDestino() == null) {
            return false;
        }
        //si el destino esta ocupado con una pieza del mismo color, el movimiento no es valido
        if (movimiento.getDestino().isOcupada() && movimiento.getDestino().getPieza().getColor() == color) {
            return false;
        }

        int difFilas = Math.abs(movimiento.getDestino().getFila() - movimiento.getOrigen().getFila());
        int difColumnas = Math.abs(movimiento.getDestino().getColumna() - movimiento.getOrigen().getColumna());

        return (((difFilas == difColumnas) || (difFilas > 0 && difColumnas == 0 || difColumnas > 0 && difFilas == 0))
                && !tablero.estaObstruido(movimiento.getOrigen(), movimiento.getDestino()))
                && !permiteJaque(tablero, movimiento, validarPermiteJaque);
    }

    @Override
    public List<Movimiento> getMovimientosValidos(Tablero tablero, Casilla casilla, boolean validarPermiteJaque) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        // son dos diagonales (Alfil)
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + dif, casilla.getFila() + dif)));
        }
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + dif, casilla.getFila() + dif * -1)));
        }

        // son dos lineas, vertical y horizontal (Torre)
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna(), casilla.getFila() + dif)));
        }
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + dif, casilla.getFila())));
        }

        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, validarPermiteJaque))).forEachOrdered(mov -> {
            lista.add(mov);
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Dama();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setMovida(movida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 900;
    }
}
