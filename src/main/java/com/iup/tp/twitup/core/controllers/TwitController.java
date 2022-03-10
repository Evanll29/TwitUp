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
    }

    public TwitsModel getTwitsModel() {
        return twitsModel;
    }

    public void updateTwits() {
        twitsModel.setTwits(this.mDatabase.getTwits());
    }

    @Override
    public void searchTwit(String research) {
        if(research.startsWith("@")) {
            twitsModel.setTwits(searchByUserTag(research.substring(1)));
        } else if(research.startsWith("#")) {
            twitsModel.setTwits(searchByTag(research.substring(1)));
        } else {
            twitsModel.setTwits(search(research));
        }
    }

    protected Set<Twit> searchByUserTag(String search) {
        Set<Twit> allTwits = this.mDatabase.getTwits();
        Set<Twit> sortedTwits = new HashSet<>();
        for(Twit twit : allTwits) {
            if(twit.getUserTags().contains(search) || twit.getTwiter().getUserTag().equals(search)) {
                sortedTwits.add(twit);
            }
        }
        return sortedTwits;
    }

    protected Set<Twit> searchByTag(String search) {
        Set<Twit> allTwits = this.mDatabase.getTwits();
        Set<Twit> sortedTwits = new HashSet<>();
        for(Twit twit : allTwits) {
            if(twit.getTags().contains(search)) {
                sortedTwits.add(twit);
            }
        }
        return sortedTwits;
    }

    protected Set<Twit> search(String search) {
        Set<Twit> sortedTwits = searchByTag(search);
        sortedTwits.addAll(searchByUserTag(search));
        return sortedTwits;
    }
}
