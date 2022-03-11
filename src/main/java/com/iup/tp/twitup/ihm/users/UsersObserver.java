package com.iup.tp.twitup.ihm.users;

public interface UsersObserver {

    void setFollow(String tag, boolean unFollow);

    void search(String search);
}
