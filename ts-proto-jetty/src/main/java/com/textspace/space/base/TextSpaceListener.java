package com.textspace.space.base;

import com.textspace.elements.CellCoordinate;

import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:13 PM
 */
public interface TextSpaceListener {
    void cellChanged(CellCoordinate coord, char ch) throws IOException;
}
