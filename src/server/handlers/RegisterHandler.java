package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import models.User;
import services.CandidateService;
import services.Cookie;
import services.UserService;
import utils.HttpUtils;
import utils.ResponseWriter;
import utils.UrlFormParser;

import java.util.HashMap;
import java.util.Map;

import static utils.ResponseWriter.showError;
import static utils.UrlFormParser.*;

public class RegisterHandler {
    private RegisterHandler() {
    }

    public static void handlePost(HttpExchange exchange) {
        Map<String, Object> data = UrlFormParser.parseFormData(exchange);

        String email = sanitizeField(data.get("email"));
        String name = sanitizeField(data.get("name"));
        String password = sanitizeField(data.get("password"));

        if (areFieldsValid(exchange, "register.ftlh", email, name, password)) return;

        if (UserService.isEmailTaken(email)) {
            showError(exchange, "register.ftlh", "Этот email уже зарегистрирован");
            return;
        }

        UserService.addUser(new User(name, email, password));
        HttpUtils.setSuccessCookie(exchange, "Вы успешно зарегистрировались! Введите логин и пароль, чтобы войти.");
        HttpUtils.redirect303(exchange, "/login");
    }

    public static void handleGet(HttpExchange exchange) {
        Map<String, Object> cookies = Cookie.parse(Cookie.getCookie(exchange));
        Map<String, Object> data = new HashMap<>();

        if(cookies.containsKey("userId")){
            data.put("candidates", CandidateService.getCandidates());
            data.put("user", UserService.getUserById(safeParseInt(cookies.get("userId").toString())));
            showError(exchange, data,"candidates.ftlh", "Вы уже авторизованы");
            return;
        }

        data.put("success", cookies.get("successMessage"));
        ResponseWriter.renderTemplate(exchange, "register.ftlh", data);
    }
}
