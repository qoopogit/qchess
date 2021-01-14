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
public class Peon extends Pieza {

    public static Peon nuevo(int color) {
        Peon pieza = new Peon();
        pieza.setColor(color);
        return pieza;
    }

    public Peon() {
        this.nombre = "P";
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

        switch (color) {
            case Pieza.BLANCA -> {
                // si es la misma columna
                if (movimiento.getDestino().getColumna() == movimiento.getOrigen().getColumna()) {
                    if (movimiento.getDestino().getFila() - movimiento.getOrigen().getFila() == 1) {
                        return true && !permiteJaque(tablero, movimiento, validarPermiteJaque);
                    }
                    //se permite avanzar dos casillas siempre y cuando sea el primer movimiento
                    if (movimiento.getDestino().getFila() - movimiento.getOrigen().getFila() == 2) {
                        if (movimiento.getOrigen().getFila() == 2) {
                            return true && !permiteJaque(tablero, movimiento, validarPermiteJaque);
                        }
                    }
                } else if (Math.abs(movimiento.getDestino().getColumna() - movimiento.getOrigen().getColumna()) == 1) {
                    //avanza a una casilla de una columna conjunta. Solo se permite un avance de una casilla. Solo se permite para capturar otra pieza. Debe existir una pieza en la casilla o debe estar habilitado Al Paso
                    if (movimiento.getDestino().getFila() - movimiento.getOrigen().getFila() == 1) {
                        //si la casilal destino tiene una pieza
                        if (movimiento.getDestino().isOcupada() && !permiteJaque(tablero, movimiento, validarPermiteJaque)) {
                            movimiento.setPiezaCapturada(movimiento.getDestino().getPieza());
                            return true;
                        } else if (movimiento.getDestino().isAlPaso() && !permiteJaque(tablero, movimiento, validarPermiteJaque)) {
                            movimiento.setPiezaCapturada(tablero.get(movimiento.getDestino().getColumna(), movimiento.getOrigen().getFila()).getPieza());
                            return true;
                        }
                    }
                }
            }

            case Pieza.NEGRA -> {
                // si es la misma columna
                if (movimiento.getDestino().getColumna() == movimiento.getOrigen().getColumna()) {
                    if (movimiento.getDestino().getFila() - movimiento.getOrigen().getFila() == -1) {
                        return true && !permiteJaque(tablero, movimiento, validarPermiteJaque);
                    }
                    //se permite avanzar dos casillas siempre y cuando sea el primer movimiento
                    if (movimiento.getDestino().getFila() - movimiento.getOrigen().getFila() == -2) {
                        if (movimiento.getOrigen().getFila() == 7) {
                            return true && !permiteJaque(tablero, movimiento, validarPermiteJaque);
                        }
                    }
                } else if (Math.abs(movimiento.getDestino().getColumna() - movimiento.getOrigen().getColumna()) == 1) {
                    //avanza a una casilla de una columna conjunta. Solo se permite un avance de una casilla. Solo se permite para capturar otra pieza. Debe existir una pieza en la casilla o debe estar habilitado Al Paso
                    if (movimiento.getDestino().getFila() - movimiento.getOrigen().getFila() == -1) {
                        //si la casilal destino tiene una pieza
                        if (movimiento.getDestino().isOcupada() && !permiteJaque(tablero, movimiento, validarPermiteJaque)) {
                            movimiento.setPiezaCapturada(movimiento.getDestino().getPieza());
                            return true;
                        } else if (movimiento.getDestino().isAlPaso() && !permiteJaque(tablero, movimiento, validarPermiteJaque)) {
                            movimiento.setPiezaCapturada(tablero.get(movimiento.getDestino().getColumna(), movimiento.getOrigen().getFila()).getPieza());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Movimiento> getMovimientosValidos(Tablero tablero, Casilla casilla, boolean validarPermiteJaque) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        // un Peon solo se puede mover hacia adelante, una casilla, dos si es la primera vez, o en diagonal si es para comer
        int saltos = 0;
        switch (color) {
            case Pieza.BLANCA -> {
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna(), casilla.getFila() + 1)));
                if (!movida) {
                    lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna(), casilla.getFila() + 2)));
                }
                //movimientos para atacar
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 1, casilla.getFila() + 1)));
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 1, casilla.getFila() - 1)));
            }
            case Pieza.NEGRA -> {
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna(), casilla.getFila() - 1)));
                if (!movida) {
                    lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna(), casilla.getFila() - 2)));
                }
                //movimientos para atacar
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() + 1, casilla.getFila() - 1)));
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getColumna() - 1, casilla.getFila() - 1)));
            }
        }

        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, validarPermiteJaque))).forEachOrdered(mov -> {
            lista.add(mov);
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Peon();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setMovida(movida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 100;
    }

}
