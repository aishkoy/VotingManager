package server;

import com.sun.net.httpserver.HttpExchange;
import config.RouterConfig;
import server.enums.ContentType;
import server.enums.HttpStatus;
import server.handlers.*;

import java.io.IOException;
import java.nio.file.Path;

public class RequestHandler {
    public RequestHandler() {
        registerRoutes();
    }
    private void registerRoutes() {
        RouterConfig.registerGet("/", MainHandler::handle);
    }

    public void handleRequest(HttpExchange exchange) throws IOException {
        RouteHandler handler = RouterConfig.getHandler(exchange);

        if(handler != null) {
            handler.handle(exchange);
            return;
        }

        Path filePath = FileHandler.resolveAndValidatePath(exchange.getRequestURI().getPath());
        if (filePath != null) {
            FileHandler.sendFile(exchange, filePath);
        } else {
            ResponseWriter.sendResponse(exchange, ContentType.PLAIN_TEXT, HttpStatus.NOT_FOUND);
        }
    }
}
