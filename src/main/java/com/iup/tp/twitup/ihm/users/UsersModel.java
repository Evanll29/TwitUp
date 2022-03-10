package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UsersModel {

    protected List<UsersListener> usersListeners;

    protected Set<User> users;

    public UsersModel() {
        this.usersListeners = new ArrayList<>();
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
        this.usersListeners.forEach(UsersListener::update);
    }

    public Set<User> getUsersExcepted(User user) {
        return users.stream().filter(u -> !u.getUserTag().equals(user.getUserTag())).collect(Collectors.toSet());
    }

    public void addUsersListener(UsersListener usersListener) {
        this.usersListeners.add(usersListener);
    }
}
