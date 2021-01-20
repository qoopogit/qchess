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
import net.qoopo.qchess.core.tablero.Tablero;
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
        this.simbolo = "♕";
    }

    @Override
    public boolean esMovimientoValido(Tablero tablero, Movimiento movimiento, boolean movLegales) {
        if (!esMovimientoValido(movimiento, tablero)) {
            return false;
        }
        int difFilas = Math.abs(movimiento.getDestino().row - movimiento.getOrigen().row);
        int difCols = Math.abs(movimiento.getDestino().col - movimiento.getOrigen().col);

        return (((difFilas == difCols) || (difFilas > 0 && difCols == 0 || difCols > 0 && difFilas == 0))
                && !tablero.estaObstruido(movimiento.getOrigen(), movimiento.getDestino()))
                && !permiteJaque(tablero, movimiento, movLegales);
    }

    @Override
    public List<Movimiento> getMovimientos(Tablero tablero, Casilla casilla, boolean movLegales) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        // son dos diagonales (Alfil)
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + dif, casilla.getFila() + dif)));
        }
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + dif, casilla.getFila() + dif * -1)));
        }

        // son dos lineas, vertical y horizontal (Torre)
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() + dif)));
        }
        for (int dif = -8; dif <= 8; dif++) {
            lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + dif, casilla.getFila())));
        }

        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, movLegales))).forEachOrdered(mov -> {
            lista.add(mov);
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Dama();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setVecesMovida(vecesMovida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 900;
    }
}
