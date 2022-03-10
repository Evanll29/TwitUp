package com.iup.tp.twitup.core.controllers;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.explorer.ExplorerObserver;
import com.iup.tp.twitup.ihm.twit.CreateTwitObserver;
import com.iup.tp.twitup.ihm.twit.TwitsModel;

public class TwitController implements ExplorerObserver, CreateTwitObserver {
    /**
     * Base de données.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    /**
     * Vue principale de l'application.
     */
    protected TwitupMainView mMainView;

    protected TwitsModel twitsModel;

    public TwitController(IDatabase mDatabase, EntityManager mEntityManager, TwitupMainView mMainView) {
        this.mDatabase = mDatabase;
        this.mEntityManager = mEntityManager;
        this.mMainView = mMainView;
        twitsModel = new TwitsModel();
    }

    @Override
    public void createTwit(User user, String twit) {
        mEntityManager.sendTwit(new Twit(user, twit));
        this.initTwits();
    }

    public TwitsModel getTwitsModel() {
        return twitsModel;
    }

    public void initTwits() {
        twitsModel.setTwits(this.mDatabase.getTwits());
    }
}
