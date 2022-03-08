package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitsModel {

    protected List<TwitsListener> twitsListeners;

    Set<Twit> twits;

    public Set<Twit> getTwits() {
        return twits;
    }

    public TwitsModel() {
        this.twitsListeners = new ArrayList<>();
    }

    public void setTwits(Set<Twit> twits) {
        this.twits = twits;
        this.twitsListeners.forEach(TwitsListener::update);
    }

    public Set<Twit> getTwitsSortedByDate() {
        return twits.stream().sorted((a, b) -> (int) (b.getEmissionDate() - a.getEmissionDate())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void addTwitsListener(TwitsListener twitsListener) {
        this.twitsListeners.add(twitsListener);
    }
}
