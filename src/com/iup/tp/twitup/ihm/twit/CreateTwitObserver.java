package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.datamodel.User;

public interface CreateTwitObserver {
    void createTwit(User user, String twit);
}