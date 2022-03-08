package com.iup.tp.twitup.ihm.connexion;

public interface ConnexionObserver {

    void connect(String username, String password);

    void disconnectUser();
}
