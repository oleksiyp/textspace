package com.textspace.elements;

import java.io.Serializable;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:15 PM
 */
public class CellCoordinate implements Serializable {
    private final int x;
    private final int y;

    public CellCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellCoordinate that = (CellCoordinate) o;

        if (x != that.x) return false;
        if (y != that.y) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "CellCoordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public boolean in(CellRange range) {
        return (range.getX() <= x && x < range.getX() + range.getW())
                && (range.getY() <= y && y < range.getY() + range.getH());
    }
}
