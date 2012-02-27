package com.textspace.space;

import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;
import com.textspace.space.base.TextSpaceListener;

import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:31 PM
 */
public class RangeTextSpaceFilter implements TextSpaceListener {
    private final CellRange range;

    private final TextSpaceListener listener;

    RangeTextSpaceFilter(CellRange range, TextSpaceListener listener) {
        this.range = range;
        this.listener = listener;
    }

    public CellRange getRange() {
        return range;
    }

    public TextSpaceListener getListener() {
        return listener;
    }

    public static TextSpaceListener decorate(TextSpaceListener listener, CellRange range) {
        return new RangeTextSpaceFilter(range, listener);
    }

    public void cellChanged(CellCoordinate coord, char ch) throws IOException {
        if (coord.in(range)) {
            listener.cellChanged(coord, ch);
        }
    }
}
