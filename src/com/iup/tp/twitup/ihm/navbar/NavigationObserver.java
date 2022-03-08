package com.iup.tp.twitup.ihm.navbar;

public interface NavigationObserver {

    void goToRegister(String error);
    void goToConnexion(String error);

    void goToProfile();
    void goToUsers();
    void goToTwits();
    void goToExplorer();
}