package de.chessy.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private UserRepository() {
        createUser("Michi");
        createUser("Julian");
    }


    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    private int currentId = 0;

    private final Map<Integer, User> users = new HashMap<>();

    public User findUserById(int id) {
        return users.get(id);
    }

    public List<User> findAllUsers() {
        return new ArrayList<>(users.values());
    }

    public void createUser(String name) {
        int id = currentId++;
        users.put(id, new User(id, name));
    }
}
