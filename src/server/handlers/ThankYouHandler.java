package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import models.Candidate;
import models.User;
import services.CandidateService;
import utils.ResponseWriter;

import java.util.Map;

import static utils.ResponseWriter.showError;

public class ThankYouHandler {
    private ThankYouHandler() {}
    public static void handle(HttpExchange exchange) {
        Map<String, Object> data = CandidateService.getCandidatesData();

        User user = AuthHandler.getAuthorisedUser(exchange);
        if (user == null) {
            showError(exchange, "login.ftlh", "Вы еще не авторизованы!");
            return;
        }

        Candidate candidate = CandidateService.getCandidateById(user.getCandidateId());
        if (candidate == null) {
            showError(exchange, data, "candidates.ftlh", "Вы еще не проголосовали!");
            return;
        }

        data.put("candidate", candidate);
        ResponseWriter.renderTemplate(exchange, "thankyou.ftlh", data);
    }
}
