package com.iup.tp.twitup.ihm;

import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.navbar.NavbarPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe de la vue principale de l'application.
 */
public class TwitupMainView extends JFrame {

    ImageIcon helpIcon;

    protected List<MainViewObserver> mainViewObserverList;

    JPanel mainPanel;

    protected Image backgroundImage;

    public TwitupMainView(String title) {
        super();
        this.mainViewObserverList = new ArrayList<>();
        this.setTitle(title);
        helpIcon = new ImageIcon("src/main/resources/images/helpIcon.png");
    }

    /**
     * Lance l'afficahge de l'IHM.
     */
    public void showGUI() {
        // Init auto de l'IHM au cas ou ;)
        this.initGUI();

        // Affichage dans l'EDT
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Custom de l'affichage
                Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                setSize(new Dimension(screenSize.width - 300, screenSize.height - 300));
                setLocation((screenSize.width - getWidth()) / 6,
                        (screenSize.height - getHeight()) / 4);

                // Affichage
                setVisible(true);
            }
        });
    }

    /**
     * Initialisation de l'IHM
     */
    protected void initGUI() {
        // Création de la fenetre principale
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridBagLayout());
        ImageIcon img = new ImageIcon("src/resources/images/twitup.png");
        this.setIconImage(img.getImage());

        mainPanel = new JPanel(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png"))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        JMenuBar menuBar = new JMenuBar();

        JMenu files = new JMenu("Fichier");
        JMenu help = new JMenu("Help");

        JMenuItem deconnexion = new JMenuItem("Deconnexion", new ImageIcon("src/resources/images/deconnexion_icon.png"));
        deconnexion.addActionListener(a -> mainViewObserverList.forEach(MainViewObserver::disconnectUser));
        JMenuItem close = new JMenuItem("Quitter", new ImageIcon("src/resources/images/exitIcon_20.png"));
        close.addActionListener(a -> mainViewObserverList.forEach(MainViewObserver::closeApp));

        JMenuItem aPropos = new JMenuItem("À propos", helpIcon);
        aPropos.addActionListener(a -> {
            String[] options = {"OK"};
            JOptionPane.showOptionDialog(this,
                    "M2 TIIL-A 2022", "Clicker sur ok",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, helpIcon, options, options[0]);
        });

        files.add(deconnexion);
        files.add(close);
        help.add(aPropos);

        menuBar.add(files);
        menuBar.add(help);
        this.setJMenuBar(menuBar);

        this.getContentPane().add(mainPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    public String chooseDirectory(String currentDirectoryPath) {
        JFileChooser fileChooser = new JFileChooser(currentDirectoryPath);
        fileChooser.setDialogTitle("Choisir un répertoire d'échange");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int retour = fileChooser.showOpenDialog(this);
        if (retour == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return currentDirectoryPath;
        }
    }

    //On renseigne une navbar ou non et on arrange les composants en fonction
    public void setPanel(JPanel panel, NavbarPanel navBar) {
        int gridX = 0;
        mainPanel.removeAll();
        if (navBar != null) {
            mainPanel.add(navBar, new GridBagConstraints(gridX++, 0, 1, 1, 0, 1, GridBagConstraints.WEST,
                    GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        }
        mainPanel.add(panel, new GridBagConstraints(gridX, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public void addObserver(MainViewObserver mainViewObserver) {
        this.mainViewObserverList.add(mainViewObserver);
    }
}
