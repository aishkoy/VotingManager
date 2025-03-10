package services;

import models.User;

import java.util.List;

public class VoteStatisticsService {
    private VoteStatisticsService() {}
    private static final List<User> users = UserService.getUsers();

    public static int getVotesForCandidate(int candidateId) {
        return (int) users.stream()
                .filter(user -> user.getCandidateId() == candidateId)
                .count();
    }

    public static double getPercentageForCandidate(int candidateId) {
        int totalVotes = getTotalVotes();
        if (totalVotes == 0) return 0.0;

        return getVotesForCandidate(candidateId) * 100.0 / totalVotes;
    }

    private static int getTotalVotes(){
        return (int) users.stream()
                .filter(user -> user.getCandidateId() > 0)
                .count();
    }
}
