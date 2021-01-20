/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.test;

import net.qoopo.qchess.core.RMovimiento;
import net.qoopo.qchess.core.tablero.Tablero;
import net.qoopo.qchess.core.util.TimeUtil;

/**
 *
 * @author alberto
 */
public class Test {

//    /**
//     * Ejemplo que carga una partida en formato pen
//     *
//     * @param args
//     */
//    public static void main(String[] args) {
//
//        // <editor-fold defaultstate="collapsed" desc="Jugadas">
////        String pen = "[Event \"Saint Louis Blitz 2017\"]\n"
////                + "[Site \"Saint Louis USA\"]\n"
////                + "[Date \"2017.08.17\"]\n"
////                + "[Round \"?\"]\n"
////                + "[White \"Hikaru Nakamura\"]\n"
////                + "[Black \"Garry Kasparov\"]\n"
////                + "[Result \"1/2-1/2\"]\n"
////                + "\n"
////                + "1. c4 c5 2. g3 g6 3. Bg2 Bg7 4. e3 h5 5. d4 h4 6. Nc3 Nc6 7. d5 Bxc3+ 8. bxc3\n"
////                + "Ne5 9. Nf3 Nxf3+ 10. Bxf3 d6 11. g4 h3 12. g5 Qa5 13. Bd2 Rh4 14. e4 e5 15. Rg1\n"
////                + "Ne7 16. Qb3 Qc7 17. Qc2 Bd7 18. Be2 Nc8 19. a4 a5 20. f3 Nb6 21. Kf2 O-O-O 22.\n"
////                + "Qb3 Kb8 23. Rg3 Rdh8 24. Kg1 Ka7 25. Kh1 Be8 26. Rgg1 Bd7 27. Rab1 Be8 28. Rb2\n"
////                + "Nd7 29. Rgb1 b6 30. Rg1 Qd8 31. Rbb1 Rf4 32. Qc2 Rhh4 33. Be1 f6 34. Bxh4 Rxh4\n"
////                + "35. gxf6 Qxf6 36. Qd2 Qf4 37. Qxf4 exf4 38. Rb2 Kb7 39. Rgb1 Kc7 40. Kg1 Rh5 41.\n"
////                + "Kf2 Rg5 42. Bf1 Rh5 43. Ke1 Rh4 44. Kd1 Rh5 45. Kd2 Rh4 46. Ke2 Rh5 47. Kf2 Rh4\n"
////                + "48. Be2 Rh5 49. Rg1 Ne5 50. Ra2 Bd7 51. Raa1 Be8 52. Ke1 Bd7 53. Kd2 Be8 54. Kc2\n"
////                + "Bd7 55. Kb3 Kd8 56. Kb2 Kc7 57. Kb3 Kd8 58. Raf1 Kc7 59. Rg4 Bxg4 60. fxg4 Rh7\n"
////                + "61. Rxf4 Rf7 62. Rxf7+ Nxf7 63. Kc2 Kd7 64. Bf1 Ng5 65. Kd3 Ke7 66. Ke3 Kf6 67.\n"
////                + "Kf4 Nf7 68. Bxh3 g5+ 69. Kg3 Ne5 70. Bf1 Ng6 71. Be2 1/2-1/2";
////
////        String pen = "[Event \"2020-tata-steel-masters\"]\n"
////                + "[Site \"\"]\n"
////                + "[Date \"2020.01.14\"]\n"
////                + "[Round \"?\"]\n"
////                + "[White \"Jorden van Foreest\"]\n"
////                + "[Black \"Magnus Carlsen\"]\n"
////                + "[Result \"1/2-1/2\"]\n"
////                + "\n"
////                + "1. e4 e5 2. Nf3 Nc6 3. Bc4 Nf6 4. Ng5 d5 5. exd5 Na5 6. Bb5+ c6 7. dxc6 bxc6 8.\n"
////                + "Bd3 Nd5 9. Nf3 Bd6 10. Nc3 O-O 11. Be2 Nf4 12. O-O Bg4 13. d3 Nxe2+ 14. Qxe2 f5\n"
////                + "15. h3 Bh5 16. g4 fxg4 17. Ng5 Qd7 18. Nce4 Be7 19. Ng3 Bg6 20. Qxg4 Qxg4 21.\n"
////                + "hxg4 c5 22. N5e4 Nc6 23. Be3 Nd4 24. Rac1 Rac8 25. Kg2 c4 26. Bxd4 exd4 27. f3\n"
////                + "Rc6 28. b3 Ba3 29. Rce1 cxd3 30. cxd3 a5 31. Rf2 Bc1 32. Nd2 Bxd3 33. Nc4 Bf4\n"
////                + "34. Ne5 Rc3 35. Nxd3 Rxd3 36. Nf5 g6 37. Re4 Bg5 38. Ne7+ Kh8 39. Nc6 Be3 40.\n"
////                + "Re2 Rd1 41. Nxa5 Rg1+ 42. Kh2 Rc1 43. Kg2 Rg1+ 44. Kh2 Rc1 45. Kg2 1/2-1/2";
////        String pen = "[Event \"2020-tata-steel-masters\"]\n"
////                + "[Site \"\"]\n"
////                + "[Date \"2020.01.21\"]\n"
////                + "[Round \"?\"]\n"
////                + "[White \"Alireza Firouzja\"]\n"
////                + "[Black \"Magnus Carlsen\"]\n"
////                + "[Result \"0-1\"]\n"
////                + "\n"
////                + "1. e4 e5 2. Nf3 Nc6 3. Bb5 Nf6 4. d3 d6 5. c3 a6 6. Ba4 Be7 7. O-O O-O 8. Re1\n"
////                + "Re8 9. Nbd2 Bf8 10. h3 b5 11. Bc2 Bb7 12. d4 g6 13. a3 Nb8 14. d5 c6 15. c4 Nbd7\n"
////                + "16. a4 Qc7 17. b3 Rec8 18. Ra2 bxc4 19. bxc4 a5 20. Nf1 Ba6 21. Ne3 Nc5 22. Nd2\n"
////                + "cxd5 23. cxd5 Rab8 24. Ba3 Qd8 25. Qf3 h5 26. Raa1 Bh6 27. Rab1 Rxb1 28. Rxb1\n"
////                + "Kg7 29. Nef1 h4 30. Ne3 Bf4 31. Nef1 Qc7 32. g3 hxg3 33. fxg3 Bh6 34. h4 Qd7 35.\n"
////                + "Kg2 Nxa4 36. Bxa4 Qxa4 37. Bxd6 Qd4 38. Qf2 Qxf2+ 39. Kxf2 Bxf1 0-1";
////
////        String pen = "[Event \"Ch Manhattan Chess Club\"]\n"
////                + "[Site \"New York (USA)\"]\n"
////                + "[Date \"1941.??.??\"]\n"
////                + "[Round \"?\"]\n"
////                + "[White \"Jose Raul Capablanca\"]\n"
////                + "[Black \"Forsberg H\"]\n"
////                + "[Result \"1-0\"]\n"
////                + "\n"
////                + "1. e3 g6 2. d4 Bg7 3. Nf3 d5 4. c4 e6 5. Nc3 Nf6 6. Be2 O-O 7. O-O b6 8. cxd5\n"
////                + "exd5 9. Ne5 c5 10. b3 Ba6 11. Ba3 Re8 12. Bxa6 Nxa6 13. f4 cxd4 14. exd4 Nc7 15.\n"
////                + "Rc1 a6 16. f5 Nb5 17. fxg6 hxg6 18. Bb4 Nxd4 19. Qxd4 Nd7 20. Nxd5 Rxe5 21. Qf2\n"
////                + "f5 22. Rfd1 Re4 23. Bc3 Rc8 24. Bxg7 Rxc1 25. Rxc1 Kxg7 26. Rc7 Kf8 27. Qd2 Qe8\n"
////                + "28. Nf6 Re1+ 29. Kf2 Re2+ 30. Qxe2 Qxe2+ 31. Kxe2 Nxf6 32. Rc6 Nd5 33. Rd6 1-0";
////// </editor-fold>
//        String pen = "[Event \"Evento local\"]\n"
//                + "[Site \"Sitio local\"]\n"
//                + "[Date \"2021.01.11\"]\n"
//                + "[Round \"1\"]\n"
//                + "[White \"Stockfish 11 64\"]\n"
//                + "[Black \"chess22k 1.14\"]\n"
//                + "[Result \"1-0\"]\n"
//                + "[PlyCount \"51\"]\n"
//                + "[ECO \"E11\"]\n"
//                + "[Opening \"Defensa Bogo-India\"]\n"
//                + "\n"
//                + "1. d4 Nf6 2. c4 e6 3. Nf3 Bb4+ 4. Bd2 c5 5. g3 d5 6. Bxb4 cxb4 7. Nbd2 O-O 8.\n"
//                + "Bg2 Nc6 9. Rc1 Bd7 10. O-O h6 11. e3 Ne7 12. Ne5 Rc8 13. Qb3 a5 14. a3 dxc4 15.\n"
//                + "Ndxc4 b5 16. Nd6 Rxc1 17. Rxc1 Qb6 18. Ne4 Rc8 19. Rxc8+ Bxc8 20. axb4 a4 21.\n"
//                + "Qc2 Nxe4 22. Bxe4 Qd6 23. h4 Qxb4 24. Bh7+ Kf8 25. Qc7 Qxb2 26. Qd8# 1-0";
//
//        Juego juego = new Juego();
//        juego.getTablero().setLenguaje(Constantes.LENGUAJE_INGLES);
//        juego.cargarPEN(pen);
//        juego.iniciar();
//        System.out.println("Fin del juego, voy revisar movimientos posibles");
//        System.out.println("Score=" + juego.getTablero().getScoreMaterial());
//        System.out.println(juego.getTablero().getJugadorActual().getNombre() + " / " + juego.getTablero().getJugadorActual().getColorNombre() + " Juegos posibles=" + juego.getTablero().getMovimientoValidos().size());
//        System.out.println(juego.getTablero().getJugadorOponente().getNombre() + " / " + juego.getTablero().getJugadorOponente().getColorNombre() + " Juegos posibles=" + juego.getTablero().getMovimientoValidosOponente().size());
//        for (Movimiento mov : juego.getTablero().getMovimientoValidos()) {
//            System.out.println("  " + mov);
//        }
//
//        System.out.println("Ahora voy de reversa");
//        juego.reversa();
//        System.out.println("Supuesta posicion inicial");
//        juego.getTablero().imprimir();
//        System.out.println("Jugadas");
//        juego.imprimirJugadas();
//    }
//    public static void main(String[] args) {
//        Tablero tablero = new Tablero();
//        tablero.posicionInicial();
//        Movimiento mov1 = Movimiento.get(tablero.get("e2"), tablero.get("e4"));
//        tablero.mover(mov1);
//        tablero.finTurno();
//        tablero.imprimir();
//        tablero.desMover(mov1);
//        tablero.imprimir();
//    }
////    
    public static void main(String[] args) {
        Tablero tablero = new Tablero();
//        tablero.posicionInicial();
        tablero.cargarFEN("rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8"); // resultado esperado 1486
        tablero.calcular();
        tablero.imprimir();
//        RMovimiento rMov= tablero.mover("e1g1");
//        tablero.finTurno();
//        tablero.imprimir();
//        tablero.finTurno();
//        tablero.desMover(rMov.getMovimiento());
//        tablero.imprimir();
//        System.out.println("Mover=" + tablero.mover("d7c8q"));
//        System.out.println("Mover=" + tablero.mover("dxc8q"));
//        System.out.println("Mover=" + tablero.mover("dxc8=Q"));
//        System.out.println("Mover=" + tablero.mover("dxc8=R"));
//        System.out.println("Mover=" + tablero.mover("dxc8r"));
//        tablero.finTurno();
//        tablero.imprimir();
//        tablero.setMovimientosLegales(false);
        long tInicio = System.currentTimeMillis();
        long nodos = tablero.perft(2);
        System.out.println("Nodos " + nodos + "  T:" + TimeUtil.getTime(tInicio));
    }
}
