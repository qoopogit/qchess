/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.engine.test;

import net.qoopo.qchess.core.Juego;
import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.engine.QChess1;

/**
 *
 * @author alberto
 */
public class Test {

    /**
     * Ejemplo de un juego
     *
     * @param args
     */
    public static void main(String[] args) {
        Juego juego = new Juego();
        juego.getTablero().setJugadorBlancas(new QChess1("Blancas", 2));
        juego.getTablero().setJugadorNegras(new QChess1("Negras ", 3));
//        juego.getTablero().cargarFEN("r1bqkbnr/ppp1n1pp/5p2/4N3/2BP4/2P5/PP3PPP/RNBQK2R w KQkq - 0 8"); //Mate en 1
//        juego.getTablero().cargarFEN("4r1k1/p5pp/1pp4r/1P1bnp2/5q2/P1R2P1P/1B2B1P1/4Q1RK b - - 0 1"); //mate en 11
//        juego.getTablero().calcular();
        juego.iniciar();
        System.out.println("Fin del juego, voy revisar movimientos posibles");
        System.out.println("Score=" + juego.getTablero().getScoreMaterial());
        System.out.println(juego.getTablero().getJugadorActual().getNombre() + " / " + juego.getTablero().getJugadorActual().getColorNombre() + " Juegos posibles=" + juego.getTablero().getMovimientoValidos().size());
        System.out.println(juego.getTablero().getJugadorOponente().getNombre() + " / " + juego.getTablero().getJugadorOponente().getColorNombre() + " Juegos posibles=" + juego.getTablero().getMovimientoValidosOponente().size());
        for (Movimiento mov : juego.getTablero().getMovimientoValidos()) {
            System.out.println("  " + mov);
        }
    }
}
