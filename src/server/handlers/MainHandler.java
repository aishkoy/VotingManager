package server.handlers;

import com.sun.net.httpserver.HttpExchange;
import services.CandidateService;
import utils.ResponseWriter;

import java.util.Map;

public class MainHandler {
    private MainHandler(){}
    public static void handle(HttpExchange exchange) {
        Map<String, Object> data = CandidateService.getCandidatesData();
        ResponseWriter.renderTemplate(exchange, "candidates.ftlh", data);
    }
}
