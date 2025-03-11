package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import services.Cookie;
import utils.HttpUtils;

import java.util.Map;

import static utils.ResponseWriter.showError;

public class LogoutHandler {
    private LogoutHandler() {}

    public static void handle(HttpExchange exchange) {
        Map<String, Object> cookies = Cookie.parse(Cookie.getCookie(exchange));
        if(!cookies.containsKey("userId")){
            showError(exchange,"login.ftlh", "Вы не авторизованы!");
            return;
        }

       setDeleteCookie(exchange);
        HttpUtils.setSuccessCookie(exchange, "Вы успешно вышли из аккаунта!");
        HttpUtils.redirect303(exchange, "/login");
    }

    public static void setDeleteCookie(HttpExchange exchange){
        Cookie delUserCookie = Cookie.make("userId", "", 0, true);
        Cookie.setCookie(exchange, delUserCookie);
    }
}
