package com.textspace.space;

import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;
import com.textspace.space.base.TextSpaceListener;

import java.io.*;
import java.util.*;

/**
 * User: captain-protect
 * Date: 2/23/12
 * Time: 8:11 PM
 */
public class TextSpace {
    private final List<TextSpaceListener> listeners = new ArrayList<TextSpaceListener>();

    private Map<CellCoordinate, Character> values = new HashMap();

    public void put(CellCoordinate coord, char ch) {
        if (ch == ' ') {
            synchronized (this) {
                values.remove(coord);
            }
        } else {
            synchronized (this) {
                values.put(coord, ch);
            }
        }
        notifyCellChange(coord, ch);
    }

    public char get(CellCoordinate coord) {
        Character ch;
        synchronized (this) {
            ch = values.get(coord);
        }
        if (ch == null) {
            return ' ';
        } else {
            return ch;
        }
    }

    public void saveTo(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fos);
        try {
            synchronized (this) {
                out.writeObject(values);
            }
        } finally {
            out.close();
        }
    }

    public void loadFrom(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream in = new ObjectInputStream(fis);
        try {
            synchronized (this) {
                try {
                    values = (Map<CellCoordinate, Character>) in.readObject();
                } catch (ClassNotFoundException e) {
                    // skip
                    e.printStackTrace();
                }
            }
        } finally {
            in.close();
        }
    }

    public char[][] getRange(CellRange range) {
        int x = range.getX();
        int y = range.getY();
        int w = range.getW();
        int h = range.getH();
        if (w < 0) {
            w = 0;
        }
        if (h < 0) {
            h = 0;
        }

        char [][]result = new char[h][];
        for (int j = 0; j < h; j++) {
            char []arr = new char[w];
            Arrays.fill(arr, ' ');
            for (int i = 0; i < w; i++) {
                CellCoordinate coordinate = new CellCoordinate(i + x, j + y);

                Character ch = values.get(coordinate);
                if (ch == null) {
                    continue;
                }
                arr[i] = ch;
            }
            result[j] = arr;
        }
        return result;
    }

    public void addListener(TextSpaceListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }

    public void removeListener(TextSpaceListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }

    private void notifyCellChange(CellCoordinate coord, char ch) {
        synchronized (listeners) {
            List<TextSpaceListener> toDelete = new ArrayList<TextSpaceListener>(listeners.size());
            for (TextSpaceListener listener : listeners)  {
                try {
                    listener.cellChanged(coord, ch);
                } catch (IOException ioex) {
                    toDelete.add(listener);
                }
            }
            listeners.removeAll(toDelete);
        }
    }
}
