package models;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private int candidateId;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password, int candidateId) {
        this(name, email, password);
        this.candidateId = candidateId;
    }

    public User(int id, String name, String email, String password, int candidateId) {
        this(name, email, password, candidateId);
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

    public String getEmail() {
        return email;
    }


    public int getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(int candidateId) {
        this.candidateId = candidateId;
    }
}
