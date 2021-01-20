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
public class Rey extends Pieza {

    public static Rey nuevo(int color) {
        Rey pieza = new Rey();
        pieza.setColor(color);
        return pieza;
    }

    public Rey() {
        this.nombre = "R";
        this.simbolo = "♔";
    }

    @Override
    public boolean esMovimientoValido(Tablero tablero, Movimiento movimiento, boolean movLegales) {
        if (!esMovimientoValido(movimiento, tablero)) {
            return false;
        }
        int difFilas = Math.abs(movimiento.getDestino().row - movimiento.getOrigen().row);
        int difCols = Math.abs(movimiento.getDestino().col - movimiento.getOrigen().col);

        return (difFilas <= 1 && difCols <= 1
                || (difCols == 2 && difFilas == 0 && !isMovida()
                && !tablero.estaObstruido(movimiento.getOrigen(), movimiento.getDestino())))
                && !permiteJaque(tablero, movimiento, movLegales); //movimiento normal o enroque
    }

    @Override
    public List<Movimiento> getMovimientos(Tablero tablero, Casilla casilla, boolean movLegales) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() - 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila() - 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila())));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila() + 1)));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila())));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila() - 1)));
        //enroques
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 2, casilla.getFila())));
        lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 2, casilla.getFila())));

        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, movLegales))).forEachOrdered(mov -> {
            lista.add(mov);
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Rey();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setVecesMovida(vecesMovida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 20000;
    }
}
