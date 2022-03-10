package com.iup.tp.twitup.core.controllers;

import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.explorer.ExplorerObserver;
import com.iup.tp.twitup.ihm.twit.CreateTwitObserver;
import com.iup.tp.twitup.ihm.twit.TwitsModel;

import java.util.HashSet;
import java.util.Set;

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

    @Override
    public void SearchTwit(String research) {
        Set<Twit> allTwits = this.mDatabase.getTwits();
        Set<Twit> sortedTwits = new HashSet<>();
        if(research.startsWith("@")) {
            research = research.substring(1);
            for(Twit twit : allTwits) {
                if(twit.getUserTags().contains(research) || twit.getTwiter().getUserTag().contains(research)) {
                    sortedTwits.add(twit);
                }
            }
        } else if(research.startsWith("#")) {
            research = research.substring(1);
            for(Twit twit : allTwits) {
                if(twit.getTags().contains(research)) {
                    sortedTwits.add(twit);
                }
            }
        } else {
            for(Twit twit : allTwits) {

            }
        }
        this.twitsModel.setTwits(sortedTwits);
    }
}
