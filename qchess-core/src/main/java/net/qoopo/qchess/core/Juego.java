/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

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
    private ArrayList<String> movimientos = new ArrayList<>();
    private int posActual = -1;
    private boolean jugando;

    public Juego() {
        tablero.setJugadorBlancas(new Humano("Humano"));
        tablero.setJugadorNegras(new Humano("Humano"));
        tablero.iniciarEstandard();
    }

    /**
     * Carga una posicion FEN
     *
     * @param FEN
     */
    public void cargarFEN(String FEN) {
        this.tablero.iniciarFEN(FEN);
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
                        case "Event":
                            setNombreEvento(valor);
                            break;
                        case "Site":
                            setSitio(valor);
                            break;
                        case "Date":
                            setFecha(valor);
                            break;
                        case "Round":
                            setRound(valor);
                            break;
                        case "White":
                            setBlancasNombre(valor);
                            break;
                        case "Black":
                            setNegrasNombre(valor);
                            break;
                        case "Result":
                            setResultado(valor);
                            break;
                        case "PlyCount":
                            setPlyCount(valor);
                            break;
                        case "ECO":
                            setEco(valor);
                            break;
                        case "Opening":
                            setApertura(valor);
                            break;
                    }
                } else {
                    // lineas de las jugadas
                    //1. e4 e5 2. Nf3 Nc6 3. Bc4 d5 4. exd5 Nb4 5. c3 Nxd5 6. d4 Nde7 7. Nxe5 f6 8.
                    //Bf7# 1-0
                    String[] tokens = linea.split(" ");
                    for (String token : tokens) {
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
//        tablero.iniciarEstandard();
        representarMovimientos();
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

    public void representarMovimientos() {
        tablero.iniciarEstandard();
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
        // si esta navegando 
        if (posActual < movimientos.size() - 1) {
            posActual++;
            System.out.println("Mov [" + (getNumeroJugada()) + "] [" + tablero.getJugadorActual().getNombre() + " /  " + tablero.getJugadorActual().getColorNombre() + "] [" + getMovimientoActual() + "]");
            //representarMovimientos();
            tablero.mover(getMovimientoActual());
            tablero.finTurno();
        } else {
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
                    valido = tablero.mover(movimiento);
                }
                tablero.finTurno();
            } else {
                Movimiento movimiento = tablero.getJugadorActual().mejorMovimiento(tablero);
                if (movimiento != null) {
                    agregarMovimiento(movimiento.getNotacion());// toma la notacion del movimento y no la referencia de las casillas o piezas porque corresponden a otro tablero
                    posActual++;
                    System.out.println("Mov [" + (getNumeroJugada()) + "] [" + tablero.getJugadorActual().getNombre() + " /  " + tablero.getJugadorActual().getColorNombre() + "] [" + getMovimientoActual() + "]");
                    tablero.mover(movimiento);
                    tablero.finTurno();
                }
            }
        }
        tablero.verificarEstado();
    }

    public void anterior() {
        if (posActual > 0) {
            posActual--;
        }
        representarMovimientos();
    }

    public void inicio() {
        posActual = -1;
        representarMovimientos();
    }

    public void fin() {
        posActual = movimientos.size() - 1;
        representarMovimientos();
    }

    public String getMovimientoActual() {
        if (movimientos.size() > posActual && posActual >= 0) {
            return movimientos.get(posActual);
        } else {
            return "N/A";
        }
    }

    public void limpiarMovimientos() {
        movimientos.clear();
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
     * Reproduce una partida
     */
    public void reproducir() {
        getTablero().imprimir();
        while (isPuedeAvanzar() || !tablero.isJaqueMate()) {
            siguiente();
            getTablero().imprimir();
        }
    }

}
