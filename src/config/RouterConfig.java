package config;

import com.sun.net.httpserver.HttpExchange;
import server.handlers.RouteHandler;

import java.util.HashMap;
import java.util.Map;

public class RouterConfig {
    private RouterConfig() {}
    private static final Map<String, RouteHandler> routes = new HashMap<>();
    public static void registerPost(String route, RouteHandler handler) {
        registerGenericHandler("POST", route, handler);
    }

    public static void registerGet(String route, RouteHandler handler) {
        registerGenericHandler("GET", route, handler);
    }

    protected static void registerGenericHandler(String method, String route, RouteHandler handler) {
        routes.put(makeKey(method, route), handler);
    }

    private static String makeKey(String method, String route) {
        route = ensureStartsWithSlash(route);
        if (route.endsWith("/") && route.length() > 1) {
            route = route.substring(0, route.length() - 1);
        }
        return String.format("%s %s", method.toUpperCase(), route);
    }

    private static String makeKey(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        String route = exchange.getRequestURI().getPath();

        return makeKey(method, route);
    }

    private static String ensureStartsWithSlash(String route){
        if(route.startsWith(".")){
            return route;
        }
        return route.startsWith("/") ? route : "/" + route;
    }

    public static RouteHandler getHandler(HttpExchange exchange) {
        String key = makeKey(exchange);
        return routes.get(key);
    }
}
