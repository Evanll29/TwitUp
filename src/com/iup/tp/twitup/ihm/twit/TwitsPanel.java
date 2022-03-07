package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.utils.LimitJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class TwitsPanel extends JPanel {

    protected List<CreateTwitObserver> createTwitObservers;
    protected JPanel jPanelMain;
    protected JPanel jPanelCreate;
    protected JPanel jPanelText;
    protected JPanel jPanelButton;
    protected Image backgroundImage;
    protected TwitsModel twitsModel;

    public TwitsPanel(TwitsModel twitsModel) {
        super(new GridBagLayout());
        createTwitObservers = new ArrayList<>();
        jPanelMain = new JPanel(new GridBagLayout());
        jPanelCreate = new JPanel(new GridBagLayout());
        jPanelText = new JPanel(new GridBagLayout());
        jPanelButton = new JPanel(new GridBagLayout());
        this.twitsModel = twitsModel;

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JLabel labelAvatar = new JLabel(new ImageIcon(twitsModel.connectedUser.getAvatarPath()));
        labelAvatar.setPreferredSize(new Dimension(50, 50));
        jPanelText.add(labelAvatar, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        JTextField fieldTwit = new JTextField("TwitUper un joli message !", 30);
        fieldTwit.setForeground(Color.GRAY);
        fieldTwit.setDocument(new LimitJTextField(250));
        jPanelText.add(fieldTwit, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));

        // JPANEL BOUTON
        JButton buttonTwit = new JButton("TwitUper");
        jPanelButton.add(buttonTwit, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        buttonTwit.addActionListener(a -> createTwitObservers.forEach(o -> o.createTwit(twitsModel.connectedUser, fieldTwit.getText())));

        // JPANEL PARENT
        jPanelCreate.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Écrire un twit"));
        jPanelCreate.add(jPanelText, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        jPanelCreate.add(jPanelButton, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        jPanelMain.add(jPanelCreate, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.NORTH,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanelMain.add(new TwitListPanel(twitsModel.getTwitsSortedByDate()), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanelMain.setOpaque(false);
        this.add(jPanelMain, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public void addObserver(CreateTwitObserver observer) {
        this.createTwitObservers.add(observer);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }

}