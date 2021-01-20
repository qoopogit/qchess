/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.engine;

import java.util.ArrayList;
import java.util.List;
import net.qoopo.qchess.core.LineaPV;
import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.core.tablero.Tablero;
import net.qoopo.qchess.core.jugador.Jugador;
import net.qoopo.qchess.core.util.CalculoTime;
import net.qoopo.qchess.core.util.NPS;
import net.qoopo.qchess.core.util.TimeUtil;

/**
 * Jugador automatico donde vemos los movimientos posibles y realizamos los
 * movimentos en un tablero virtual para calcular
 *
 * @author alberto
 */
public class QChess1 extends Jugador {

    private int depth = 5;

    public QChess1() {
    }

    public QChess1(String nombre) {
        this.nombre = nombre;
    }

    public QChess1(String nombre, int profundidad) {
        this.nombre = nombre;
        this.depth = profundidad;
    }

    public QChess1(String nombre, int color, int profundidad) {
        this.nombre = nombre;
        this.color = color;
        this.depth = profundidad;
    }

    public QChess1(int profundidad) {
        this.depth = color;
    }

    public QChess1(int color, int profundidad) {
        this.color = color;
        this.depth = profundidad;
    }

    @Override
    public Movimiento mejorMovimiento(Tablero tablero) {
        Movimiento mejorMovimiento = null;
        long tInicio = System.currentTimeMillis();
        System.out.println(getNombre() + " [" + getColorNombre() + "] Calculando mejor movimiento.Analizando " + tablero.getMovimientoValidos().size() + " movimientos validos ");
        int mejorScore = Integer.MIN_VALUE;
        LineaPV mejorPV = new LineaPV();
        tablero.setMovimientosLegales(false);

        int _colorTurno = tablero.getColorTurno();
        int _scoreMaterial = tablero.getScoreMaterial();
        int _scoreMovimientos = tablero.getScoreMovimientos();

        final List<Movimiento> _lstMovimientos = new ArrayList<>(tablero.getMovimientoValidos());
        for (Movimiento mov : _lstMovimientos) {
            //Se realiza el movimiento
            LineaPV pv = new LineaPV();
            if (tablero.mover(mov).isSatisfactorio()) {
                tablero.finTurno();
                if (!tablero.isJaque(tablero.getColorTurnoOponente())) {
                    pv.agregar(mov.getNotacion());
                    NPS.iniciar();
                    CalculoTime.reset();
                    long tIniPV = System.currentTimeMillis();
                    int score = -alphaBetaNegaMax(tablero, pv, depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE - 1);
                    NPS.fin();
                    System.out.println("score:" + score + " " + pv.toString() + " NPS:" + NPS.getNPS() + "     T:" + TimeUtil.getTime(tIniPV) + " TC:" + TimeUtil.getTimeFormater(CalculoTime.total) + " TPC:" + TimeUtil.getTimeFormater(CalculoTime.getTime()) + " VC:" + CalculoTime.cuantos);
                    if (score > mejorScore) {
                        mejorScore = score;
                        mejorMovimiento = mov;
                        mejorPV = pv;
                    }
                }
                if (!tablero.desMover(mov)) {
                    System.out.println("ERROR: Error al deshacer el movimiento " + mov);
                }

                tablero.setColorTurno(_colorTurno);
                tablero.setScoreMaterial(_scoreMaterial);
                tablero.setScoreMovimientos(_scoreMovimientos);

            } else {
                System.out.println("ERROR: Al realizar el movimiento " + mov);
            }
        }
        if (mejorMovimiento != null) {
            System.out.println("Mejor score[" + mejorScore + "] mejor movimiento [" + mejorMovimiento.getNotacion() + "] PV:[" + mejorPV.toString() + "] T [" + TimeUtil.getTime(tInicio) + "] TC [" + TimeUtil.getTimeFormater(CalculoTime.getTime()) + "]");
        } else {
            System.out.println("NO SE ENCONTRO MEJOR MOVIMIENTO !!!!!");
        }
        tablero.setMovimientosLegales(true);
        //buscamos los mejores movimientos y lo realizamos
        return mejorMovimiento;
    }

    /**
     * Algoritmo recrusivo para clacular el mejor movimiento
     *
     * @param tablero
     * @param pv
     * @param depth
     * @param alpha
     * @param beta
     * @return
     */
    private int alphaBetaNegaMax(Tablero tablero, LineaPV pv, int depth, int alpha, int beta) {
        NPS.agregar();
//        System.out.println("Profundidad=" + depth);
        // si el jugador enemigo esta en jaque mate, deberia ser el maximo valor, como el resultado lo multiplicamos por -1, devolvemos el varlo minimo posible
        if (tablero.isJaqueMate()) {
            return (Integer.MIN_VALUE + 1 + this.depth - depth);
//        } else if (tablero.isJaque()) {
//            return (Integer.MIN_VALUE + 1 + this.depth - depth);
        } else if (tablero.isAhogado()) {
            return 0;// tablas
        } else if (depth <= 0) {
            //Devuelve el valor del tablero. El valor del tablero es 0 cuando estan empatados, positivo cuando los blancos llevan ventaja, negativo cuando lso negros llevan ventaja
            return (tablero.getScoreMaterial() + tablero.getScoreMovimientos());
            // Como queremos el mayor valor de la jugada, multiplicamos por el color del turno (1 o -1) para obtener un valor positivo siempre            
//            return (tablero.getScore() * tablero.getColorTurno());
        }
        //calculamos las siguientes jugadas posibles para obtener el valor de esta jugada
        //recorremos los movimientos posibles del actual jugador (en las llamadas impares serian del oponente (1,3,5,)))
        LineaPV mejorPV = new LineaPV();

        int _colorTurno = tablero.getColorTurno();
        int _scoreMaterial = tablero.getScoreMaterial();
        int _scoreMovimientos = tablero.getScoreMovimientos();

        final List<Movimiento> _lstMovimientos = new ArrayList<>(tablero.getMovimientoValidos());

        for (Movimiento mov : _lstMovimientos) {
            //realiza el movimiento
            LineaPV _pv = new LineaPV();
            if (tablero.mover(mov).isSatisfactorio()) {
                tablero.finTurno();
                if (!tablero.isJaque(tablero.getColorTurnoOponente())) {
                    _pv.agregar(mov.getNotacion());
                    // calcula la mejor calificacion que va a tener el tablero en los siguientes movimientos posibles
                    int score = -alphaBetaNegaMax(tablero, _pv, depth - 1, -beta, -alpha);
                    if (score >= beta) {
                        pv.agregar(mov.getNotacion());
                        if (!tablero.desMover(mov)) {
                            System.out.println("ERROR: Error al deshacer el movimiento " + mov);
                        }
                        return score;
                    }
                    if (score > alpha) {
                        alpha = score;
                        mejorPV = _pv;
                    }
                }
                if (!tablero.desMover(mov)) {
                    System.out.println("ERROR: Error al deshacer el movimiento " + mov);
                }

                tablero.setColorTurno(_colorTurno);
                tablero.setScoreMaterial(_scoreMaterial);
                tablero.setScoreMovimientos(_scoreMovimientos);

//            if (score > mejorScore) {
//                mejorScore = score;
//                if (score > alpha) {
//                    alpha = score;
//                }
//            }
            } else {
                System.out.println("ERRO: Al realizar el movimiento " + mov);
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

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public Jugador clone() {
        return new QChess1(nombre, color, depth);
    }

}
