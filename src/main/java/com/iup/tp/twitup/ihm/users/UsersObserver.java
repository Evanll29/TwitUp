package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.Twit;

public interface UsersObserver {

    void setFollow(String tag, boolean unFollow);

    void search(String search);
}
