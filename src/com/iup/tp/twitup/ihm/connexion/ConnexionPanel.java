package com.iup.tp.twitup.ihm.connexion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConnexionPanel extends JPanel {

    protected List<ConnexionObserver> connexionObservers;
    protected JPanel jPanelConnexion;
    protected JPanel jPanelText;
    protected JPanel jPanelButtons;
    protected Image backgroundImage;

    public ConnexionPanel() {

        super(new GridBagLayout());

        connexionObservers = new ArrayList<>();

        jPanelConnexion = new JPanel(new GridBagLayout());
        jPanelText = new JPanel(new GridBagLayout());
        jPanelButtons = new JPanel(new GridBagLayout());
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JLabel labelUsername = new JLabel("Nom d'utilisateur: ");
        jPanelText.add(labelUsername, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JTextField fieldUsername = new JTextField(20);
        jPanelText.add(fieldUsername, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelPassword = new JLabel("Mot de passe: ");
        jPanelText.add(labelPassword, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JPasswordField fieldPassword = new JPasswordField(20);
        jPanelText.add(fieldPassword, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // JPANEL BOUTONS
        JButton buttonLogin = new JButton("Login");
        jPanelButtons.add(buttonLogin, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton buttonRegister = new JButton("S'inscrire");
        jPanelButtons.add(buttonRegister, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // JPANEL PARENT
        jPanelConnexion.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Connexion"));
        jPanelConnexion.add(jPanelText, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        jPanelConnexion.add(jPanelButtons, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 70, 10, 70), 0, 0));
        this.add(jPanelConnexion, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // ACTION BOUTONS
        buttonLogin.addActionListener(a -> this.connexionObservers.forEach(c->c.connect(fieldUsername.getText(), new String(fieldPassword.getPassword()))));

        buttonRegister.addActionListener(a -> this.connexionObservers.forEach(c -> c.goToRegister(null)));
    }

    public void addObserver(ConnexionObserver observer) {
        this.connexionObservers.add(observer);
    }

    public void addErrorMessage(String error) {
        JLabel msg = new JLabel(error);
        msg.setForeground(Color.red);
        jPanelConnexion.add(msg, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }
}