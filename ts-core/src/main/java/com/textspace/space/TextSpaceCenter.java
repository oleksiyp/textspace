package com.textspace.space;

import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;
import com.textspace.elements.CharCodes;
import com.textspace.space.base.TextSpaceInput;
import com.textspace.space.base.TextSpaceListener;

import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:58 PM
 */
public class TextSpaceCenter implements TextSpaceInput {
    private final TextSpace space = new TextSpace();

    public void key(UserState state, char ch) throws IOException {
        switch (ch) {
            case CharCodes.ENTER:
                state.setCursor(state.getInputLine());
                while (space.get(state.getCursor()) != ' ') {
                    state.offsetCursor(0, 1);
                }
                state.setInputLine(state.getCursor());
                break;
            case CharCodes.BACKSPACE:
                state.offsetCursor(-1, 0);
                space.put(state.getCursor(), ' ');
                break;
            case CharCodes.LEFT: state.offsetCursor(-1, 0); break;
            case CharCodes.RIGHT: state.offsetCursor(1, 0); break;
            case CharCodes.UP: state.offsetCursor(0, -1); break;
            case CharCodes.DOWN: state.offsetCursor(0, 1); break;
            default:
                space.put(state.getCursor(), ch);
                state.offsetCursor(1, 0);
                break;
        }
    }

    public synchronized void reposition(UserState state, CellCoordinate coord) throws IOException {
        state.setCursor(coord.getX(), coord.getY());
    }

    public synchronized void range(final UserState state, CellRange range) throws IOException {
        if (state.getViewRange() == null || !range.equals(state.getViewRange())) {
            if (state.getViewRangeListener() != null) {
                space.removeListener(state.getViewRangeListener());
            }
            state.setViewRange(range);
            RangeTextSpaceFilter listener = new RangeTextSpaceFilter(range, new TextSpaceListener() {
                public void cellChanged(CellCoordinate coord, char ch) throws IOException {
                    state.getOutput().cell(coord, ch);
                }
            });
            state.setViewRangeListener(listener);
            space.addListener(listener);
        }
        char[][] values = space.getRange(range);
        state.getOutput().range(range, values);
    }

    public TextSpace getTextSpace() {
        return space;
    }
}
