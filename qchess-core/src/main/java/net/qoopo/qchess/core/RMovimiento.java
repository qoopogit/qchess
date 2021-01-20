/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

/**
 * Clase que contiene la respuesta de un movimiento
 *
 * @author alberto
 */
public class RMovimiento {

    private Movimiento movimiento = null;
    private boolean satisfactorio = false;

    public RMovimiento() {
    }

    public RMovimiento(Movimiento movimiento, boolean satisfactorio) {
        this.movimiento = movimiento;
        this.satisfactorio = satisfactorio;
    }

    public Movimiento getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
    }

    public boolean isSatisfactorio() {
        return satisfactorio;
    }

    public void setSatisfactorio(boolean satisfactorio) {
        this.satisfactorio = satisfactorio;
    }

    public static RMovimiento getFalse() {
        return new RMovimiento(null, false);
    }

    public static RMovimiento get(Movimiento mov) {
        return new RMovimiento(mov, mov != null);
    }

}
