package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.connexion.ConnectedUserModel;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.users.components.UserPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UsersPanel extends JPanel implements UsersListener {

    protected List<UsersObserver> usersObservers;
    protected JPanel usersPanel;
    protected JPanel searchPanel;
    protected JScrollPane scrollPane;
    protected Image backgroundImage;
    protected int gridY;
    protected UsersModel usersModel;
    protected ConnectedUserModel connectedUserModel;

    public UsersPanel(UsersModel usersModel, ConnectedUserModel connectedUserModel) {
        super(new GridBagLayout());
        gridY = 0;
        usersObservers = new ArrayList<>();
        usersPanel = new JPanel(new GridBagLayout());
        searchPanel = new JPanel(new GridBagLayout());
        scrollPane = new JScrollPane();
        scrollPane.setAutoscrolls(true);
        scrollPane.setViewportView(usersPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        this.connectedUserModel = connectedUserModel;
        this.usersModel = usersModel;
        usersModel.addUsersListener(this);
        connectedUserModel.addUsersListener(this);

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JTextField fieldUsers = new JTextField("Rechercher un utilisateur ...", 30);
        fieldUsers.setForeground(Color.GRAY);
        searchPanel.add(fieldUsers, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(15, 10, 15, 10), 0, 0));

        JButton buttonUsers = new JButton("Rechercher");
        searchPanel.add(buttonUsers, new GridBagConstraints(1, 0, 1, 1, 0, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(15, 10, 15, 10), 0, 0));

        for (User user : usersModel.getUsersExcepted(connectedUserModel.getUserConnected())) {
            UserPanel userPanel = new UserPanel(user, connectedUserModel.getUserConnected().isFollowing(user));
            userPanel.setObservers(this.usersObservers);
            usersPanel.add(userPanel, new GridBagConstraints(0, gridY++, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(10, 0, 10, 0), 0, 0));
        }

        searchPanel.setOpaque(false);
        this.add(searchPanel, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 10, 0), 0, 0));
        this.add(scrollPane, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(20, 20, 20, 20), 0, 0));

        buttonUsers.addActionListener(a -> usersObservers.forEach(o -> o.search(fieldUsers.getText())));

        fieldUsers.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                usersObservers.forEach(o -> o.search(fieldUsers.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                usersObservers.forEach(o -> o.search(fieldUsers.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

    }

    public void addObserver(UsersObserver usersObserver) {
        this.usersObservers.add(usersObserver);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void update() {
        usersPanel.removeAll();
        for (User user : usersModel.getUsersExcepted(connectedUserModel.getUserConnected())) {
            UserPanel userPanel = new UserPanel(user, connectedUserModel.getUserConnected().isFollowing(user));
            userPanel.setObservers(this.usersObservers);
            usersPanel.add(userPanel, new GridBagConstraints(0, gridY++, 1, 1, 1, 1, GridBagConstraints.CENTER,
                    GridBagConstraints.BOTH, new Insets(10, 0, 10, 0), 0, 0));
        }
        this.revalidate();
        this.repaint();
    }
}