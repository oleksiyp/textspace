package com.textspace.elements;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:27 PM
 */
public class CellRange {
    private final int x;
    private final int y;
    private final int w;
    private final int h;

    public CellRange(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
