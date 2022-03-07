package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;

import java.util.Set;
import java.util.stream.Collectors;

public class UsersModel {

    protected Set<User> users;

    protected User connectedUser;

    public Set<User> getUsers() {
        return users;
    }

    public User getConnectedUser() {
        return connectedUser;
    }

    public void setConnectedUser(User connectedUser) {
        this.connectedUser = connectedUser;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<User> getUsersButConnected() {

        return users.stream().filter(u -> !u.getUserTag().equals(connectedUser.getUserTag())).collect(Collectors.toSet());
    }
}
