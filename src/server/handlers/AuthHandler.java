package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import models.User;
import services.CandidateService;
import services.Cookie;
import services.UserService;
import utils.HttpUtils;
import utils.ResponseWriter;
import utils.UrlFormParser;

import java.util.Map;

import static utils.ResponseWriter.showError;
import static utils.UrlFormParser.*;

public class AuthHandler {
    private AuthHandler() {}
    public static void handleGet(HttpExchange exchange) {
        Map<String, Object> cookies = Cookie.parse(Cookie.getCookie(exchange));
        Map<String, Object> data = CandidateService.getCandidatesData();

        if (cookies.containsKey("userId")) {
            User user = getAuthorisedUser(exchange);
            if (user != null) {
                data.put("user", user);
                showError(exchange, data,"candidates.ftlh", "Вы уже авторизованы");
                return;
            }
        }

        String path = exchange.getRequestURI().getPath();
        String template = path.contains("login") ? "login.ftlh" : "register.ftlh";

        data.put("success", cookies.get("successMessage"));
        ResponseWriter.renderTemplate(exchange, template, data);
    }

    public static void handleLoginPost(HttpExchange exchange) {
        Map<String, Object> data = UrlFormParser.parseFormData(exchange);
        String email = sanitizeField(data.get("email"));
        String password = sanitizeField(data.get("password"));

        if(areFieldsValid(exchange, "login.ftlh", email, password)) return;

        User logUser = UserService.authenticate(email, password);
        if (logUser == null) {
            showError(exchange,"login.ftlh", "Неверный email или пароль.");
            return;
        }

        Cookie userCookie = Cookie.make("userId", logUser.getId(), 600, true);
        Cookie.setCookie(exchange, userCookie);
        HttpUtils.redirect303(exchange, "/");
    }

    public static void handleRegisterPost(HttpExchange exchange) {
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
    public static User getAuthorisedUser(HttpExchange exchange) {
        Map<String, Object> cookies = Cookie.parse(Cookie.getCookie(exchange));
        int userId = safeParseInt(cookies.getOrDefault("userId", "").toString());

        if (userId <= 0) {
            LogoutHandler.setDeleteCookie(exchange);
            return null;
        }

        User user = UserService.getUserById(userId);
        if(user == null){
            LogoutHandler.setDeleteCookie(exchange);
            return null;
        }

        return user;
    }
}
