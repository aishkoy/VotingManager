package services;

import models.User;
import utils.JsonHandler;

import java.util.List;

public class UserService {
    private UserService(){}
    private static final List<User> users = JsonHandler.readUsersJson("users.json");

    private static int getMaxId(){
        return users.stream()
                .mapToInt(User::getId)
                .max()
                .orElse(0);
    }

    public static boolean isEmailTaken(String email) {
        return users.stream().anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }

    public static User getUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
    }

    public static void addUser(User user) {
        if(user.getId() == 0){
            user.setId(getMaxId() + 1);
        }
        users.add(user);
        saveUsers();
    }

    public static List<User> getUsers() {
        return users;
    }

    public  static void saveUsers() {
        JsonHandler.writeUsersJson("users.json", users);
    }

}
