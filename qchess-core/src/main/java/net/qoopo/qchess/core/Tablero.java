/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

import java.util.ArrayList;
import java.util.List;
import net.qoopo.qchess.core.jugador.Jugador;
import net.qoopo.qchess.core.piezas.Alfil;
import net.qoopo.qchess.core.piezas.Caballo;
import net.qoopo.qchess.core.piezas.Dama;
import net.qoopo.qchess.core.piezas.Peon;
import net.qoopo.qchess.core.piezas.Rey;
import net.qoopo.qchess.core.piezas.Torre;
import net.qoopo.qchess.core.tablero.Casilla;
import net.qoopo.qchess.core.util.LenguajeUtil;

/**
 *
 * @author alberto
 */
public class Tablero {

    private static final String[] LETRAS = {"a", "b", "c", "d", "e", "f", "g", "h"};
    private int lenguaje = Constantes.LENGUAJE_ESPANIOL;
    //ColumnasxFilas
    private Casilla[][] casillas;

    private int colorTurno = Pieza.BLANCA;

    private Jugador jugadorNegras;
    private Jugador jugadorBlancas;
    private List<Movimiento> movimientoValidos = new ArrayList<>();
    private List<Movimiento> movimientoValidosOponente = new ArrayList<>();

    //indica si un tablero es virtual (usado para calcular movimientos). en caso de ser virtual no se imprimiran mensajes de movimientos invalidos por enroque qeu son muchos
    private boolean virtual = false;

    private int score;

    public Tablero() {
        iniciar();
    }

    public Jugador getJugadorActual() {
        return colorTurno == Pieza.BLANCA ? jugadorBlancas : jugadorNegras;
    }

    public Jugador getJugadorOponente() {
        return colorTurno == Pieza.BLANCA ? jugadorNegras : jugadorBlancas;
    }

    /**
     * Inicializa un tablero vacio
     */
    private void iniciar() {
        casillas = new Casilla[8][8];
        colorTurno = Pieza.BLANCA;
        for (int col = 0; col < 8; col++) {
            for (int fila = 0; fila < 8; fila++) {
                casillas[col][fila] = new Casilla(LETRAS[col] + (fila + 1), col + 1, fila + 1);
            }
        }
    }

    /**
     * Inicia en la ubicacion estandar
     */
    public void iniciarEstandard() {
        iniciar();
        //Negras
        get("a8").setPieza(Torre.nuevo(Pieza.NEGRA));
        get("b8").setPieza(Caballo.nuevo(Pieza.NEGRA));
        get("c8").setPieza(Alfil.nuevo(Pieza.NEGRA));
        get("d8").setPieza(Dama.nuevo(Pieza.NEGRA));
        get("e8").setPieza(Rey.nuevo(Pieza.NEGRA));
        get("f8").setPieza(Alfil.nuevo(Pieza.NEGRA));
        get("g8").setPieza(Caballo.nuevo(Pieza.NEGRA));
        get("h8").setPieza(Torre.nuevo(Pieza.NEGRA));
        get("a7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("b7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("c7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("d7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("e7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("f7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("g7").setPieza(Peon.nuevo(Pieza.NEGRA));
        get("h7").setPieza(Peon.nuevo(Pieza.NEGRA));
        //Blancas
        get("a1").setPieza(Torre.nuevo(Pieza.BLANCA));
        get("b1").setPieza(Caballo.nuevo(Pieza.BLANCA));
        get("c1").setPieza(Alfil.nuevo(Pieza.BLANCA));
        get("d1").setPieza(Dama.nuevo(Pieza.BLANCA));
        get("e1").setPieza(Rey.nuevo(Pieza.BLANCA));
        get("f1").setPieza(Alfil.nuevo(Pieza.BLANCA));
        get("g1").setPieza(Caballo.nuevo(Pieza.BLANCA));
        get("h1").setPieza(Torre.nuevo(Pieza.BLANCA));
        get("a2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("b2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("c2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("d2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("e2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("f2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("g2").setPieza(Peon.nuevo(Pieza.BLANCA));
        get("h2").setPieza(Peon.nuevo(Pieza.BLANCA));
        calcular();
    }

    /**
     * Inicia el tablero con una notacion FEN
     *
     * @param FEN
     */
    public void iniciarFEN(String FEN) {
        iniciar();
        if (FEN != null && !FEN.isBlank()) {
            String[] partes = FEN.split(" ");
            {
                //lenguaje, 1 ingles, 2 espaniol
                lenguaje = Constantes.LENGUAJE_INGLES;
                if (partes[0].contains("K") || partes[0].contains("k")) {
                    lenguaje = Constantes.LENGUAJE_INGLES;
                } else {
                    lenguaje = Constantes.LENGUAJE_ESPANIOL;
                }

                String[] filas = partes[0].split("/");
                int fila = 8;
                for (String sFila : filas) {
                    if (sFila != null && !sFila.isBlank()) {
                        int col = 1;
                        for (int i = 0; i < sFila.length(); i++) {
                            int numero = -1;
                            String letra = sFila.substring(i, i + 1);
                            try {
                                numero = Integer.parseInt(letra);
                            } catch (Exception e) {
                                numero = -1;
                            }
                            if (numero != -1) {
                                col += numero;
                            } else {
                                Pieza pieza = null;
                                switch (LenguajeUtil.getLetraPiezaFEN(letra, lenguaje)) {
                                    case "t":
                                        pieza = Torre.nuevo(Pieza.NEGRA);
                                        break;
                                    case "T":
                                        pieza = Torre.nuevo(Pieza.BLANCA);
                                        break;
                                    case "r":
                                        pieza = Rey.nuevo(Pieza.NEGRA);
                                        break;
                                    case "R":
                                        pieza = Rey.nuevo(Pieza.BLANCA);
                                        break;
                                    case "d":
                                        pieza = Dama.nuevo(Pieza.NEGRA);
                                        break;
                                    case "D":
                                        pieza = Dama.nuevo(Pieza.BLANCA);
                                        break;
                                    case "a":
                                        pieza = Alfil.nuevo(Pieza.NEGRA);
                                        break;
                                    case "A":
                                        pieza = Alfil.nuevo(Pieza.BLANCA);
                                        break;
                                    case "c":
                                        pieza = Caballo.nuevo(Pieza.NEGRA);
                                        break;
                                    case "C":
                                        pieza = Caballo.nuevo(Pieza.BLANCA);
                                        break;
                                    case "p":
                                        pieza = Peon.nuevo(Pieza.NEGRA);
                                        break;
                                    case "P":
                                        pieza = Peon.nuevo(Pieza.BLANCA);
                                        break;
                                }
                                get(col, fila).setPieza(pieza);
                                col++;
                            }
                        }
                    }
                    fila--;
                }
            }
            if (partes.length > 1) {
                String siguiente = partes[1];
                switch (lenguaje) {
                    case Constantes.LENGUAJE_ESPANIOL -> {
                        this.colorTurno = siguiente.equalsIgnoreCase("b") ? Pieza.BLANCA : Pieza.NEGRA;
                    }
                    case Constantes.LENGUAJE_INGLES -> {
                        this.colorTurno = siguiente.equalsIgnoreCase("w") ? Pieza.BLANCA : Pieza.NEGRA;
                    }
                }
//                this.colorTurno= ;
                System.out.println("Siguiente a jugar:" + siguiente);
            }
        }
    }

    /**
     * Devuelve la posicion en formato FEN
     *
     * @return
     */
    public String getFEN() {
        StringBuilder sb = new StringBuilder();
        for (int fila = 8; fila > 0; fila--) {
            int vacios = 0;
            for (int col = 1; col <= 8; col++) {
                Casilla c = get(col, fila);
                if (c.isOcupada()) {
                    if (c.getPieza().getColor() == Pieza.BLANCA) {
                        if (vacios > 0) {
                            sb.append(vacios);
                        }
                        sb.append(c.getPieza().getNombre().toUpperCase());
                    } else {
                        if (vacios > 0) {
                            sb.append(vacios);
                        }
                        sb.append(c.getPieza().getNombre().toLowerCase());
                    }
                    vacios = 0;
                } else {
                    vacios++;
                    if (col == 8 && vacios > 0) {
                        sb.append(vacios);
                    }
                }
            }
            if (fila > 1) {
                sb.append("/");
            }
        }
        return sb.toString();
    }

    /**
     * Devuelve una casilla en funcion de su columna y fila
     *
     * @param columna
     * @param fila
     * @return
     */
    public Casilla get(int columna, int fila) {
        try {
            return casillas[columna - 1][fila - 1];
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelve una casilla en funcion dela notacion e5, b8, a7
     *
     * @param notacion
     * @return
     */
    public Casilla get(String notacion) {
        try {
            if (notacion != null && !notacion.isEmpty() && notacion.length() == 2) {
                String letra = notacion.substring(0, 1);
                for (int i = 0; i < 8; i++) {
                    if (LETRAS[i].equalsIgnoreCase(letra)) {
                        return casillas[i][Integer.parseInt(notacion.substring(1, 2)) - 1];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Imprime en la consola el tablero
     */
    public void imprimir() {
        StringBuilder sb = new StringBuilder();
        sb.append("Siguiente Turno:").append(this.colorTurno == Pieza.BLANCA ? "Blancas" : "Negras").append("\n").append("\n");
        for (int fila = 8; fila > 0; fila--) {
            sb.append(fila).append(" ");
            for (int col = 1; col <= 8; col++) {
                sb.append("[").append(get(col, fila)).append("]");
            }
            sb.append("\n");
        }
        sb.append(" ");
        for (int col = 0; col < 8; col++) {
            sb.append("  ").append(LETRAS[col]).append(" ");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    /**
     * Realiza una validacion si las casillas que hay entre la casilla origen y
     * la casilla destino esta ocupada.
     *
     * Las casillas origen y desitno deben estar o en la misma fila , la misma
     * columna o en la misma diagonal
     *
     * @param origen
     * @param destino
     * @return
     */
    public boolean estaObstruido(Casilla origen, Casilla destino) {
        if (origen != null && destino != null) {
            if (origen.getColumna() == destino.getColumna()) {
                int menor = Math.min(origen.getFila(), destino.getFila());
                int mayor = Math.max(origen.getFila(), destino.getFila());
                for (int fila = menor + 1; fila < mayor; fila++) {
                    if (get(origen.getColumna(), fila).isOcupada()) {
                        return true;
                    }
                }
            } else if (origen.getFila() == destino.getFila()) {
                int menor = Math.min(origen.getColumna(), destino.getColumna());
                int mayor = Math.max(origen.getColumna(), destino.getColumna());
                for (int col = menor + 1; col < mayor; col++) {
                    if (get(col, origen.getFila()).isOcupada()) {
                        return true;
                    }
                }
            } else if (Math.abs(destino.getColumna() - origen.getColumna()) == Math.abs(destino.getFila() - origen.getFila())) {
                //recorre la diagonal de derecha a izquierda, 
                int signoColumna = destino.getColumna() > origen.getColumna() ? 1 : -1;
                int signoFila = destino.getFila() > origen.getFila() ? 1 : -1;
                int nCasillas = Math.abs(destino.getColumna() - origen.getColumna()) - 1;

                int col = origen.getColumna();
                int fila = origen.getFila();
                for (int n = 1; n <= nCasillas; n++) {
                    col += signoColumna;
                    fila += signoFila;
                    if (get(col, fila).isOcupada()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    /**
     * Devuelve el indice de acuerdo a la letra de la columna
     *
     * @param letra
     * @return
     */
    private int getIndiceColumna(String letra) {
        for (int i = 0; i < LETRAS.length; i++) {
            if (LETRAS[i].equals(letra)) {
                return (i + 1);
            }
        }
        return -1;
    }

    /**
     * Devuelve la casilla que contiene la pieza solicitada
     *
     * @param pieza
     * @param casillaDestino la casilla destino a la que puede llegar la pieza
     * que se solicita solicitada
     * @param color
     * @return
     */
    private Casilla getCasillaPieza(String pieza, Casilla casillaDestino, int color) {
        // si la letra es minuscula se refiere a una columna, busca un peon en esa minuscula
        int columna = getIndiceColumna(pieza);
        if (columna != -1) {
            //buscamos un peon en la columna solicitada
            for (int fila = 1; fila <= 8; fila++) {
                Casilla c = get(columna, fila);
                if (c != null && c.isOcupada() && c.getPieza() != null && c.getPieza().getColor() == color && c.getPieza() instanceof Peon) {
                    if (casillaDestino == null || c.getPieza().esMovimientoValido(this, Movimiento.get(c, casillaDestino), false)) {
                        return c;
                    }
                }
            }
        } else {
            String letraPieza = pieza.substring(0, 1);
            String ayuda = null;
            int columnaAyuda = -1;
            int filaAyuda = -1;
            //si se indica la columna o fila
            if (pieza.length() == 2) {
                ayuda = pieza.substring(1, 2);
                columnaAyuda = getIndiceColumna(ayuda);
                if (columnaAyuda == -1) {
                    filaAyuda = Integer.parseInt(ayuda);
                }
            }
            //busca una pieza solicitada que cumpla las condiciones, la cual es que pueda realizar el movimiento solicitado
            for (int fila = 1; fila <= 8; fila++) {
                for (int col = 1; col <= 8; col++) {
                    Casilla c = get(col, fila);
                    if (c != null && c.isOcupada() && c.getPieza().getColor() == color && c.getPieza().getNombre().equals(LenguajeUtil.getLetraPieza(letraPieza, lenguaje))) {
                        if (casillaDestino == null || c.getPieza().esMovimientoValido(this, Movimiento.get(c, casillaDestino), false)) {
                            if (ayuda == null || (columnaAyuda == col || fila == filaAyuda)) {
                                return c;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("No se devolvio la casilla de la pieza para " + pieza + " Color=" + Pieza.getColorNombre(colorTurno));
        imprimir();
        return null;
    }

    /**
     * Usado solo para pruebas, realiza un movimiento , realiza el fin de turno
     * y realiza una impresion del tablero
     *
     * @param movimiento
     */
    public void moverTest(String movimiento) {
        mover(movimiento);
        finTurno();
        imprimir();
    }

    /**
     * Parsea el movimiento en notacion algebraica y realiza el movimiento
     * solicitado
     *
     * @param movimiento
     * @return devuelve si el movimiento fue realizado
     */
    public boolean mover(String movimiento) {
        try {
            movimiento = movimiento.replace("#", "").replace("+", "").replace("x", "");
            switch (movimiento) {
                //enroque corto
                case "O-O" -> {
                    if (colorTurno == Pieza.BLANCA) {
                        return mover(get("e1"), get("g1"));
                    } else {
                        return mover(get("e8"), get("g8"));
                    }
                }
                //enroque largo
                case "O-O-O" -> {
                    if (colorTurno == Pieza.BLANCA) {
                        return mover(get("e1"), get("c1"));
                    } else {
                        return mover(get("e8"), get("c8"));
                    }
                }
                default -> {
                    int largo = movimiento.length();
                    Casilla destino = get(movimiento.substring(movimiento.length() - 2));//la casilla de destino
                    switch (largo) {
                        case 2 -> { //del tipo e2, g4, movimientos de peon
                            return mover(getCasillaPieza(movimiento.substring(0, 1), destino, colorTurno), destino);
                        }
                        case 3 -> //del tipo Ca6
                        {
                            return mover(getCasillaPieza(movimiento.substring(0, 1), destino, colorTurno), destino);
                        }
                        case 4 -> {
                            // del tipo e2e4, b6b7, tambien puede ser del tipo Nde7, donde si indica la columna o fila de la pieza que se debe mover, en caso de haber dos piezas del mismo tipo que pueden llegar a la casilla solicitada
                            Casilla origen = get(movimiento.substring(0, 2));
                            if (origen != null) {
                                return mover(origen, destino);
                            } else {
                                //se indica la letra de la pieza y la columna y fila donde esta
                                return mover(getCasillaPieza(movimiento.substring(0, 2), destino, colorTurno), destino);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Realiza un movimiento desde la casilla origen a la casilla destino
     *
     * @param origen
     * @param destino
     * @return devuelve si el movimiento fue realizado
     */
    public boolean mover(Casilla origen, Casilla destino) {
        if (origen != null && destino != null) {
            return mover(Movimiento.get(origen, destino));
        } else {
            System.out.println("Movimiento invalido, nulos  Origen[" + origen + "] Destino[" + destino + "]");
        }
        return false;
    }

//    /**
//     * Realiza un movimiento sin validaciones
//     *
//     * @param movimiento
//     */
//    public void moverFuerza(Movimiento movimiento) {
//        if (movimiento.getPieza() != null) {
////        Pieza capturada = movimiento.getDestino().getPieza();
//            movimiento.getDestino().setPieza(movimiento.getPieza());
//            movimiento.getOrigen().setPieza(null);
//            movimiento.getPieza().setMovida(true);
//        }
//    }
    /**
     * Realiza el movimiento solicitado
     *
     * @param movimiento
     * @return devuelve si el movimiento fue realizado
     */
    public boolean mover(Movimiento movimiento) {
//        if (movimiento.getPieza().esMovimientoValido(this, movimiento, true)) {
        if (movimiento.esEnroque()) {
            //coloca la torre junto al rey
            int direccion = movimiento.getDestino().getColumna() > movimiento.getOrigen().getColumna() ? 1 : -1;
            Casilla destinotorre = get(movimiento.getDestino().getColumna() + direccion * -1, movimiento.getDestino().getFila());
            Casilla origenTorre = get((colorTurno == Pieza.BLANCA ? (direccion == 1 ? "h1" : "a1") : (direccion == 1 ? "h8" : "a8")));

            //valida que la torre no se haya movido (el rey ya valida si es un enroque que no se haya movido)
            if (origenTorre.isOcupada() && origenTorre.getPieza() instanceof Torre) {
                if (!origenTorre.getPieza().isMovida()) {
                    if (!estaObstruido(movimiento.getOrigen(), origenTorre)) {
                        movimiento.getPieza().setMovida(true);
                        //Realiza el enroque, mueve el rey
                        movimiento.getDestino().setPieza(movimiento.getPieza());
                        movimiento.getOrigen().setPieza(null);
                        //mueve la torre
                        Pieza torre = origenTorre.getPieza();
                        torre.setMovida(true);
                        destinotorre.setPieza(torre);
                        origenTorre.setPieza(null);
                        return true;
                    } else {
                        if (!virtual) {
                            System.out.println("Movimiento invalido. No se puede realizar el enroque, las casillas intermedias estan ocupadas [" + movimiento + "]");
                        }
                    }
                } else {
                    if (!virtual) {
                        System.out.println("Movimiento invalido. No se puede realizar el enroque, La torre ya se ha movido [" + movimiento + "]");
                    }
                }
            } else {
                if (!virtual) {
                    System.out.println("Movimiento invalido. No se puede realizar el enroque. La casilla destino no contiene una torre. Destino [" + origenTorre.getNombre() + "] [" + movimiento + "]");
                }
            }
        } else {
//            Pieza capturada = movimiento.getDestino().getPieza();
            movimiento.getDestino().setPieza(movimiento.getPieza());
            movimiento.getOrigen().setPieza(null);
            movimiento.getPieza().setMovida(true);
            return true;
        }
//        } else {
//            System.out.println("Movimiento invalido " + movimiento);
//        }
        return false;
    }

    /**
     * Valida si el rey del jugador actual esta en Jaque
     *
     * @return
     */
    public boolean isJaque() {
        int lengActual = this.lenguaje;
        this.lenguaje = Constantes.LENGUAJE_ESPANIOL;
        Casilla casilla = getCasillaPieza("R", null, colorTurno);
        this.lenguaje = lengActual;
        if (casilla == null) {
            System.out.println("NO SE PUEDE DETECTAR JAQUE, NO SE ENCUENTRA LA CASILLA DEL REY [" + getJugadorActual().getNombre() + "/" + getJugadorActual().getColorNombre() + "]");
//            throw new Exception("NO SE PUEDE DETECTAR JAQUE, NO SE ENCUENTRA LA CASILLA DEL REY");
        }
        return casilla != null && casilla.isOcupada() && casilla.getPieza().getAmenazas() > 0;
    }

    /**
     * Valida si el rey del jugador actual esta en Jaque MAte
     *
     * @return
     */
    public boolean isJaqueMate() {
        return movimientoValidos.isEmpty() && isJaque();
    }

    /**
     * Valida si el rey del jugador esta ahogado, no esta en jaque pero no puede
     * mover
     *
     * @return
     * @returnme avisas
     */
    public boolean isAhogado() {
        return movimientoValidos.isEmpty() && !isJaque();
    }

    /**
     * Realiza el calculo del tablero, cuantos atacandes hay por pieza, a
     * cuantos puede atacar, y da un valor del tablero para cada jugador
     */
    public void calcular() {
        calcular(true); // usar verdadero para detectar jaque mate
    }

    //usado para controlar los niveles anidados que llamo al metodo calcular
//    public static int niveles = 0;
    /**
     * Realiza el calculo del tablero, cuantos atacandes hay por pieza, a
     * cuantos puede atacar, y da un valor del tablero para cada jugador
     *
     * @param validarPermiteJaque. Cuando calcula los movimientos posibles,
     * calcula si el movimiento permite el Jaque
     */
    public void calcular(boolean validarPermiteJaque) {
        try {
//            niveles++;
//            System.out.println("Niveles anidados:" + Tablero.niveles);
//            System.out.println("--> Calculando tablero");
            score = 0;
            movimientoValidos.clear();
            movimientoValidosOponente.clear();
            //recorre el tablero, para encerar los valores
            for (int fila = 1; fila <= 8; fila++) {
                for (int col = 1; col <= 8; col++) {
                    Casilla origen = get(col, fila);
                    if (origen.getPieza() != null) {
                        origen.getPieza().limpiarValores();
                    }
                }
            }

//            //recorre el tablero, fuerza bruta, recore todas las casillas del tablero, y pregunta si se puede mover a todas las demas casillas (64x64=4096)
//            for (int fila = 1; fila <= 8; fila++) {
//                for (int col = 1; col <= 8; col++) {
//                    Casilla origen = get(col, fila);
//                    if (origen.isOcupada()) {
//                        //recorre el tablero buscando las piezas que puede atacar
//                        for (int filaDest = 1; filaDest <= 8; filaDest++) {
//                            for (int colDest = 1; colDest <= 8; colDest++) {
//                                //si no es la misma casilla
//                                if (!(filaDest == fila && colDest == col)) {
//                                    Movimiento mov = Movimiento.get(origen, get(colDest, filaDest));
//                                    if (mov.getPieza().esMovimientoValido(this, mov, validarPermiteJaque)) {
//                                        if (mov.getPieza().color == colorTurno) {
//                                            movimientoValidos.add(mov);
//                                        } else {
//                                            movimientoValidosOponente.add(mov);
//                                        }
//
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
            //recorre el tablero, para calcular el score del tablero en funcion de las piezas
            for (int fila = 1; fila <= 8; fila++) {
                for (int col = 1; col <= 8; col++) {
                    Casilla casilla = get(col, fila);
                    if (casilla.isOcupada()) {
                        score += casilla.getPieza().getValor() * casilla.getPieza().getColor();
                        if (casilla.getPieza().color == colorTurno) {
                            movimientoValidos.addAll(casilla.getPieza().getMovimientosValidos(this, casilla, validarPermiteJaque));
                        } else {
                            movimientoValidosOponente.addAll(casilla.getPieza().getMovimientosValidos(this, casilla, validarPermiteJaque));
                        }
                    }
                }
            }
            //calcula amenazas y objetivos
            for (Movimiento mov : movimientoValidos) {
                if (mov.getDestino().isOcupada()) {
                    mov.getDestino().getPieza().amenazas++;
                    mov.getOrigen().getPieza().objetivos++;
                }
                mov.getOrigen().getPieza().jugadas++;
            }
            for (Movimiento mov : movimientoValidosOponente) {
                if (mov.getDestino().isOcupada()) {
                    mov.getDestino().getPieza().amenazas++;
                    mov.getOrigen().getPieza().objetivos++;
                }
                mov.getOrigen().getPieza().jugadas++;
            }

            // utiliza el numero de movimientos posibles par amodificar la calificacion
            if (colorTurno == Pieza.BLANCA) {
                score += 10 * (movimientoValidos.size());
                score -= 10 * movimientoValidosOponente.size();
            } else {
                score -= 10 * (movimientoValidos.size());
                score += 10 * movimientoValidosOponente.size();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        niveles--;
    }

    public int getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(int lenguaje) {
        this.lenguaje = lenguaje;
    }

    public void verificarEstado() {
        try {
            if (isJaqueMate()) {
                try {
                    System.out.println(getJugadorActual().getNombre() + " / " + getJugadorActual().getColorNombre() + " esta en JAQUE MATE !!!");
                } catch (Exception e) {
                }
            } else if (isJaque()) {
                try {
                    System.out.println(getJugadorActual().getNombre() + " / " + getJugadorActual().getColorNombre() + " esta en Jaque !!!");
                } catch (Exception e) {
                }
            } else if (isAhogado()) {
                try {
                    System.out.println(getJugadorActual().getNombre() + " / " + getJugadorActual().getColorNombre() + " esta Ahogado !!!");
                } catch (Exception e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Finaliza el turno y llama a clacular el tablero
     */
    public void finTurno() {
        this.colorTurno *= -1;
        calcular();
    }

    public Jugador getJugadorNegras() {
        return jugadorNegras;
    }

    public void setJugadorNegras(Jugador jugadorNegras) {
        jugadorNegras.setColor(Pieza.NEGRA);
        this.jugadorNegras = jugadorNegras;
    }

    public Jugador getJugadorBlancas() {
        return jugadorBlancas;
    }

    public void setJugadorBlancas(Jugador jugadorBlancas) {
        jugadorBlancas.setColor(Pieza.BLANCA);
        this.jugadorBlancas = jugadorBlancas;
    }

    public List<Movimiento> getMovimientoValidos() {
        return movimientoValidos;
    }

    public void setMovimientoValidos(List<Movimiento> movimientoValidos) {
        this.movimientoValidos = movimientoValidos;
    }

    public List<Movimiento> getMovimientoValidosOponente() {
        return movimientoValidosOponente;
    }

    public void setMovimientoValidosOponente(List<Movimiento> movimientoValidosOponente) {
        this.movimientoValidosOponente = movimientoValidosOponente;
    }

    public int getColorTurno() {
        return colorTurno;
    }

    public void setColorTurno(int colorTurno) {
        this.colorTurno = colorTurno;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    /**
     * Crea una copia del tablero
     *
     * @return
     */
    @Override
    public Tablero clone() {
        Tablero t = new Tablero();
        t.setJugadorBlancas(jugadorBlancas.clone());
        t.setJugadorNegras(jugadorNegras.clone());
        t.iniciarFEN(this.getFEN());
        //clona las casillas con las piezas ylso calculos realizados
//        for (int col = 0; col < 8; col++) {
//            for (int fila = 0; fila < 8; fila++) {
//                t.casillas[col][fila] = casillas[col][fila].clone();
//            }
//        }
        t.colorTurno = colorTurno;
        t.lenguaje = lenguaje;
        t.score = score;
        t.virtual = true;
        return t;
    }

    private boolean esTurnoBlancas() {
        return colorTurno == Pieza.BLANCA;
    }

    /**
     * Devuelve el material de las piezas en positivo.
     *
     * @return
     */
    public int getValorMaterial() {
        return esTurnoBlancas() ? score : -score;
    }

}
