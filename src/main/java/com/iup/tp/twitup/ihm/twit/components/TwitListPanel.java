package com.iup.tp.twitup.ihm.twit.components;

import com.iup.tp.twitup.datamodel.Twit;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

public class TwitListPanel extends JPanel {

    protected JPanel mainPanel;
    protected JScrollPane scrollPane;

    public TwitListPanel(Set<Twit> twits) {
        super(new GridBagLayout());
        int gridY = 0;
        mainPanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setViewportView(mainPanel);
        scrollPane.setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.setOpaque(false);

        for (Twit twit : twits) {
            mainPanel.add(new TwitPanel(twit), new GridBagConstraints(0, gridY++, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(10, 0, 10, 0), 0, 0));
        }

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    }
}
