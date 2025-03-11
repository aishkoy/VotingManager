package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import models.Candidate;
import models.User;
import services.CandidateService;
import services.UserService;
import utils.HttpUtils;
import utils.ResponseWriter;
import utils.UrlFormParser;

import java.util.Map;

import static utils.ResponseWriter.showError;
import static utils.UrlFormParser.safeParseInt;
import static utils.UrlFormParser.sanitizeField;

public class VoteHandler {
    private VoteHandler() {}
    public static void handleGet(HttpExchange exchange) {
        Map<String, Object> data = CandidateService.getCandidatesData();
        ResponseWriter.renderTemplate(exchange, "votes.ftlh", data);
    }

    public static void handlePost(HttpExchange exchange) {
        Map<String, Object> data = CandidateService.getCandidatesData();

        User user = AuthHandler.getAuthorisedUser(exchange);
        if(user == null){
            showError(exchange, "login.ftlh", "Вы еще не авторизованы!");
            return;}

        int candidateId = user.getCandidateId();
        if (candidateId > 0) {
            showError(exchange, data, "candidates.ftlh", "Вы уже проголосовали за кандидата!");
            return;
        }

        Map<String, Object> urlData = UrlFormParser.parseFormData(exchange);

        String candidateStr = sanitizeField(urlData.get("candidateId"));
        int canId = safeParseInt(candidateStr);

        Candidate candidate = CandidateService.getCandidateById(canId);
        if (candidate == null) {
            showError(exchange, data, "candidates.ftlh", "Неверный id кандидата!");
            return;
        }

        user.setCandidateId(canId);
        UserService.saveUsers();
        HttpUtils.redirect303(exchange, "/thankyou");
    }
}
