package com.iup.tp.twitup.ihm.createUser;

public interface CreationObserver {

    void register(String tag, String password, String name, String pathToAvatar);
}
