package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.ihm.connexion.ConnectedUserModel;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.twit.components.TwitListPanel;
import com.iup.tp.twitup.ihm.utils.LimitJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TwitsPanel extends JPanel implements TwitsListener{

    protected List<CreateTwitObserver> createTwitObservers;
    protected JPanel jPanelMain;
    protected JPanel jPanelCreate;
    protected JPanel jPanelText;
    protected JPanel jPanelButton;
    protected Image backgroundImage;
    protected TwitsModel twitsModel;

    public TwitsPanel(TwitsModel twitsModel, ConnectedUserModel connectedUserModel) {
        super(new GridBagLayout());
        createTwitObservers = new ArrayList<>();
        jPanelMain = new JPanel(new GridBagLayout());
        jPanelCreate = new JPanel(new GridBagLayout());
        jPanelText = new JPanel(new GridBagLayout());
        jPanelButton = new JPanel(new GridBagLayout());
        this.twitsModel = twitsModel;
        this.twitsModel.addTwitsListener(this);

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JLabel labelAvatar = new JLabel(new ImageIcon(connectedUserModel.getUserConnected().getAvatarPath()));
        labelAvatar.setPreferredSize(new Dimension(50, 50));
        jPanelText.add(labelAvatar, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        JTextField fieldTwit = new JTextField("TwitUper un joli message !", 60);
        fieldTwit.setForeground(Color.GRAY);
        fieldTwit.setDocument(new LimitJTextField(250));
        jPanelText.add(fieldTwit, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));

        // JPANEL BOUTON
        JButton buttonTwit = new JButton("TwitUper");
        jPanelButton.add(buttonTwit, new GridBagConstraints(1, 1, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        buttonTwit.addActionListener(a -> createTwitObservers.forEach(o -> o.createTwit(connectedUserModel.getUserConnected(), fieldTwit.getText())));

        // JPANEL PARENT
        jPanelCreate.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Écrire un twit"));
        jPanelCreate.add(jPanelText, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 10), 0, 0));
        jPanelCreate.add(jPanelButton, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        jPanelMain.add(jPanelCreate, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(15, 10, 15, 10), 0, 0));
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

    @Override
    public void update() {
        jPanelMain.remove(1);
        jPanelMain.add(new TwitListPanel(twitsModel.getTwitsSortedByDate()), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        this.revalidate();
        this.repaint();
    }
}