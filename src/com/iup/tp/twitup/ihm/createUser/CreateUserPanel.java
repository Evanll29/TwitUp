package com.iup.tp.twitup.ihm.createUser;

import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.navbar.NavigationObserver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CreateUserPanel extends JPanel {

    protected List<CreationObserver> creationObservers;
    protected List<NavigationObserver> navigationObservers;
    protected String pathToFile;
    protected JPanel jPanelCreation;
    protected JPanel jPanelText;
    protected JPanel jPanelButtons;
    protected Image backgroundImage;

    public CreateUserPanel() {
        super(new GridBagLayout());
        this.creationObservers = new ArrayList<>();
        this.navigationObservers = new ArrayList<>();
        jPanelCreation = new JPanel(new GridBagLayout());
        jPanelText = new JPanel(new GridBagLayout());
        jPanelButtons = new JPanel(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JLabel labelTag = new JLabel("TwitUp tag: ");
        jPanelText.add(labelTag, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JTextField fieldTag = new JTextField(20);
        jPanelText.add(fieldTag, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelPassword = new JLabel("Mot de passe: ");
        jPanelText.add(labelPassword, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JPasswordField fieldPassword = new JPasswordField(20);
        jPanelText.add(fieldPassword, new GridBagConstraints(1, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelName = new JLabel("Nom d'utilisateur: ");
        jPanelText.add(labelName, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JTextField fieldName = new JTextField(20);
        jPanelText.add(fieldName, new GridBagConstraints(1, 2, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel labelAvatar = new JLabel("Avatar: ");
        jPanelText.add(labelAvatar, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton searchAvatar = new JButton("Choisir un fichier: ");
        jPanelText.add(searchAvatar, new GridBagConstraints(1, 3, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JLabel fileName = new JLabel("");
        jPanelText.add(fileName, new GridBagConstraints(2, 3, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));

        // JPANEL BOUTONS
        JButton buttonRegister = new JButton("Valider");
        jPanelButtons.add(buttonRegister, new GridBagConstraints(1, 4, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        JButton buttonLogin = new JButton("Déjà inscrit ?");
        jPanelButtons.add(buttonLogin, new GridBagConstraints(0, 4, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));

        // JPANEL PARENT
        jPanelCreation.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Inscription sur Twitup"));

        jPanelCreation.add(jPanelText, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 10, 10), 0, 0));
        jPanelCreation.add(jPanelButtons, new GridBagConstraints(0, 1, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 100, 10, 100), 0, 0));
        this.add(jPanelCreation);

        // ACTIONS BUTTONS
        searchAvatar.addActionListener(e -> {
            JFileChooser file = new JFileChooser(System.getProperty("user.home"));
            file.setDialogTitle("Choisir une photo");
            file.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            file.setFileFilter(getFileFilter());
            int res = file.showOpenDialog(CreateUserPanel.this);
            if(res == JFileChooser.APPROVE_OPTION) {
                pathToFile = file.getSelectedFile().getAbsolutePath();
                fileName.setText(file.getSelectedFile().getName());
                this.revalidate();
                this.repaint();
            }
        });

        buttonRegister.addActionListener(a -> this.creationObservers.forEach(c->c.register(fieldTag.getText(), new String(fieldPassword.getPassword()), fieldName.getText(), pathToFile)));

        buttonLogin.addActionListener(a -> this.navigationObservers.forEach(c -> c.goToConnexion(null)));
    }

    public void addObserver(CreationObserver observer) {
        this.creationObservers.add(observer);
    }

    public void addNavigationObserver(NavigationObserver observer) {
        this.navigationObservers.add(observer);
    }

    protected FileFilter getFileFilter() {
        return new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".png") || pathname.getName().endsWith(".jpg") || pathname.isDirectory();
            }

            @Override
            public String getDescription() {
                return "Images au format .png ou .jpg";
            }
        };
    }

    public void addErrorMessage(String error) {
        JLabel msg = new JLabel(error);
        msg.setForeground(Color.red);
        jPanelCreation.add(msg, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }

}