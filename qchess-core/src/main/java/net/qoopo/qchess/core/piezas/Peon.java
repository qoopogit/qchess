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
public class Peon extends Pieza {

    public static Peon nuevo(int color) {
        Peon pieza = new Peon();
        pieza.setColor(color);
        return pieza;
    }

    public Peon() {
        this.nombre = "P";
        this.simbolo = "♟";
    }

    @Override
    public boolean esMovimientoValido(Tablero tablero, Movimiento movimiento, boolean movLegales) {
        if (!esMovimientoValido(movimiento, tablero)) {
            return false;
        }

        Casilla origen = movimiento.getOrigen(tablero);
        Casilla destino = movimiento.getDestino(tablero);

        switch (color) {
            case Pieza.BLANCA -> {
                // si es la misma columna
                if (destino.getCol() == origen.getCol()) {
                    if (destino.getFila() - origen.getFila() == 1 && !destino.isOcupada()) {
                        return true && !permiteJaque(tablero, movimiento, movLegales);
                    }
                    //se permite avanzar dos casillas siempre y cuando sea el primer movimiento
                    if (destino.getFila() - origen.getFila() == 2 && !destino.isOcupada() && !tablero.estaObstruido(movimiento.getOrigen(), movimiento.getDestino())) {
                        if (origen.getFila() == 2) {
                            return true && !permiteJaque(tablero, movimiento, movLegales);
                        }
                    }
                } else if (Math.abs(destino.getCol() - origen.getCol()) == 1) {
                    //avanza a una casilla de una columna conjunta. Solo se permite un avance de una casilla. Solo se permite para capturar otra pieza. Debe existir una pieza en la casilla o debe estar habilitado Al Paso
                    if (destino.getFila() - origen.getFila() == 1) {
                        //si la casilal destino tiene una pieza
                        if (destino.isOcupada() && !permiteJaque(tablero, movimiento, movLegales)) {
                            movimiento.setPiezaCapturada(destino.getPieza().getNombre());
                            return true;
                        } else if (destino.isAlPaso() && !permiteJaque(tablero, movimiento, movLegales)) {
                            movimiento.setPiezaCapturada(tablero.get(destino.getCol(), origen.getFila()).getPieza().getNombre());
                            return true;
                        }
                    }
                }
            }

            case Pieza.NEGRA -> {
                // si es la misma columna
                if (destino.getCol() == origen.getCol() && !destino.isOcupada()) {
                    if (destino.getFila() - origen.getFila() == -1) {
                        return true && !permiteJaque(tablero, movimiento, movLegales);
                    }
                    //se permite avanzar dos casillas siempre y cuando sea el primer movimiento
                    if (destino.getFila() - origen.getFila() == -2 && !destino.isOcupada() && !tablero.estaObstruido(movimiento.getOrigen(), movimiento.getDestino())) {
                        if (origen.getFila() == 7) {
                            return true && !permiteJaque(tablero, movimiento, movLegales);
                        }
                    }
                } else if (Math.abs(destino.getCol() - origen.getCol()) == 1) {
                    //avanza a una casilla de una columna conjunta. Solo se permite un avance de una casilla. Solo se permite para capturar otra pieza. Debe existir una pieza en la casilla o debe estar habilitado Al Paso
                    if (destino.getFila() - origen.getFila() == -1) {
                        //si la casilal destino tiene una pieza
                        if (destino.isOcupada() && !permiteJaque(tablero, movimiento, movLegales)) {
                            movimiento.setPiezaCapturada(destino.getPieza().getNombre());
                            return true;
                        } else if (destino.isAlPaso() && !permiteJaque(tablero, movimiento, movLegales)) {
                            movimiento.setPiezaCapturada(tablero.get(destino.getCol(), origen.getFila()).getPieza().getNombre());
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<Movimiento> getMovimientos(Tablero tablero, Casilla casilla, boolean movLegales) {
        List<Movimiento> lstTmp = new ArrayList<>();
        List<Movimiento> lista = new ArrayList<>();
        // un Peon solo se puede mover hacia adelante, una casilla, dos si es la primera vez, o en diagonal si es para comer
        switch (color) {
            case Pieza.BLANCA -> {
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() + 1)));
//                if (!this.isMovida()) {
                if (casilla.getFila() == 2) {
                    lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() + 2)));
                }
                //movimientos para atacar
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila() + 1)));
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila() + 1)));

            }
            case Pieza.NEGRA -> {
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() - 1)));
//                if (!this.isMovida()) {
                if (casilla.getFila() == 7) {
                    lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol(), casilla.getFila() - 2)));
                }
                //movimientos para atacar
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() + 1, casilla.getFila() - 1)));
                lstTmp.add(Movimiento.get(casilla, tablero.get(casilla.getCol() - 1, casilla.getFila() - 1)));
            }
        }

        lstTmp.stream().filter(mov -> (esMovimientoValido(tablero, mov, movLegales))).forEachOrdered(mov -> {

            // si los movimientos son promocion, agrego uno por cada pieza que puedo reclamar
            if (mov.isPromocion(tablero, this)) {
                int _color = mov.getPieza(tablero).getColor();
                Movimiento mov1 = mov.clone();
                mov1.setPiezaPromocion(Dama.nuevo(_color)); //Dama
                Movimiento mov2 = mov.clone();
                mov2.setPiezaPromocion(Torre.nuevo(_color)); //Torre
                Movimiento mov3 = mov.clone();
                mov3.setPiezaPromocion(Alfil.nuevo(_color)); //Alfil
                Movimiento mov4 = mov.clone();
                mov4.setPiezaPromocion(Caballo.nuevo(_color)); //Caballo
                lista.add(mov1);
                lista.add(mov2);
                lista.add(mov3);
                lista.add(mov4);
            } else {
                lista.add(mov);
            }
        });
        return lista;
    }

    @Override
    public Pieza clone() {
        Pieza t = new Peon();
        t.setAmenazas(amenazas);
        t.setColor(color);
        t.setVecesMovida(vecesMovida);
        t.setObjetivos(objetivos);
        t.setProtectores(protectores);
        return t;
    }

    @Override
    public int getValor() {
        return 100;
    }

}
