package com.iup.tp.twitup.ihm.explorer;

import com.iup.tp.twitup.ihm.twit.TwitsListener;
import com.iup.tp.twitup.ihm.twit.TwitsModel;
import com.iup.tp.twitup.ihm.twit.components.TwitListPanel;
import com.iup.tp.twitup.ihm.utils.LimitJTextField;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExplorerPanel extends JPanel implements TwitsListener {

    protected List<ExplorerObserver> explorerObservers;
    protected JPanel jPanelExplorer;
    protected Image backgroundImage;
    protected TwitsModel twitsModel;

    public ExplorerPanel(TwitsModel twitsModel) {

        super(new GridBagLayout());
        explorerObservers = new ArrayList<>();
        jPanelExplorer = new JPanel(new GridBagLayout());
        this.twitsModel = twitsModel;
        this.twitsModel.addTwitsListener(this);
        jPanelExplorer.setOpaque(false);

        // BACKGROUND IMAGE
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(ExplorerPanel.class.getResource("/images/background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // JPANEL TEXT
        JTextField fieldTwit = new JTextField("Rechercher un twitup ...", 30);
        fieldTwit.setDocument(new LimitJTextField(250));
        fieldTwit.setFont(new Font("Arial", Font.PLAIN, 20));
        jPanelExplorer.add(fieldTwit, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(15, 10, 15, 10), 0, 0));

        JButton buttonTwit = new JButton("Rechercher");
        jPanelExplorer.add(buttonTwit, new GridBagConstraints(1, 0, 1, 1, 0, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(15, 10, 15, 10), 0, 0));

        // JPANEL PARENT
        this.add(jPanelExplorer, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.HORIZONTAL, new Insets(0, 0, 10, 0), 0, 0));

        this.add(new TwitListPanel(twitsModel.getTwitsSortedByDate()), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        buttonTwit.addActionListener(a -> explorerObservers.forEach(o -> o.searchTwit(fieldTwit.getText())));

        fieldTwit.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                explorerObservers.forEach(o -> o.searchTwit(fieldTwit.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                explorerObservers.forEach(o -> o.searchTwit(fieldTwit.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    public void addObserver(ExplorerObserver observer) {
        this.explorerObservers.add(observer);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, null);
    }

    @Override
    public void update() {
        this.remove(1);
        this.add(new TwitListPanel(twitsModel.getTwitsSortedByDate()), new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
        this.revalidate();
        this.repaint();
    }
}