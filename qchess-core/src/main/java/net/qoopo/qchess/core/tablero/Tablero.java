/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core.tablero;

import java.util.ArrayList;
import java.util.List;
import net.qoopo.qchess.core.Constantes;
import net.qoopo.qchess.core.Movimiento;
import net.qoopo.qchess.core.Pieza;
import net.qoopo.qchess.core.Position;
import net.qoopo.qchess.core.RMovimiento;
import net.qoopo.qchess.core.jugador.Jugador;
import net.qoopo.qchess.core.piezas.Alfil;
import net.qoopo.qchess.core.piezas.Caballo;
import net.qoopo.qchess.core.piezas.Dama;
import net.qoopo.qchess.core.piezas.Peon;
import net.qoopo.qchess.core.piezas.Rey;
import net.qoopo.qchess.core.piezas.Torre;
import net.qoopo.qchess.core.util.CalculoTime;
import net.qoopo.qchess.core.util.LenguajeUtil;

/**
 *
 * @author alberto
 */
public class Tablero {

    private static final String[] LETRAS = {"a", "b", "c", "d", "e", "f", "g", "h"};

    //ColumnasxFilas
    private final Casilla[][] casillas = new Casilla[8][8];
    //Jugador del color Blanco
    private Jugador jugadorNegras;
    //Jugador del color Negro
    private Jugador jugadorBlancas;
    //Lista de movimientos validos para el color actual
    private List<Movimiento> movimientoValidos = new ArrayList<>();
    //Lista de movimientos validos para el color oponente
    private List<Movimiento> movimientoValidosOponente = new ArrayList<>();
    //indica si un tablero es virtual (usado para calcular movimientos). en caso de ser virtual no se imprimiran mensajes de movimientos invalidos por enroque qeu son muchos
    private boolean virtual = false;
    //valor de las piezas
    private int scoreMaterial;
    //valor del desarrollo, movimientos posibles
    private int scoreMovimientos;
    //Numero de movimientos dados en el tablero
    private int movimientos;
    //Color del actual turno
    private int colorTurno = Pieza.BLANCA;
    //Lenguaje a usar en la lectura de las piezas
    private int lenguaje = Constantes.LENGUAJE_ESPANIOL;

    private boolean movimientosLegales = true;

    public Tablero() {
        iniciar();
    }

    /**
     * Devuelve el jugador actual
     *
     * @return
     */
    public Jugador getJugadorActual() {
        return colorTurno == Pieza.BLANCA ? jugadorBlancas : jugadorNegras;
    }

    /**
     * Devuelve el jugador oponente
     *
     * @return
     */
    public Jugador getJugadorOponente() {
        return colorTurno == Pieza.BLANCA ? jugadorNegras : jugadorBlancas;
    }

    /**
     * Inicializa un tablero vacio
     */
    private void iniciar() {
        colorTurno = Pieza.BLANCA;
        for (int fila = 0; fila < 8; fila++) {
            boolean negro = (fila + 1) % 2 == 0;
            for (int col = 0; col < 8; col++) {
                casillas[col][fila] = new Casilla(LETRAS[col] + (fila + 1), col + 1, fila + 1, negro);
                negro = !negro;
            }
        }
    }

    /**
     * Inicia el tablero con la ubicacion estandar
     */
    public void posicionInicial() {
        //        <editor-fold defaultstate="collapsed" desc="Inicializacion del tablero Manual">
//        iniciar();
//        //Negras
//        get("a8").setPieza(Torre.nuevo(Pieza.NEGRA));
//        get("b8").setPieza(Caballo.nuevo(Pieza.NEGRA));
//        get("c8").setPieza(Alfil.nuevo(Pieza.NEGRA));
//        get("d8").setPieza(Dama.nuevo(Pieza.NEGRA));
//        get("e8").setPieza(Rey.nuevo(Pieza.NEGRA));
//        get("f8").setPieza(Alfil.nuevo(Pieza.NEGRA));
//        get("g8").setPieza(Caballo.nuevo(Pieza.NEGRA));
//        get("h8").setPieza(Torre.nuevo(Pieza.NEGRA));
//        get("a7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("b7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("c7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("d7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("e7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("f7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("g7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        get("h7").setPieza(Peon.nuevo(Pieza.NEGRA));
//        //Blancas
//        get("a1").setPieza(Torre.nuevo(Pieza.BLANCA));
//        get("b1").setPieza(Caballo.nuevo(Pieza.BLANCA));
//        get("c1").setPieza(Alfil.nuevo(Pieza.BLANCA));
//        get("d1").setPieza(Dama.nuevo(Pieza.BLANCA));
//        get("e1").setPieza(Rey.nuevo(Pieza.BLANCA));
//        get("f1").setPieza(Alfil.nuevo(Pieza.BLANCA));
//        get("g1").setPieza(Caballo.nuevo(Pieza.BLANCA));
//        get("h1").setPieza(Torre.nuevo(Pieza.BLANCA));
//        get("a2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("b2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("c2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("d2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("e2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("f2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("g2").setPieza(Peon.nuevo(Pieza.BLANCA));
//        get("h2").setPieza(Peon.nuevo(Pieza.BLANCA));
// </editor-fold>
//        cargarFEN("tcadract/pppppppp/8/8/8/8/PPPPPPPP/TCADRACT w");
        cargarFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w");
        calcular();
    }

    /**
     * Inicia el tablero con una notacion FEN
     *
     * @param FEN
     */
    public void cargarFEN(String FEN) {
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
                                    case "t" ->
                                        pieza = Torre.nuevo(Pieza.NEGRA);
                                    case "T" ->
                                        pieza = Torre.nuevo(Pieza.BLANCA);
                                    case "r" ->
                                        pieza = Rey.nuevo(Pieza.NEGRA);
                                    case "R" ->
                                        pieza = Rey.nuevo(Pieza.BLANCA);
                                    case "d" ->
                                        pieza = Dama.nuevo(Pieza.NEGRA);
                                    case "D" ->
                                        pieza = Dama.nuevo(Pieza.BLANCA);
                                    case "a" ->
                                        pieza = Alfil.nuevo(Pieza.NEGRA);
                                    case "A" ->
                                        pieza = Alfil.nuevo(Pieza.BLANCA);
                                    case "c" ->
                                        pieza = Caballo.nuevo(Pieza.NEGRA);
                                    case "C" ->
                                        pieza = Caballo.nuevo(Pieza.BLANCA);
                                    case "p" ->
                                        pieza = Peon.nuevo(Pieza.NEGRA);
                                    case "P" ->
                                        pieza = Peon.nuevo(Pieza.BLANCA);
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
                    case Constantes.LENGUAJE_ESPANIOL ->
                        this.colorTurno = siguiente.equalsIgnoreCase("b") ? Pieza.BLANCA : Pieza.NEGRA;
                    case Constantes.LENGUAJE_INGLES ->
                        this.colorTurno = siguiente.equalsIgnoreCase("w") ? Pieza.BLANCA : Pieza.NEGRA;
                }
            }
        }
    }

    /**
     * Devuelve la posicion actual en formato FEN
     *
     * @return
     */
    public String getFEN() {
        StringBuilder sb = new StringBuilder();
        for (int fila = 8; fila > 0; fila--) {
            int vacios = 0;
            for (int col = 1; col <= 8; col++) {
                final Casilla casilla = get(col, fila);
                if (casilla.isOcupada()) {
                    if (casilla.getPieza().getColor() == Pieza.BLANCA) {
                        if (vacios > 0) {
                            sb.append(vacios);
                        }
                        sb.append(casilla.getPieza().getNombre().toUpperCase());
                    } else {
                        if (vacios > 0) {
                            sb.append(vacios);
                        }
                        sb.append(casilla.getPieza().getNombre().toLowerCase());
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
        if (columna > 0 && columna <= 8 && fila > 0 && fila <= 8) {
            return casillas[columna - 1][fila - 1];
        } else {
            return null;
        }
    }

    /**
     * Devuelve una casilla en funcion de la posicion
     *
     * @param pos
     * @return
     */
    public Casilla get(Position pos) {
        return get(pos.col, pos.row);
    }

    /**
     * Devuelve una casilla en funcion de la notacion e5, b8, a7
     *
     * @param notacion
     * @return
     */
    public Casilla get(String notacion) {
        try {
            if (notacion != null && !notacion.isEmpty() && notacion.length() == 2) {
                return get(
                        getIndiceColumna(notacion.substring(0, 1)),
                        Integer.parseInt(notacion.substring(1, 2))
                );
            }
//            if (notacion != null && !notacion.isEmpty() && notacion.length() == 2) {
//                String letra = notacion.substring(0, 1);
//                for (int i = 0; i < 8; i++) {
//                    if (LETRAS[i].equalsIgnoreCase(letra)) {
//                        return casillas[i][Integer.parseInt(notacion.substring(1, 2)) - 1];
//                    }
//                }
//            }
        } catch (Exception e) {
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
        sb.append("FEN:").append(getFEN());
        sb.append("\n");
        sb.append("Score material: [").append(scoreMaterial).append("] Score movs: [").append(scoreMovimientos).append("]\n");
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
    public boolean estaObstruido(String origen, String destino) {
        return estaObstruido(get(origen), get(destino));
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
    public boolean estaObstruido(Position origen, Position destino) {
        return estaObstruido(get(origen), get(destino));
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
            if (origen.getCol() == destino.getCol()) {
                int menor = Math.min(origen.getFila(), destino.getFila());
                int mayor = Math.max(origen.getFila(), destino.getFila());
                for (int fila = menor + 1; fila < mayor; fila++) {
                    if (get(origen.getCol(), fila).isOcupada()) {
                        return true;
                    }
                }
            } else if (origen.getFila() == destino.getFila()) {
                int menor = Math.min(origen.getCol(), destino.getCol());
                int mayor = Math.max(origen.getCol(), destino.getCol());
                for (int col = menor + 1; col < mayor; col++) {
                    if (get(col, origen.getFila()).isOcupada()) {
                        return true;
                    }
                }
            } else if (Math.abs(destino.getCol() - origen.getCol()) == Math.abs(destino.getFila() - origen.getFila())) {
                //recorre la diagonal de derecha a izquierda, 
                int signoColumna = destino.getCol() > origen.getCol() ? 1 : -1;
                int signoFila = destino.getFila() > origen.getFila() ? 1 : -1;
                int nCasillas = Math.abs(destino.getCol() - origen.getCol()) - 1;

                int col = origen.getCol();
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
     * @param pieza Letra de la pieza
     * @param destino la casilla destino a la que puede llegar la pieza que se
     * solicita solicitada
     * @param color Color de la pieza solicitada
     * @return
     */
    private Casilla getCasilla(String pieza, Casilla destino, int color) {
        // si la letra es minuscula se refiere a una columna, busca un peon en esa minuscula
        int columna = getIndiceColumna(pieza);
        if (columna != -1) {
            //buscamos un peon en la columna solicitada
            for (int fila = 1; fila <= 8; fila++) {
                Casilla c = get(columna, fila);
                if (c != null && c.isOcupada() && c.getPieza() != null && c.getPieza().getColor() == color && c.getPieza() instanceof Peon) {
                    if (destino == null || c.getPieza().esMovimientoValido(this, Movimiento.get(c, destino), false)) {
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
                        if (destino == null || c.getPieza().esMovimientoValido(this, Movimiento.get(c, destino), false)) {
                            if (ayuda == null || (columnaAyuda == col || fila == filaAyuda)) {
                                return c;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("ERROR: No se devolvio la casilla de la pieza para [" + pieza + "] Color [" + Pieza.getColorNombre(color) + "] con destino [" + destino + "] [" + (destino != null ? destino.getNombre() : "XX") + "]");
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
    public RMovimiento mover(String movimiento) {
        try {
            movimiento = movimiento.replace("#", "").replace("+", "").replace("x", "").replace("=", "").trim();
//            System.out.println("Mov [" + movimiento + "]");
            switch (movimiento) {
                //enroque corto
                case "O-O" -> {
                    if (colorTurno == Pieza.BLANCA) {
                        return mover("e1", "g1");
                    } else {
                        return mover("e8", "g8");
                    }
                }
                //enroque largo
                case "O-O-O" -> {
                    if (colorTurno == Pieza.BLANCA) {
                        return mover("e1", "c1");
                    } else {
                        return mover("e8", "c8");
                    }
                }
                default -> {
                    int largo = movimiento.length();
                    Casilla destino = get(movimiento.substring(movimiento.length() - 2));//la casilla de destino
                    switch (largo) {
                        case 2 -> { //del tipo e2, g4, movimientos de peon
                            return mover(getCasilla(movimiento.substring(0, 1), destino, colorTurno), destino);
                        }
                        case 3 -> //del tipo Ca6, o del tipo d8Q donde se promociona a una Dama en el ejemplo
                        {
                            if (destino != null) {
                                //Ca6
                                return mover(getCasilla(movimiento.substring(0, 1), destino, colorTurno), destino);
                            } else {
                                //Promocion, d8Q
                                //es una promocion, y la ficha que promociona se indica en la ultima letra (dc8Q)
                                destino = get(movimiento.substring(0, 2));
                                return mover(Movimiento.get(getCasilla(movimiento.substring(0, 2), destino, colorTurno), destino, Pieza.get(LenguajeUtil.getLetraPieza(movimiento.substring(movimiento.length() - 1), lenguaje), colorTurno)));
                            }
                        }
                        case 4 -> {
                            // del tipo e2e4, b6b7, tambien puede ser del tipo Nde7, donde si indica la columna o fila de la pieza que se debe mover, en caso de haber dos piezas del mismo tipo que pueden llegar a la casilla solicitada
                            // odel tipo dc8Q, donde el peon de la columna d captura a la ficha de c8 y la promociona por una Dama
                            Casilla origen = get(movimiento.substring(0, 2));
                            if (origen != null) {
                                return mover(origen, destino);
                            } else {
                                if (destino == null) {
                                    //es una promocion, y la ficha que promociona se indica en la ultima letra (dc8Q)
                                    destino = get(movimiento.substring(1, 3));
                                    return mover(Movimiento.get(getCasilla(movimiento.substring(0, 1), destino, colorTurno), destino, Pieza.get(LenguajeUtil.getLetraPieza(movimiento.substring(movimiento.length() - 1), lenguaje), colorTurno)));
                                } else {
                                    //se indica la letra de la pieza y la columna y fila donde esta (Nde2)
                                    return mover(getCasilla(movimiento.substring(0, 2), destino, colorTurno), destino);
                                }
                            }
                        }
                        case 5 -> {
                            // del tipo e2e4Q, notacion de las dos casillas y una promocion
                            Casilla origen = get(movimiento.substring(0, 2));
                            destino = get(movimiento.substring(2, 4));
                            return mover(Movimiento.get(origen, destino, Pieza.get(LenguajeUtil.getLetraPieza(movimiento.substring(movimiento.length() - 1), lenguaje), colorTurno)));

                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return RMovimiento.getFalse();
    }

    /**
     * Realiza un movimiento desde la casilla origen a la casilla destino
     *
     * @param origen
     * @param destino
     * @return devuelve si el movimiento fue realizado
     */
    public RMovimiento mover(Casilla origen, Casilla destino) {
        if (origen != null && destino != null) {
            return mover(Movimiento.get(origen, destino));
        } else {
            System.out.println("ERROR: Movimiento invalido, nulos  Origen[" + origen + "] Destino[" + destino + "]");
        }
        return RMovimiento.getFalse();
    }

    /**
     * Realiza un movimiento desde la casilla origen a la casilla destino
     *
     * @param origen
     * @param destino
     * @return devuelve si el movimiento fue realizado
     */
    public RMovimiento mover(String origen, String destino) {
        if (origen != null && destino != null) {
            return mover(Movimiento.get(get(origen), get(destino)));
        } else {
            System.out.println("ERROR: Movimiento invalido, nulos  Origen[" + origen + "] Destino[" + destino + "]");
        }
        return RMovimiento.getFalse();
    }

    /**
     * Realiza el movimiento solicitado
     *
     * @param movimiento
     * @return devuelve si el movimiento fue realizado
     */
    public RMovimiento mover(Movimiento movimiento) {
        Casilla origen = movimiento.getOrigen(this);
        Pieza pieza = origen.getPieza();

        if (pieza == null) {
            System.out.println("mv con pieza nula [" + movimiento.getNotacion() + "]");
//            try {
//                throw new Exception("mv con pieza nula [" + movimiento.getNotacion() + "]");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            return RMovimiento.getFalse();
        }

        Casilla destino = movimiento.getDestino(this);

//        if (movimiento.getPieza().esMovimientoValido(this, movimiento, true)) {
        if (movimiento.isEnroque(this, pieza)) {
            //coloca la torre junto al rey
            int direccion = destino.getCol() > origen.getCol() ? 1 : -1;
            Casilla destinotorre = get(destino.getCol() + direccion * -1, destino.getFila());
            Casilla origenTorre = get((colorTurno == Pieza.BLANCA ? (direccion == 1 ? "h1" : "a1") : (direccion == 1 ? "h8" : "a8")));

            //valida que la torre no se haya movido (el rey ya valida si es un enroque que no se haya movido)
            if (origenTorre.isOcupada() && origenTorre.getPieza() instanceof Torre) {
                if (!origenTorre.getPieza().isMovida()) {
                    if (!estaObstruido(origen, origenTorre)) {
                        pieza.aumentarMovs();
                        //Realiza el enroque, mueve el rey
                        destino.setPieza(pieza);
                        origen.setPieza(null);
                        //mueve la torre
                        Pieza torre = origenTorre.getPieza();
                        torre.aumentarMovs();
                        destinotorre.setPieza(torre);
                        origenTorre.setPieza(null);
                        movimientos++;
                        return RMovimiento.get(movimiento);
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
        } else if (movimiento.isPromocion(this, pieza)) {
            pieza.aumentarMovs();
            movimiento.setPiezaCapturada(destino.getPieza() != null ? destino.getPieza().getNombre() : null);
            destino.setPieza(movimiento.getPiezaPromocion());
            origen.setPieza(null);
            movimientos++;
            return RMovimiento.get(movimiento);
        } else {
            pieza.aumentarMovs();
            movimiento.setPiezaCapturada(destino.getPieza() != null ? destino.getPieza().getNombre() : null);
            destino.setPieza(pieza);
            origen.setPieza(null);
            movimientos++;
            return RMovimiento.get(movimiento);
        }
//        } else {
//            System.out.println("Movimiento invalido " + movimiento);
//        }
        return RMovimiento.getFalse();
    }

    /**
     * Deshace un movimiento
     *
     * @param movimiento
     * @return
     */
    public boolean desMover(Movimiento movimiento) {
        if (movimiento != null) {
            Casilla origen = movimiento.getOrigen(this);
            Casilla destino = movimiento.getDestino(this);

            if (origen != null && destino != null) {
                Pieza pieza = destino.getPieza();
                if (pieza != null) {
                    if (movimiento.isEnroque(this, pieza)) {
                        //coloca la torre junto al rey
                        int direccion = destino.getCol() > origen.getCol() ? 1 : -1;
                        Casilla destinotorre = get(destino.getCol() + direccion * -1, destino.getFila());
                        Casilla origenTorre = get((colorTurno == Pieza.BLANCA ? (direccion == 1 ? "h1" : "a1") : (direccion == 1 ? "h8" : "a8")));
                        //valida que la torre no se haya movido (el rey ya valida si es un enroque que no se haya movido)
                        //Realiza el enroque, mueve el rey
                        origen.setPieza(destino.getPieza());
                        destino.setPieza(null);
                        pieza.restarMovs();
                        //mueve la torre
                        Pieza torre = destinotorre.getPieza();
                        torre.restarMovs();
                        origenTorre.setPieza(torre);
                        destinotorre.setPieza(null);
                        movimientos--;
                        return true;
                    } else if (movimiento.isPromocion(this, pieza)) {
                        pieza = Peon.nuevo(pieza.getColor());// reemplaza la pieza por un peon.
                        origen.setPieza(pieza);
                        destino.setPieza(Pieza.get(movimiento.getPiezaCapturada(), pieza.getColor() * -1));
                        pieza.restarMovs();
                        movimientos--;
                        return true;
                    } else {
                        origen.setPieza(pieza);
                        destino.setPieza(Pieza.get(movimiento.getPiezaCapturada(), pieza.getColor() * -1));
                        pieza.restarMovs();
                        movimientos--;
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Valida si el rey del jugador actual esta en Jaque
     *
     * @return
     */
    public boolean isJaque() {
        return isJaque(colorTurno);
    }

    public boolean isJaque(int color) {
        int lengActual = this.lenguaje;
        this.lenguaje = Constantes.LENGUAJE_ESPANIOL;
        Casilla casilla = getCasilla("R", null, color);
        this.lenguaje = lengActual;
        if (casilla == null) {
            System.out.println("NO SE PUEDE DETECTAR JAQUE, NO SE ENCUENTRA LA CASILLA DEL REY [" + getJugadorActual().getNombre() + "/" + getJugadorActual().getColorNombre() + "]");
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
        calcular(movimientosLegales); // usar verdadero para detectar jaque mate
    }

    /**
     * Realiza el calculo del tablero y los movimientos legales, cuantos
     * atacandes hay por pieza, a cuantos puede atacar, y da un valor del
     * tablero para cada jugador
     *
     * @param movimientosLegales. Si es verdadero, solo genera movimientos
     * Legales (Los movimientos no permiten jaque), si es falso solo genera
     * movimientos pseudo-legales (No se valida si permiten un Jaque).
     */
    public void calcular(boolean movimientosLegales) {
        long tInicio = System.currentTimeMillis();
        try {
            scoreMaterial = 0;
            scoreMovimientos = 0;
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
            // <editor-fold defaultstate="collapsed" desc="Calcular el tablero por fuerza bruta">
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
//                                    if (mov.getPieza().esMovimientoValido(this, mov, movimientosLegales)) {
//                                        if (mov.getPieza().getColor() == colorTurno) {
//                                            movimientoValidos.add(mov);
//                                        } else {
//                                            movimientoValidosOponente.add(mov);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
// </editor-fold>
            //recorre el tablero, para calcular el scoreMaterial del tablero en funcion de las piezas
            for (int fila = 8; fila >= 1; fila--) {
//            for (int fila = 1; fila <= 8; fila++) {
                for (int col = 1; col <= 8; col++) {
//                for (int col = 8; col >= 1; col--) {
                    Casilla casilla = get(col, fila);
                    if (casilla.isOcupada()) {
                        scoreMaterial += casilla.getPieza().getValor() * casilla.getPieza().getColor();
                        if (casilla.getPieza().getColor() == colorTurno) {
                            movimientoValidos.addAll(casilla.getPieza().getMovimientos(this, casilla, movimientosLegales));
                        } else {
                            movimientoValidosOponente.addAll(casilla.getPieza().getMovimientos(this, casilla, movimientosLegales));
                        }
                    }
                }
            }
            //calcula amenazas y objetivos
            for (Movimiento mov : movimientoValidos) {
                Casilla destino = mov.getDestino(this);
                Casilla origen = mov.getOrigen(this);
                if (destino.isOcupada()) {
                    destino.getPieza().amenazasAdd();
                    origen.getPieza().objetivosAdd();
                }
                origen.getPieza().jugadasAdd();
            }
            for (Movimiento mov : movimientoValidosOponente) {
                Casilla destino = mov.getDestino(this);
                Casilla origen = mov.getOrigen(this);
                if (destino.isOcupada()) {
                    destino.getPieza().amenazasAdd();
                    origen.getPieza().objetivosAdd();
                }
                origen.getPieza().jugadasAdd();
            }
            // utiliza el numero de movimientos posibles para modificar la calificacion
            scoreMovimientos += 10 * (movimientoValidos.size()) * colorTurno;
            scoreMovimientos -= 10 * movimientoValidosOponente.size() * colorTurno;
        } catch (Exception e) {
            e.printStackTrace();
        }
        CalculoTime.fin(tInicio);
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

    public int getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(int movimientos) {
        this.movimientos = movimientos;
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

    public int getColorTurnoOponente() {
        return colorTurno * -1;
    }

    public void setColorTurno(int colorTurno) {
        this.colorTurno = colorTurno;
    }

    public int getScoreMaterial() {
        return scoreMaterial;
    }

    public void setScoreMaterial(int scoreMaterial) {
        this.scoreMaterial = scoreMaterial;
    }

    public int getScoreMovimientos() {
        return scoreMovimientos;
    }

    public void setScoreMovimientos(int scoreMovimientos) {
        this.scoreMovimientos = scoreMovimientos;
    }

    public boolean isVirtual() {
        return virtual;
    }

    public void setVirtual(boolean virtual) {
        this.virtual = virtual;
    }

    public boolean isMovimientosLegales() {
        return movimientosLegales;
    }

    public void setMovimientosLegales(boolean movimientosLegales) {
        this.movimientosLegales = movimientosLegales;
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
        return esTurnoBlancas() ? scoreMaterial : -scoreMaterial;
    }

    /**
     * Prueba perft
     *
     * @param depth
     * @return
     */
    public long perft(int depth) {
        return perft(depth, true);
    }

    /**
     * Prueba perft
     *
     * @param depth
     * @param print
     * @return
     */
    public long perft(int depth, boolean print) {
        long nodes = 0;
        if (depth == 0) {
            return 1;
        }
        //optimizacion, cuando solo hay movimientos legales el numero no cambia
        if (depth == 1) {
            return movimientoValidos.size();
        }

        final List<Movimiento> _lstMovimientos = new ArrayList<>(movimientoValidos);
        int _colorTurno = this.colorTurno;
        int _scoreMaterial = this.scoreMaterial;
        int _scoreMovimientos = this.scoreMovimientos;
        for (Movimiento mov : _lstMovimientos) {
            if (mover(mov).isSatisfactorio()) {
                finTurno();
                if (this.movimientosLegales || !isJaque(getColorTurnoOponente())) {
                    long nNodes = perft(depth - 1, false);
                    if (print) {
                        System.out.println(mov.getNotacion() + ": " + nNodes);
                    }
                    nodes += nNodes;
                }
                if (!desMover(mov)) {
                    System.out.println("ERROR: Error al deshacer el movimiento " + mov);
                }
                //devuelve los valores originales del turno y las calificaciones
                this.colorTurno = _colorTurno;
                this.scoreMaterial = _scoreMaterial;
                this.scoreMovimientos = _scoreMovimientos;
            } else {
                System.out.println("ERROR: Error al hacer el movimiento " + mov);
            }
        }
        return nodes;
    }
    //--------------------------------------------------------------
//    public long perft(int depth) {
//        return perft(this, depth, true);
//    }
//
//    public long perft(Tablero tablero, int depth, boolean print) {
//        long nodes = 0;
//        if (depth == 0) {
//            return 1;
//        }
//        //optimizacion, cuando solo hay movimientos legales el numero no cambia
//        if (depth == 1) {
//            return movimientoValidos.size();
//        }
//        for (Movimiento mov : movimientoValidos) {
//            Tablero tVirtual = tablero.clone();
//            if (tVirtual.mover(mov).isSatisfactorio()) {
//                tVirtual.finTurno();
//                long tmNodes = tVirtual.perft(tVirtual, depth - 1, false);
//                if (print) {
//                    System.out.println(mov.getNotacion() + ": " + tmNodes);
//                }
//                nodes += tmNodes;
//            } else {
//                System.out.println("ERROR: Al hacer el movimiento " + mov);
//            }
//        }
//        return nodes;
//    }

    /**
     * Crea una copia del tablero
     *
     * @return
     */
    @Override
    public Tablero clone() {
        Tablero t = new Tablero();
        if (jugadorBlancas != null) {
            t.setJugadorBlancas(jugadorBlancas.clone());
        }
        if (jugadorNegras != null) {
            t.setJugadorNegras(jugadorNegras.clone());
        }
        t.cargarFEN(this.getFEN());
        //clona las casillas con las piezas ylso calculos realizados
//        for (int col = 0; col < 8; col++) {
//            for (int fila = 0; fila < 8; fila++) {
//                t.casillas[col][fila] = casillas[col][fila].clone();
//            }
//        }
        t.colorTurno = colorTurno;
        t.lenguaje = lenguaje;
        t.scoreMaterial = scoreMaterial;
        t.virtual = true;
        return t;
    }

}
