package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitsModel {

    Set<Twit> twits;

    public Set<Twit> getTwits() {
        return twits;
    }

    public void setTwits(Set<Twit> twits) {
        this.twits = twits;
    }

    public Set<Twit> getTwitsSortedByDate() {
        return twits.stream().sorted((a, b) -> (int) (b.getEmissionDate() - a.getEmissionDate())).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
