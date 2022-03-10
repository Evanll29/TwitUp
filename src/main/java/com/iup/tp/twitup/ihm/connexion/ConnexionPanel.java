package com.iup.tp.twitup.ihm.connexion;

import com.iup.tp.twitup.ihm.navbar.NavigationObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ConnexionPanel extends JPanel {

    protected List<ConnexionObserver> connexionObservers;
    protected List<NavigationObserver> navigationObservers;
    protected JPanel jPanelConnexion;
    protected JPanel jPanelTitre;
    protected JPanel jPanelText;
    protected JPanel jPanelButtons;
    protected Image backgroundImage;
    protected JTextField fieldUsername;
    protected JPasswordField fieldPassword;
    protected boolean activate = false;

    public ConnexionPanel() {

        super(new GridBagLayout());
        connexionObservers = new ArrayList<>();
        navigationObservers = new ArrayList<>();
        jPanelTitre = new JPanel(new GridBagLayout());
        jPanelConnexion = new JPanel(new GridBagLayout());
        jPanelText = new JPanel(new GridBagLayout());
        jPanelButtons = new JPanel(new GridBagLayout());
        Font fieldFont = new Font("Arial", Font.PLAIN, 20);

        // OPACITY
        jPanelConnexion.setOpaque(false);
        jPanelText.setOpaque(false);
        jPanelButtons.setOpaque(false);
        jPanelTitre.setOpaque(false);
        this.setOpaque(false);

        // BACKGROUND IMAGE
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TITRE
        JLabel connexion = new JLabel("Connexion");
        connexion.setFont(new Font("Arial", Font.PLAIN, 30));
        connexion.setForeground(Color.white);
        jPanelTitre.add(connexion, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

        // JPANEL TEXT
        fieldUsername = new JTextField("Login ...");
        jPanelText.add(fieldUsername, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        fieldUsername.setFont(fieldFont);
        fieldUsername.setBackground(Color.white);
        fieldUsername.setForeground(Color.gray.brighter());
        fieldUsername.setColumns(30);
        fieldUsername.setBorder(BorderFactory.createCompoundBorder(null,
                new EmptyBorder(new Insets(15, 25, 15, 25))));
        fieldUsername.addMouseListener(new FieldMouseListener());

        fieldPassword = new JPasswordField();
        fieldPassword.setFont(fieldFont);
        fieldPassword.setBackground(Color.white);
        fieldPassword.setForeground(Color.gray.brighter());
        fieldPassword.setColumns(30);
        fieldPassword.setBorder(BorderFactory.createCompoundBorder(null,
                new EmptyBorder(new Insets(15, 25, 15, 25))));
        fieldPassword.addMouseListener(new FieldMouseListener());

        jPanelText.add(fieldPassword, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // JPANEL BOUTONS
        JButton buttonRegister = new JButton("S'inscrire");

        jPanelButtons.add(buttonRegister, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        JButton buttonLogin = new JButton("Login");

        jPanelButtons.add(buttonLogin, new GridBagConstraints(1, 2, 1, 1, 1, 0, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // JPANEL PARENT
        jPanelConnexion.add(jPanelTitre, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        jPanelConnexion.add(jPanelText, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        jPanelConnexion.add(jPanelButtons, new GridBagConstraints(0, 2, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 70, 10, 70), 0, 0));

        this.add(jPanelConnexion, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // ACTION BOUTONS
        buttonLogin.addActionListener(a -> this.connexionObservers.forEach(c -> c.connect(fieldUsername.getText(), new String(fieldPassword.getPassword()))));

        buttonRegister.addActionListener(a -> this.navigationObservers.forEach(c -> c.goToRegister(null)));
    }

    public void addConnexionObserver(ConnexionObserver observer) {
        this.connexionObservers.add(observer);
    }

    public void addNavigationObserver(NavigationObserver observer) {
        this.navigationObservers.add(observer);
    }

    public void addErrorMessage(String error) {
        JLabel msg = new JLabel(error);
        msg.setForeground(Color.red);
        jPanelConnexion.add(msg, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }

    class FieldMouseListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // TODO Auto-generated method stub
            if (activate == false) {
                fieldUsername.setText("");
            }
            activate = true;
            fieldUsername.setForeground(Color.black);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

    }
}