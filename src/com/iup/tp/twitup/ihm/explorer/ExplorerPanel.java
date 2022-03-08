package com.iup.tp.twitup.ihm.explorer;

import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExplorerPanel extends JPanel {

    protected List<ExplorerObserver> explorerObservers;
    protected JPanel jPanelMain;
    protected JPanel jPanelExplorer;
    protected Image backgroundImage;

    public ExplorerPanel() {

        super(new GridBagLayout());
        explorerObservers = new ArrayList<>();
        jPanelMain = new JPanel(new GridBagLayout());
        jPanelExplorer = new JPanel(new GridBagLayout());

        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ConnexionPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JTextField fieldTwit = new JTextField("Rechercher un twitup ...", 30);
        fieldTwit.setForeground(Color.GRAY);
        jPanelExplorer.add(fieldTwit, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(15, 10, 15, 10), 0, 0));

        JButton buttonTwit = new JButton("Rechercher");
        jPanelExplorer.add(buttonTwit, new GridBagConstraints(1, 0, 1, 1, 0, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(15, 10, 15, 10), 0, 0));

        // JPANEL PARENT
        jPanelMain.add(jPanelExplorer, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        this.add(jPanelMain, new GridBagConstraints(0, 0, 1, 1, 1, 0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 10));
    }

    public void addObserver(ExplorerObserver observer) {
        this.explorerObservers.add(observer);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }
}