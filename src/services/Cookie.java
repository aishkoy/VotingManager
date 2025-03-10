package services;


import com.sun.net.httpserver.HttpExchange;
import utils.UrlFormParser;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Cookie<V> {
    private final String name;
    private final V value;
    private Integer maxAge;
    private boolean httpOnly;

    private Cookie(String name, V value) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(value);
        this.name = name.strip();
        this.value = value;
    }

    private Cookie(String name, V value, Integer maxAge, boolean httpOnly) {
        this(name, value);
        this.maxAge = maxAge;
        this.httpOnly = httpOnly;
    }


    public static <V> Cookie make(String name, V value, int maxAge, boolean httpOnly) {
        return new Cookie<>(name, value, maxAge, httpOnly);
    }

    private V getValue(){return this.value;}
    public String getName() {return this.name;}
    public Integer getMaxAge() {return this.maxAge;}
    public boolean isHttpOnly() {return this.httpOnly;}

    public static Map<String, Object> parse(String cookieString) {
        return UrlFormParser.parseUrlEncoded(cookieString, "; ");
    }

    public static String getCookie(HttpExchange exchange){
        return exchange.getRequestHeaders()
                .getOrDefault("Cookie", List.of(""))
                .getFirst();
    }

    public static void setCookie(HttpExchange exchange, Cookie cookies){
        exchange.getResponseHeaders().add("Set-Cookie", cookies.toString());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Charset utf8 = StandardCharsets.UTF_8;
        String encName = URLEncoder.encode(getName().strip(), utf8);
        String stringValue = getValue().toString();
        String encValue = URLEncoder.encode(stringValue, utf8);

        sb.append(String.format("%s=%s", encName, encValue));

        if(getMaxAge() != null) {
            sb.append("; Max-Age=").append(getMaxAge());
        }

        if(isHttpOnly()) {
            sb.append("; HttpOnly");
        }
        return sb.toString();
    }
}
