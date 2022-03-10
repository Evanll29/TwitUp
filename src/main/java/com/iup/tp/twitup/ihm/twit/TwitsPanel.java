package com.iup.tp.twitup.ihm.twit;

import com.iup.tp.twitup.ihm.connexion.ConnectedUserModel;
import com.iup.tp.twitup.ihm.twit.components.TwitListPanel;
import com.iup.tp.twitup.ihm.utils.LimitJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TwitsPanel extends JPanel implements TwitsListener {

    protected List<CreateTwitObserver> createTwitObservers;
    protected JPanel jPanelCreate;
    protected Image backgroundImage;
    protected TwitsModel twitsModel;

    public TwitsPanel(TwitsModel twitsModel, ConnectedUserModel connectedUserModel) {
        super(new GridBagLayout());
        createTwitObservers = new ArrayList<>();
        jPanelCreate = new JPanel(new GridBagLayout());
        jPanelCreate.setOpaque(false);
        this.twitsModel = twitsModel;
        this.twitsModel.addTwitsListener(this);
        this.setOpaque(false);

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(TwitsPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL CREATE
        JLabel labelAvatar = new JLabel(new ImageIcon(TwitsPanel.class.getResource(connectedUserModel.getUserConnected().getAvatarPath())));
        labelAvatar.setPreferredSize(new Dimension(50, 50));
        jPanelCreate.add(labelAvatar, new GridBagConstraints(0, 0, 1, 1, 0, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JTextField fieldTwit = new JTextField("TwitUper un joli message !");
        fieldTwit.setDocument(new LimitJTextField(250));
        fieldTwit.setFont(new Font("Arial", Font.PLAIN, 20));
        jPanelCreate.add(fieldTwit, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));

        JButton buttonTwit = new JButton("TwitUper");
        jPanelCreate.add(buttonTwit, new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        buttonTwit.addActionListener(a -> createTwitObservers.forEach(o -> o.createTwit(connectedUserModel.getUserConnected(), fieldTwit.getText())));

        // JPANEL PARENT
        this.add(jPanelCreate, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 10, 0), 0, 0));

        this.add(new TwitListPanel(twitsModel.getTwitsSortedByDate()), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
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
        this.remove(1);
        this.add(new TwitListPanel(twitsModel.getTwitsSortedByDate()), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 0, 0));
        this.revalidate();
        this.repaint();
    }
}