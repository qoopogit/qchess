/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

import net.qoopo.qchess.core.tablero.Tablero;
import java.util.ArrayList;
import java.util.Scanner;
import net.qoopo.qchess.core.jugador.Humano;

/**
 *
 * @author alberto
 */
public class Juego extends Thread {

    private String nombreEvento;
    private String sitio;
    private String fecha;
    private String round;
    private String blancasNombre;
    private String negrasNombre;
    private String resultado;
    private String plyCount;
    private String eco;
    private String apertura;
    private Tablero tablero = new Tablero();
    private final ArrayList<String> movimientos = new ArrayList<>();
    private final ArrayList<Movimiento> movimientosMov = new ArrayList<>();
    private int posActual = -1;

//    //variable que indica que se esta reproduciendo una jugada en lugar de estar realizando un juego
    private boolean jugando = true;

    public Juego() {
        tablero.setJugadorBlancas(new Humano("Humano"));
        tablero.setJugadorNegras(new Humano("Humano"));
        tablero.posicionInicial();
    }

    /**
     * Carga una posicion FEN
     *
     * @param FEN
     */
    public void cargarFEN(String FEN) {
        this.tablero.cargarFEN(FEN);
    }

    /**
     * Carga una partida con el formato PGN
     *
     * @param pgn partida en formato
     */
    public void cargarPEN(String pgn) {
        limpiarMovimientos();
        try {
            String[] lineas = pgn.split("\n");
            String[] partes;
            String valor;
            for (String linea : lineas) {
                if (linea.startsWith("[")) {
                    //[Event "Evento local"]
                    linea = linea.substring(1, linea.length() - 1);
                    partes = linea.split("\"");
                    try {
                        valor = partes[1].substring(0, partes[1].length() - 1).trim();//elimina el ultimo "
                    } catch (Exception e) {
                        valor = "";
                    }
                    switch (partes[0].trim()) {
                        case "Event" ->
                            setNombreEvento(valor);
                        case "Site" ->
                            setSitio(valor);
                        case "Date" ->
                            setFecha(valor);
                        case "Round" ->
                            setRound(valor);
                        case "White" ->
                            setBlancasNombre(valor);
                        case "Black" ->
                            setNegrasNombre(valor);
                        case "Result" ->
                            setResultado(valor);
                        case "PlyCount" ->
                            setPlyCount(valor);
                        case "ECO" ->
                            setEco(valor);
                        case "Opening" ->
                            setApertura(valor);
                    }
                } else {
                    // lineas de las jugadas
                    //1. e4 e5 2. Nf3 Nc6 3. Bc4 d5 4. exd5 Nb4 5. c3 Nxd5 6. d4 Nde7 7. Nxe5 f6 8.
                    //Bf7# 1-0
                    for (String token : linea.split(" ")) {
                        if (!token.contains(".")) {
                            if (esTokenValido(token)) {
                                agregarMovimiento(token);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jugando = false;
//        tablero.posicionInicial();
        reproducirMovimientos();
    }

    /**
     * Valida que un token (movimiento dentro de un pen) es valido
     *
     * @param token
     * @return
     */
    private boolean esTokenValido(String token) {
        if (token == null) {
            return false;
        }
        if (token.isBlank()) {
            return false;
        }
        if (token.equalsIgnoreCase("O-O")) {
            return true;
        }
        if (token.equalsIgnoreCase("O-O-O")) {
            return true;
        }
        if (token.equalsIgnoreCase("1-1")) {
            return false;
        }
        if (token.equalsIgnoreCase("1-0")) {
            return false;
        }
        if (token.equalsIgnoreCase("0-1")) {
            return false;
        }
        if (token.contains("-") && !token.endsWith("-")) {
            return false;
        }
        return true;
    }

    public void reproducirMovimientos() {
        tablero.posicionInicial();
        for (int i = 0; i <= posActual; i++) {
            tablero.mover(movimientos.get(i));
            tablero.finTurno();
        }
    }

    /**
     * Agrega un movimiento a la lista de movimientos
     *
     * @param movimiento
     */
    public void agregarMovimiento(String movimiento) {
        movimientos.add(movimiento);
    }

    public void siguiente() {
        RMovimiento rMovimiento = null;
        // si esta navegando 
        if (posActual < movimientos.size() - 1) {
            posActual++;
            System.out.println("Mov [" + (getNumeroJugada()) + "] [" + tablero.getJugadorActual().getNombre() + " /  " + tablero.getJugadorActual().getColorNombre() + "] [" + getMovimientoActual() + "]");
            //representarMovimientos();
            rMovimiento = tablero.mover(getMovimientoActual());
            tablero.finTurno();
        } else {
            System.out.println("vamos a jugar");
            // si esta jugando, espera que el movimiento se realice
            if (tablero.getJugadorActual().isHumano()) {
                //leemos el movimiento desde la entrada estandar
                boolean valido = false;
                while (!valido) {
                    Scanner sc = new Scanner(System.in);
                    String movimiento = sc.nextLine();
                    agregarMovimiento(movimiento);
                    posActual++;
                    System.out.println("Mov [" + (getNumeroJugada()) + "] [" + tablero.getJugadorActual().getNombre() + " /  " + tablero.getJugadorActual().getColorNombre() + "] [" + getMovimientoActual() + "]");
                    rMovimiento = tablero.mover(movimiento);
                    valido = rMovimiento.isSatisfactorio();
                }
                tablero.finTurno();
            } else {
                Movimiento movimiento = tablero.getJugadorActual().mejorMovimiento(tablero);
                if (movimiento != null) {
                    agregarMovimiento(movimiento.getNotacion());// toma la notacion del movimento y no la referencia de las casillas o piezas porque corresponden a otro tablero
                    posActual++;
                    System.out.println("Mov [" + (getNumeroJugada()) + "] [" + tablero.getJugadorActual().getNombre() + " /  " + tablero.getJugadorActual().getColorNombre() + "] [" + getMovimientoActual() + "]");
                    rMovimiento = tablero.mover(movimiento);
                    tablero.finTurno();
                }
            }
        }
        if (rMovimiento != null) {
            this.movimientosMov.add(rMovimiento.getMovimiento());
        }
        tablero.verificarEstado();
    }

    public void anterior() {
        if (posActual > 0) {
            posActual--;
        }
        reproducirMovimientos();
    }

    public void inicio() {
        posActual = -1;
        reproducirMovimientos();
    }

    public void fin() {
        posActual = movimientos.size() - 1;
        reproducirMovimientos();
    }

    public String getMovimientoActual() {
        if (movimientos.size() > posActual && posActual >= 0) {
            return movimientos.get(posActual);
        } else {
            return "N/A";
        }
    }

    public String getMovimientoActualMov() {
        if (movimientosMov.size() > posActual && posActual >= 0) {
            return movimientosMov.get(posActual).getNotacion();
        } else {
            return "N/A";
        }
    }

    public void limpiarMovimientos() {
        movimientos.clear();
        movimientosMov.clear();
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getSitio() {
        return sitio;
    }

    public void setSitio(String sitio) {
        this.sitio = sitio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getBlancasNombre() {
        return blancasNombre;
    }

    public void setBlancasNombre(String blancasNombre) {
        this.blancasNombre = blancasNombre;
    }

    public String getNegrasNombre() {
        return negrasNombre;
    }

    public void setNegrasNombre(String negrasNombre) {
        this.negrasNombre = negrasNombre;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getPlyCount() {
        return plyCount;
    }

    public void setPlyCount(String plyCount) {
        this.plyCount = plyCount;
    }

    public String getEco() {
        return eco;
    }

    public void setEco(String eco) {
        this.eco = eco;
    }

    public String getApertura() {
        return apertura;
    }

    public void setApertura(String apertura) {
        this.apertura = apertura;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public boolean isPuedeAvanzar() {
        return posActual < movimientos.size() - 1;
    }

    public int getPosActual() {
        return posActual;
    }

    public void setPosActual(int posActual) {
        this.posActual = posActual;
    }

    //Devuelve el numero de jugada
    public int getNumeroJugada() {
        return (posActual + 2) / 2;
    }

    /**
     * Reproduce una partida o inicia el loop del juego
     */
    public void iniciar() {
        getTablero().imprimir();
//        System.out.println("antes while");
        while ((isPuedeAvanzar() || jugando) && !tablero.isJaqueMate()) {
//            System.out.println("lloooopppp");
            siguiente();
            getTablero().imprimir();
        }
    }

    /**
     * Realiza un loop inverso de las jugadas y deshace los movimientos
     */
    public void reversa() {
        System.out.println("Jugadas en lista   :" + movimientos.size());
        System.out.println("Jugadas en lista 2 :" + movimientosMov.size());
        getTablero().finTurno();
        getTablero().imprimir();
        while (posActual > -1) {
            System.out.println("Mov [" + (getNumeroJugada()) + "] [" + tablero.getJugadorActual().getNombre() + " /  " + tablero.getJugadorActual().getColorNombre() + "] [" + getMovimientoActual() + "] [" + getMovimientoActualMov() + "]");
            tablero.desMover(movimientosMov.get(posActual));
            getTablero().finTurno();
            posActual--;
            getTablero().imprimir();
        }
    }

    /**
     * Imprime las jugadas al aconsola
     */
    public void imprimirJugadas() {
        StringBuilder sb = new StringBuilder();
        int c = 1;
        int c2 = 1;
        for (String movimiento : this.movimientos) {
            if (c2 % 2 != 0) {
                sb.append(" ").append(c).append(".");
                c++;
            }
            sb.append(" ").append(movimiento);
            c2++;
        }
        System.out.println(sb.toString());
    }

}
