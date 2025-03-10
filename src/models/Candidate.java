package models;

public class Candidate {
    private int id;
    private final String name;
    private final String photo;
    private int votes;

    private double percentage;
    public Candidate(String name, String photo) {
        this.name = name;
        this.photo = photo;
    }

    public Candidate(int id, String name, String photo) {
        this(name, photo);
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
