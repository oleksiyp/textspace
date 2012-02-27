package com.textspace.space;

import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;
import com.textspace.space.base.TextSpaceListener;
import com.textspace.space.base.TextSpaceOutput;

import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/24/12
 * Time: 12:15 AM
 */
public class UserState {
    private CellRange viewRange;

    private CellCoordinate cursor = new CellCoordinate(0, 0);

    private CellCoordinate inputLine = new CellCoordinate(0, 0);

    private TextSpaceOutput output;

    private TextSpaceListener viewRangeListener;

    public UserState(TextSpaceOutput output) {
        this.output = output;
    }

    public void offsetCursor(int offX, int offY) throws IOException {
        if (offX == 0 && offY == 0) {
            return;
        }
        setCursor(new CellCoordinate(cursor.getX() + offX, cursor.getY() + offY));
    }

    public void setCursor(int x, int y) throws IOException {
        offsetCursor(x - cursor.getX(), y - cursor.getY());
    }

    public void setCursor(CellCoordinate value) throws IOException {
        cursor = value;
        if (output != null) {
            output.reposition(cursor);
        }
    }

    public CellCoordinate getInputLine() {
        return inputLine;
    }

    public CellRange getViewRange() {
        return viewRange;
    }

    public void setViewRange(CellRange viewRange) {
        this.viewRange = viewRange;
    }

    public CellCoordinate getCursor() {
        return cursor;
    }

    public TextSpaceListener getViewRangeListener() {
        return viewRangeListener;
    }

    public void setViewRangeListener(RangeTextSpaceFilter viewRangeListener) {
        this.viewRangeListener = viewRangeListener;
    }

    public TextSpaceOutput getOutput() {
        return output;
    }

    public void setInputLine(CellCoordinate inputLine) {
        this.inputLine = inputLine;
    }
}
