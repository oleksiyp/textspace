package com.textspace.space.base;

import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;

import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 9:33 PM
 */
public interface TextSpaceOutput {
    void cell(CellCoordinate coord, char ch) throws IOException;

    void range(CellRange range, char[][] values) throws IOException;

    void reposition(CellCoordinate coord) throws IOException;
}
