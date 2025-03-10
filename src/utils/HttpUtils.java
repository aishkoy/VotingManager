package utils;

import com.sun.net.httpserver.HttpExchange;
import services.Cookie;
import server.enums.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

public class HttpUtils {
    private HttpUtils() {}
    public static void redirect303(HttpExchange exchange, String path){
        try{
            exchange.getResponseHeaders().add("Location", path);
            exchange.sendResponseHeaders(HttpStatus.SEE_OTHER.getCode(), 0);
            exchange.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String getBody(HttpExchange exchange) {
        InputStream inputStream = exchange.getRequestBody();
        Charset charset = StandardCharsets.UTF_8;
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, charset);

        try(BufferedReader reader = new BufferedReader(inputStreamReader)) {
            return reader.lines().collect(Collectors.joining(""));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String getQueryParams(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        return Objects.nonNull(query) ? query : "";
    }

    public static void setSuccessCookie(HttpExchange exchange, String message) {
        Cookie errorCookie = Cookie.make("successMessage", message, 3, true);
        Cookie.setCookie(exchange, errorCookie);
    }
}
