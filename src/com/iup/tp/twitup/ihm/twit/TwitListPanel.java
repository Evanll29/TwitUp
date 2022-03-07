package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.users.UserPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class TwitListPanel extends JPanel {

    protected JPanel twitsPanel;
    protected JScrollPane scrollPane;
    protected Image backgroundImage;

    public TwitListPanel(Set<Twit> twits) {
        super(new GridBagLayout());

        int gridY = 0;
        twitsPanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setViewportView(twitsPanel);

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Twit twit : twits) {
            twitsPanel.add(new TwitPanel(twit), new GridBagConstraints(0, gridY++, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(10,0,10,0), 0, 0));
        }


        this.add(twitsPanel, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
    }


    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }
}
