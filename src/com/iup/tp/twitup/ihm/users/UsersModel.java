package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UsersModel {

    protected Set<User> users;

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsersExcepted(User user) {
        return users.stream().filter(u -> !u.getUserTag().equals(user.getUserTag())).collect(Collectors.toSet());
    }
}
