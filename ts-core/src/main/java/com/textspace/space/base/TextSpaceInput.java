package com.textspace.space.base;

import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;
import com.textspace.space.UserState;

import java.io.IOException;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:41 PM
 */
public interface TextSpaceInput {
    void key(UserState state, char ch) throws IOException;

    void reposition(UserState state, CellCoordinate coord) throws IOException;

    void range(UserState state, CellRange range) throws IOException;
}
