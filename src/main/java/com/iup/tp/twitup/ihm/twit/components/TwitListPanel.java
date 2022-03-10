package com.iup.tp.twitup.ihm.twit.components;

import com.iup.tp.twitup.datamodel.Twit;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class TwitListPanel extends JPanel {

    protected JPanel twitsPanel;
    protected JScrollPane scrollPane;

    public TwitListPanel(Set<Twit> twits) {
        super(new GridBagLayout());

        int gridY = 0;
        twitsPanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        this.setOpaque(false);
        scrollPane.setViewportView(twitsPanel);

        for(Twit twit : twits) {
            twitsPanel.add(new TwitPanel(twit), new GridBagConstraints(0, gridY++, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(10,0,10,0), 0, 0));
        }


        this.add(twitsPanel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    }
}
