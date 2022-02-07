package com.splitwise.service;

import com.splitwise.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    Map<String, User> users;

    public UserService() {
        this.users = new HashMap<>();
    }

    public void addUsers(String userId, String name, String email, String phoneNumber) {
        if(users.containsKey(userId)) return;

        this.users.put(userId, new User(userId, name, email, phoneNumber));
    }

    public void addUsers(User user) {
        if(users.containsKey(user.getUserId())) return;

        this.users.put(user.getUserId(), user);
    }

    public Map<String, User> getUsers() {
        return this.users;
    }

    public User getUserByUserId(String userId) {
        return this.users.get(userId);
    }
}
