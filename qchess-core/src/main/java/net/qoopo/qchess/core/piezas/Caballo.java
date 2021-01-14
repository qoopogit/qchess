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
public class Caballo extends Pieza {

    public static Caballo nuevo(int color) {
        Caballo pieza = new Caballo();
        pieza.setColor(color);
        return pieza;
    }

    public Caballo() {
        this.nombre = "C";
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

        return (difFilas == 2 && difColumnas == 1 || difColumnas == 2 && difFilas == 1) && !permiteJaque(tablero, movimiento, validarPermiteJaque);
    }

    @Override
    public List<Movimiento> getMovimientosValidos(Tablero tablero, Casilla casilla, boolean validarPermiteJaque) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 2, casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 2, casilla.getFila() - 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 1, casilla.getFila() + 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 1, casilla.getFila() - 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() - 1, casilla.getFila() + 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() - 1, casilla.getFila() - 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() - 2, casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() - 2, casilla.getFila() - 1)));

        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, validarPermiteJaque))).forEachOrdered(mov -> {
            lista.add(mov);
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Caballo();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setMovida(movida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 300;
    }
}
