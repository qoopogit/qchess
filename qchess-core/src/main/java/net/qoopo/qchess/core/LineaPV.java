/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

/**
 * Clase qeu representa la variaciones de jugadas
 *
 * @author alberto
 */
public class LineaPV {

    /**
     * La cantidad de movimientos de esta linea de variaciones
     */
    private int cantidad;

    /**
     * String que representa las jugadas
     */
    private StringBuilder PV = new StringBuilder();

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public StringBuilder getPV() {
        return PV;
    }

    public void setPV(StringBuilder PV) {
        this.PV = PV;
    }

    public LineaPV agregar(String mov) {
        PV.append(" ").append(mov);
        cantidad++;
        return this;
    }

    public LineaPV agregar(LineaPV pv) {
        PV.append(pv.getPV().toString());
        cantidad += pv.cantidad;
        return this;
    }

    @Override
    public String toString() {
        return "PV:" + PV.toString().trim() + " N:" + cantidad;
    }

}
