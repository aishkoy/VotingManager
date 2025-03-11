package utils;

import com.sun.net.httpserver.HttpExchange;
import server.enums.ContentType;
import server.enums.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileHandler {
    private FileHandler(){}
    private static final Path HOME_PATH = Paths.get("static/");

    public static Path resolveAndValidatePath(String path) {
        if (path == null || path.isEmpty()) {
            return HOME_PATH.resolve("canditates.flth");
        }

        while (path.length() > 1 && path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (path.equals("/") || path.isEmpty()) {
            return HOME_PATH.resolve("canditates.flth");
        }

        Path filePath = HOME_PATH.resolve(path.substring(1));
        System.out.println("Resolving path: " + filePath.toAbsolutePath());
        return isFileValid(filePath) ? filePath : null;
    }

    public static boolean isFileValid(Path path) {
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public static void sendFile(HttpExchange exchange, Path filePath) throws IOException {
        ContentType contentType = ContentType.getTypeFromPath(filePath.toString());
        exchange.getResponseHeaders().set("Content-Type", contentType.getMimeType());
        exchange.sendResponseHeaders(HttpStatus.OK.getCode(), Files.size(filePath));

        try (OutputStream os = exchange.getResponseBody();
             InputStream is = Files.newInputStream(filePath)) {
            is.transferTo(os);
        }
    }

}
