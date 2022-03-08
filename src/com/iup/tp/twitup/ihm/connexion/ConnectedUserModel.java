package com.iup.tp.twitup.ihm.connexion;

import com.iup.tp.twitup.datamodel.User;

public class ConnectedUserModel {

    protected User userConnected;

    public User getUserConnected() {
        return userConnected;
    }

    public void setUserConnected(User userConnected) {
        this.userConnected = userConnected;
    }
}
