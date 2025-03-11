package services;

import models.Candidate;
import utils.JsonHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CandidateService {
    private CandidateService() {}
    private static final List<Candidate> candidates = JsonHandler.readCandidatesJson("candidates.json");

    static {
        candidates
                .forEach(candidate -> {
                    if (candidate.getId() == 0) {
                        candidate.setId(getMaxId() + 1);
                    }
                });
        saveCandidates();
    }
    private static int getMaxId() {
        return candidates.stream()
                .mapToInt(Candidate::getId)
                .max()
                .orElse(0);
    }

    public static Candidate getCandidateById(int id) {
        return candidates.stream().filter(candidate -> candidate.getId() == id).findFirst().orElse(null);
    }

    public static Map<String, Object> getCandidatesData() {
        Map<String, Object> data = new HashMap<>();
        candidates.forEach(candidate -> {
            candidate.setVotes(VoteStatisticsService.getVotesForCandidate(candidate.getId()));
            candidate.setPercentage(VoteStatisticsService.getPercentageForCandidate(candidate.getId()));
        });

        candidates.sort((c1, c2) -> {
                    int percentageCompare = Double.compare(c2.getPercentage(), c1.getPercentage());
                    if (percentageCompare != 0) {
                        return percentageCompare;
                    }
                    return c1.getName().compareTo(c2.getName());
                });

        CandidateService.saveCandidates();
        data.put("candidates", candidates);
        return data;
    }
    public static void saveCandidates() {
        JsonHandler.writeCandidatesJson("candidates.json", candidates);
    }
}
