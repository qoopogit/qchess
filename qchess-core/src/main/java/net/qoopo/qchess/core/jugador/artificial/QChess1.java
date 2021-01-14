/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.jugador.artificial;

import net.qoopo.qchess.core.LineaPV;
import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.core.Tablero;
import net.qoopo.qchess.core.jugador.Jugador;
import net.qoopo.qchess.core.util.NPS;
import net.qoopo.qchess.core.util.TimeUtil;

/**
 * Jugador automatico donde vemos los movimientos posibles y realizamos los
 * movimentos en un tablero virtual para calcular
 *
 * @author alberto
 */
public class QChess1 extends Jugador {

    private int profundidad = 3;

    public QChess1() {
    }

    public QChess1(String nombre) {
        this.nombre = nombre;
    }

    public QChess1(String nombre, int color) {
        this.nombre = nombre;
        this.color = color;
    }

    public QChess1(String nombre, int color, int profundidad) {
        this.nombre = nombre;
        this.color = color;
        this.profundidad = profundidad;
    }

    public QChess1(int color) {
        this.color = color;
    }

    public QChess1(int color, int profundidad) {
        this.color = color;
        this.profundidad = profundidad;
    }

    @Override
    public Movimiento mejorMovimiento(Tablero tablero) {
        Movimiento mejorMovimiento = null;
        long tInicio = System.currentTimeMillis();
        System.out.println(getNombre() + " [" + getColorNombre() + "] Calculando mejor movimiento.Analizando " + tablero.getMovimientoValidos().size() + " movimientos validos ");
        int mejorScore = Integer.MIN_VALUE;
        int cont = 0;
//        int total = tablero.getMovimientoValidos().size();
        LineaPV mejorPV = new LineaPV();
        for (Movimiento mov : tablero.getMovimientoValidos()) {
            cont++;
//            System.out.println("Se analiza el movimiento " + mov + "[" + cont + "/" + total + " " + (cont * 100 / total) + "%]");
            //Se realiza el movimiento
            LineaPV pv = new LineaPV();
            Tablero tVirtual = tablero.clone();
            if (tVirtual.mover(mov.getNotacion())) {
                tVirtual.finTurno();
                pv.agregar(mov.getNotacion());
                // calcula la mejor calificacion que va a tener el tablero en los siguientes movimientos posibles
                NPS.iniciar();
                long tIniPV = System.currentTimeMillis();
                int score = -alphaBetaNegaMax(tVirtual, pv, profundidad, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
                NPS.fin();
                System.out.println("score:" + score + " " + pv.toString() + " NPS:" + NPS.getNPS() + " T:" + TimeUtil.getTime(tIniPV));
                if (score > mejorScore) {
                    mejorScore = score;
                    mejorMovimiento = mov;
                    mejorPV = pv;
                }
            }
        }
        if (mejorMovimiento != null) {
            System.out.println("Mejor score[" + mejorScore + "] mejor movimiento [" + mejorMovimiento.getNotacion() + "] PV:[" + mejorPV.toString() + "] T [" + TimeUtil.getTime(tInicio) + "]");
        } else {
            System.out.println("NO SE ENCONTRO MEJOR MOVIMIENTO !!!!!");
        }
        //buscamos los mejores movimientos y lo realizamos
        return mejorMovimiento;
    }

    /**
     * Algoritmo recrusivo para clacular el mejor movimiento
     *
     * @param tablero
     * @param pv
     * @param profundidad
     * @param alpha
     * @param beta
     * @return
     */
    private int alphaBetaNegaMax(Tablero tablero, LineaPV pv, int profundidad, int alpha, int beta) {
        NPS.agregar();
//        System.out.println("Profundidad=" + profundidad);
        // si el jugador enemigo esta en jaque mate, deberia ser el maximo valor, como el resultado lo multiplicamos por -1, devolvemos el varlo minimo posible
        if (tablero.isJaqueMate()) {
            return (Integer.MIN_VALUE + 1 + this.profundidad - profundidad);
        } else if (tablero.isAhogado()) {
            return 0;// tablas
        } else if (profundidad <= 0) {
            //Devuelve el valor del tablero. El valor del tablero es 0 cuando estan empatados, positivo cuando los blancos llevan ventaja, negativo cuando lso negros llevan ventaja
            return (tablero.getScore());
            // Como queremos el mayor valor de la jugada, multiplicamos por el color del turno (1 o -1) para obtener un valor positivo siempre            
//            return (tablero.getScore() * tablero.getColorTurno());
        }
        //calculamos las siguientes jugadas posibles para obtener el valor de esta jugada
        //recorremos los movimientos posibles del actual jugador (en las llamadas impares serian del oponente (1,3,5,)))
        LineaPV mejorPV = new LineaPV();
        for (Movimiento mov : tablero.getMovimientoValidos()) {
            //realiza el movimiento
            LineaPV _pv = new LineaPV();
            Tablero tVirtual = tablero.clone();
            if (tVirtual.mover(mov.getNotacion())) {
                tVirtual.finTurno();
                _pv.agregar(mov.getNotacion());
                // calcula la mejor calificacion que va a tener el tablero en los siguientes movimientos posibles
                int score = -alphaBetaNegaMax(tVirtual, _pv, profundidad - 1, -beta, -alpha);
                if (score >= beta) {
                    pv.agregar(mov.getNotacion());
                    return score;
                }
                if (score > alpha) {
                    alpha = score;
                    mejorPV = _pv;
                }
//            if (score > mejorScore) {
//                mejorScore = score;
//                if (score > alpha) {
//                    alpha = score;
//                }
//            }
            }
        }
        pv.agregar(mejorPV);
        return alpha;
//        return mejorScore;
    }

    @Override
    public boolean isHumano() {
        return false;
    }

    public int getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(int profundidad) {
        this.profundidad = profundidad;
    }

    @Override
    public Jugador clone() {
        return new QChess1(nombre, color, profundidad);
    }

}
