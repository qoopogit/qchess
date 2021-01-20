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
import net.qoopo.qchess.core.tablero.Casilla;
import net.qoopo.qchess.core.tablero.Tablero;

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
        this.simbolo = "♘";
    }

    @Override
    public boolean esMovimientoValido(Tablero tablero, Movimiento movimiento, boolean movLegales) {
        if (!esMovimientoValido(movimiento, tablero)) {
            return false;
        }

        int difFilas = Math.abs(movimiento.getDestino().row-movimiento.getOrigen().row);
        int difCols = Math.abs(movimiento.getDestino().col-movimiento.getOrigen().col);
        return (difFilas == 2 && difCols == 1 || difCols == 2 && difFilas == 1)
                && !permiteJaque(tablero, movimiento, movLegales);
    }

    @Override
    public List<Movimiento> getMovimientos(Tablero tablero, Casilla casilla, boolean movLegales) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 2, casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 2, casilla.getFila() - 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila() + 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila() - 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila() + 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila() - 2)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 2, casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 2, casilla.getFila() - 1)));
        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, movLegales))).forEachOrdered(mov -> {
            lista.add(mov);
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Caballo();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setVecesMovida(vecesMovida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 300;
    }
}
