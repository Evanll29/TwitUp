package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.connexion.ConnectedUserModel;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.navbar.NavbarPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfilPanel extends JPanel {

    protected List<UsersObserver> usersObservers;

    protected JPanel jPanelMain;
    protected JPanel jPanelProfil;
    protected JPanel jPanelTop;
    protected JPanel jPanelNames;
    protected Image backgroundImage;

    public ProfilPanel(ConnectedUserModel connectedUserModel) {

        super(new GridBagLayout());
        usersObservers = new ArrayList<>();
        jPanelProfil = new JPanel(new GridBagLayout());
        jPanelTop = new JPanel(new GridBagLayout());
        jPanelNames = new JPanel(new GridBagLayout());
        jPanelMain = new JPanel(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TOP

        JLabel labelAvatar = new JLabel(new ImageIcon(connectedUserModel.getUserConnected().getAvatarPath()));
        labelAvatar.setPreferredSize(new Dimension(50,50));
        jPanelTop.add(labelAvatar, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        JButton modifyButton = new JButton("Éditer le profil");
        jPanelTop.add(modifyButton, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));

        // JPANEL NAMES
        JLabel labelTagName = new JLabel("Tag utilisateur: ");
        jPanelNames.add(labelTagName, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelAccountTagName = new JLabel(connectedUserModel.getUserConnected().getUserTag());
        jPanelNames.add(labelAccountTagName, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelUsername = new JLabel("Nom d'utilisateur: ");
        jPanelNames.add(labelUsername, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelAccountUsername = new JLabel(connectedUserModel.getUserConnected().getName());
        jPanelNames.add(labelAccountUsername, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));


        // JPANEL PARENT
        jPanelProfil.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Profil"));
        jPanelProfil.add(jPanelTop, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        jPanelProfil.add(jPanelNames, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 70, 10, 70), 0, 0));

        jPanelMain.add(jPanelProfil, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        jPanelMain.setOpaque(false);
        this.add(jPanelMain, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public void addObserver(UsersObserver observer) {
        this.usersObservers.add(observer);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }
}