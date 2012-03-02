package com.textspace.web;

import com.textspace.space.base.TextSpaceInput;
import com.textspace.space.base.TextSpaceOutput;
import com.textspace.space.UserState;
import com.textspace.elements.CellCoordinate;
import com.textspace.elements.CellRange;
import org.eclipse.jetty.websocket.WebSocket;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
* User: captain-protect
* Date: 2/23/12
* Time: 9:38 PM
*/
public class TextSpaceWebSocket implements WebSocket, WebSocket.OnTextMessage, TextSpaceOutput
{
    private static final Logger LOGGER = Logger.getLogger(TextSpaceWebSocket.class.getName());

    private Connection connection;

    private final TextSpaceInput input;

    private final UserState state = new UserState(this);

    public TextSpaceWebSocket(TextSpaceInput input) {
        this.input = input;
    }

    public void onOpen(Connection connection) {
        this.connection = connection;
    }

    public void onMessage(String data) {
        String []commands = data.split(";");
        for (String cmd : commands) {
            try {
                processCommand(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processCommand(String cmd) throws IOException {
        String []arr = cmd.split(",");
        if (arr[0].equals("key")) {
            char ch = (char) Integer.parseInt(arr[1]);
            input.key(state, ch);
        } else if (arr[0].equals("reposition")) {
            int x = Integer.parseInt(arr[1]);
            int y = Integer.parseInt(arr[2]);
            input.reposition(state, new CellCoordinate(x, y));
        } else if (arr[0].equals("range")) {
            int x = Integer.parseInt(arr[1]);
            int y = Integer.parseInt(arr[2]);
            int w = Integer.parseInt(arr[3]);
            int h = Integer.parseInt(arr[4]);
            input.range(state, new CellRange(x, y, w, h));
        }
    }

    public void onClose(int closeCode, String message) {
        connection.close(closeCode, message);
    }

    public void range(CellRange range, char[][] values) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("range,");
        builder.append(Integer.toString(range.getX()));
        builder.append(",");
        builder.append(Integer.toString(range.getY()));
        builder.append(",");
        builder.append(Integer.toString(range.getW()));
        builder.append(",");
        builder.append(Integer.toString(range.getH()));
        builder.append(",");
        int repeat = -1;
        char value = 0;

        for (char []row : values) {
            for (char c : row) {
                if (repeat == -1) {
                    repeat = 1;
                    value = c;
                } else if (value == c) {
                    repeat++;
                } else {
                    builder.append(repeat);
                    builder.append(",");
                    builder.append(Integer.toString(value));
                    builder.append(",");
                    repeat = 1;
                    value = c;
                }
            }
        }
        builder.append(repeat);
        builder.append(",");
        builder.append(Integer.toString(value));
        builder.append(",");
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Command sent: " + builder.toString());
        }
        connection.sendMessage(builder.toString());
    }

    public void cell(CellCoordinate coord, char ch) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("cell,");
        builder.append(Integer.toString(coord.getX()));
        builder.append(",");
        builder.append(Integer.toString(coord.getY()));
        builder.append(",");
        builder.append(Integer.toString(ch));
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Command sent: " + builder.toString());
        }
        connection.sendMessage(builder.toString());
    }

    public void reposition(CellCoordinate coord) throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append("reposition,");
        builder.append(Integer.toString(coord.getX()));
        builder.append(",");
        builder.append(Integer.toString(coord.getY()));
        if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Command sent: " + builder.toString());
        }
        connection.sendMessage(builder.toString());
    }

}
