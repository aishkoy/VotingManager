package utils;

import com.sun.net.httpserver.HttpExchange;
import config.FreemarkerConfig;
import server.enums.ContentType;
import server.enums.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class ResponseWriter {
    private ResponseWriter() {}
    private static final String CONTENT_TYPE = "Content-Type";

    public static void showError(HttpExchange exchange, String template, String errorMessage) {
        ResponseWriter.renderTemplate(exchange, template, Map.of("error", errorMessage));
    }

    public static void showError(HttpExchange exchange, Map<String, Object> data, String template, String errorMessage) {
        data.put("error", errorMessage);
        ResponseWriter.renderTemplate(exchange, template, data);
    }
    public static void sendResponse(HttpExchange exchange, ContentType contentType, HttpStatus status){
        try {
            exchange.getResponseHeaders().set(CONTENT_TYPE, contentType.getMimeType());
            exchange.sendResponseHeaders(status.getCode(), status.toString().getBytes(StandardCharsets.UTF_8).length);

            try (OutputStreamWriter writer = new OutputStreamWriter(exchange.getResponseBody(), StandardCharsets.UTF_8)) {
                writer.write(status.toString());
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendResponse(HttpExchange exchange, byte[] responseBytes,
                                    ContentType contentType, HttpStatus status) {
        try {
            exchange.getResponseHeaders().set(CONTENT_TYPE, contentType.getMimeType());
            exchange.sendResponseHeaders(status.getCode(), responseBytes.length);

            try (OutputStream outputStream = exchange.getResponseBody()) {
                outputStream.write(responseBytes);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderTemplate(HttpExchange exchange, String template, Map<String, Object> params) {
        byte[] response = FreemarkerConfig.render(template, params);
        ResponseWriter.sendResponse(exchange, response, ContentType.HTML, HttpStatus.OK);
    }
}
