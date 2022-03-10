package com.iup.tp.twitup.ihm.twit.components;

import com.iup.tp.twitup.datamodel.Twit;

import javax.swing.*;
import java.awt.*;

public class TwitPanel extends JPanel {

    JPanel twitPanel;

    public TwitPanel(Twit twit) {
        super(new GridBagLayout());

        twitPanel = new JPanel(new GridBagLayout());

        JLabel userName = new JLabel(twit.getTwiter().getName());
        twitPanel.add(userName, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        JLabel userTag = new JLabel("@"+twit.getTwiter().getUserTag());
        twitPanel.add(userTag, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JLabel twitContent = new JLabel(twit.getText());
        twitPanel.add(twitContent, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        twitPanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(twitPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }
}
