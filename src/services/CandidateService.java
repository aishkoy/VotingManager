package services;

import models.Candidate;
import utils.JsonHandler;

import java.util.List;

public class CandidateService {
    private CandidateService(){}
    private static final List<Candidate> candidates = JsonHandler.readCandidatesJson("candidates.json");

    public static List<Candidate> getCandidates() {
        return candidates;
    }

    static{
        candidates
                .forEach(candidate -> {
                    if(candidate.getId() == 0){
                        candidate.setId(getMaxId()+1);
                    }
                });
        saveCandidates();
    }

    private static int getMaxId(){
        return candidates.stream()
                .mapToInt(Candidate::getId)
                .max()
                .orElse(0);
    }

    public static void saveCandidates(){
        JsonHandler.writeCandidatesJson("candidates.json", candidates);
    }
}
