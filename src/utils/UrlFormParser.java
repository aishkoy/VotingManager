package utils;

import com.sun.net.httpserver.HttpExchange;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static utils.ResponseWriter.showError;

public class UrlFormParser {
    private UrlFormParser() {}

    private static Optional<Map.Entry<String, Object>> decode(String kv) {
        if (!kv.contains("=")) {
            return Optional.empty();
        }

        String[] pairs = kv.split("=", 2);
        if (pairs.length != 2) {
            return Optional.empty();
        }

        Charset utf8 = StandardCharsets.UTF_8;
        String key = URLDecoder.decode(pairs[0], utf8);
        String value = URLDecoder.decode(pairs[1], utf8);
        return Optional.of(Map.entry(key, (Object) value));
    }
    public static Map<String, Object> parseUrlEncoded(String rawLines, String delimiter) {
        String[] lines = rawLines.strip().split(delimiter);
        Map<String, Object> result = new HashMap<>();

        Arrays.stream(lines)
                .map(UrlFormParser::decode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(entry -> !entry.getKey().isEmpty())
                .forEach(entry -> result.put(entry.getKey(), entry.getValue()));

        return result;
    }

    public static Map<String, Object> parseFormData(HttpExchange exchange) {
        return UrlFormParser.parseUrlEncoded(HttpUtils.getBody(exchange), "&");
    }

    public static boolean areFieldsValid(HttpExchange exchange,
                                         String template, String... fields) {
        for (String field : fields) {
            if (field == null || field.isEmpty()) {
                showError(exchange, template, "Все поля должны быть заполнены.");
                return true;
            }
        }
        return false;
    }

    public static String sanitizeField(Object field) {
        return field == null || field.toString().trim().isEmpty() ? "" : field.toString().trim();
    }
    public static int safeParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
