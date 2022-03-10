package com.iup.tp.twitup.ihm.connexion;

import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.users.UsersListener;

import java.util.ArrayList;
import java.util.List;

public class ConnectedUserModel {

    protected List<UsersListener> usersListeners;

    protected User userConnected;

    public ConnectedUserModel() {
        this.usersListeners = new ArrayList<>();
    }

    public User getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(User userConnected) {
        this.userConnected = userConnected;
        this.usersListeners.forEach(UsersListener::update);
    }

    public void addUsersListener(UsersListener usersListener) {
        this.usersListeners.add(usersListener);
    }
}
