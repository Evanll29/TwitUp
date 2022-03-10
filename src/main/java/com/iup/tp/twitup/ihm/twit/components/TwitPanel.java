package com.iup.tp.twitup.ihm.twit.components;

import com.iup.tp.twitup.datamodel.Twit;

import javax.swing.*;
import java.awt.*;

public class TwitPanel extends JPanel {

    JPanel twitPanel;
    JPanel titrePanel;
    JPanel contenuPanel;

    public TwitPanel(Twit twit) {

        super(new GridBagLayout());
        twitPanel = new JPanel(new GridBagLayout());
        titrePanel = new JPanel(new GridBagLayout());
        contenuPanel = new JPanel(new GridBagLayout());

        // JPANEL TITRE
        JLabel userName = new JLabel(twit.getTwiter().getName());
        titrePanel.add(userName, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        JLabel userTag = new JLabel("@" + twit.getTwiter().getUserTag());
        titrePanel.add(userTag, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // JPANEL CONTENU
        JLabel twitContent = new JLabel(twit.getText());
        contenuPanel.add(twitContent, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        // JPANEL PARENT
        twitPanel.setBorder(BorderFactory.createEtchedBorder());
        twitPanel.add(titrePanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        twitPanel.add(contenuPanel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.add(twitPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }
}
