package server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private final HttpServer httpServer;
    private final RequestHandler requestHandler;

    public Server(String host, int port) throws IOException {
        this.httpServer = createServer(host, port);
        this.requestHandler = new RequestHandler();
        initRoutes();
    }

    public void start() {
        httpServer.start();
    }

    private HttpServer createServer(String host, int port) throws IOException {
        System.out.printf("Starting server on http://%s:%d%n", host, port);
        InetSocketAddress address = new InetSocketAddress(host, port);
        return HttpServer.create(address, 50);
    }

    private void initRoutes(){
        httpServer.createContext("/", requestHandler::handleRequest);
    }
}
