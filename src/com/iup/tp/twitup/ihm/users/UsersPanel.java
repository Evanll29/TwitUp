package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.connexion.ConnectedUserModel;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.navbar.NavbarPanel;
import com.iup.tp.twitup.ihm.utils.RoundedBorder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class UsersPanel extends JPanel {

    protected List<UsersObserver> usersObservers;
    protected JPanel usersPanel;
    protected JScrollPane scrollPane;
    protected Image backgroundImage;
    protected int gridY;

    public UsersPanel(UsersModel usersModel, ConnectedUserModel connectedUserModel) {
        super(new GridBagLayout());
        gridY = 0;
        usersObservers = new ArrayList<>();
        usersPanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setViewportView(usersPanel);

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(User user : usersModel.getUsersExcepted(connectedUserModel.getUserConnected())) {
            UserPanel userPanel = new UserPanel(user);
            userPanel.setObservers(this.usersObservers);
            usersPanel.add(userPanel, new GridBagConstraints(0, gridY++, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(10,0,10,0), 0, 0));
        }

        this.add(scrollPane, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(20,20,20,20), 0, 0));

    }

    public void addObserver(UsersObserver usersObserver) {
        this.usersObservers.add(usersObserver);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }
}