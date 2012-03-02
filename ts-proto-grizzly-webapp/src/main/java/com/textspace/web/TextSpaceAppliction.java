package com.textspace.web;

import com.sun.grizzly.tcp.Request;
import com.sun.grizzly.websockets.ProtocolHandler;
import com.sun.grizzly.websockets.WebSocket;
import com.sun.grizzly.websockets.WebSocketApplication;
import com.sun.grizzly.websockets.WebSocketListener;
import com.textspace.space.TextSpaceCenter;

/**
 * User: captain-protect
 * Date: 2/28/12
 * Time: 3:09 PM
 */
public class TextSpaceAppliction extends WebSocketApplication {
    private TextSpaceCenter center = new TextSpaceCenter();

    @Override
    public boolean isApplicationRequest(Request request) {
        return "/socket".equals(request.requestURI().getString());
    }

    @Override
    public WebSocket createWebSocket(ProtocolHandler protocolHandler, WebSocketListener... listeners) {
        return new TextSpaceWebSocket(center, protocolHandler, listeners);
    }
}
