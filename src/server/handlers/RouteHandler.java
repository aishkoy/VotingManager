package server.handlers;

import com.sun.net.httpserver.HttpExchange;
public interface RouteHandler {
    void handle(HttpExchange exchange);
}