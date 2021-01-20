/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.qoopo.qchess.core;

/**
 *
 * @author alberto
 */
public class Position {

    public int col = -1;
    public int row = -1;

    public boolean isSet() {
        return col != -1 && row != -1;
    }
//
//    public boolean equals(Position other) {
//        return col == other.col && row == other.row;
//    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.col;
        hash = 97 * hash + this.row;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.col != other.col) {
            return false;
        }
        if (this.row != other.row) {
            return false;
        }
        return true;
    }

}
