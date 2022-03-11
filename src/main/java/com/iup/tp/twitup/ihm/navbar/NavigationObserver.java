package com.iup.tp.twitup.ihm.navbar;

import com.iup.tp.twitup.datamodel.User;

public interface NavigationObserver {

    void goToRegister(String error);
    void goToConnexion(String error);

    void goToProfile(User user);
    void goToUsers();
    void goToTwits();
    void goToExplorer();
}