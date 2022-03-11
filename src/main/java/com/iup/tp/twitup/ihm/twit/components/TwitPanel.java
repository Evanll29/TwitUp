package com.iup.tp.twitup.ihm.twit.components;

import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.ihm.users.components.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class TwitPanel extends JPanel {

    JPanel twitPanel;
    JPanel titrePanel;
    JPanel contenuPanel;
    JPanel iconsPanel;

    public TwitPanel(Twit twit) {

        super(new GridBagLayout());
        twitPanel = new JPanel(new GridBagLayout());
        titrePanel = new JPanel(new GridBagLayout());
        contenuPanel = new JPanel(new GridBagLayout());
        iconsPanel = new JPanel(new GridBagLayout());
        twitPanel.setBackground(Color.white);
        titrePanel.setBackground(Color.white);
        contenuPanel.setBackground(Color.white);
        iconsPanel.setBackground(Color.white);

        // JPANEL TITRE
        JLabel userName = new JLabel(twit.getTwiter().getName());
        userName.setForeground(Color.BLACK);
        userName.setFont(new Font("Arial", Font.BOLD, 20));
        titrePanel.add(userName, new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        JLabel userTag = new JLabel("@" + twit.getTwiter().getUserTag());
        userTag.setForeground(Color.gray.brighter());
        userTag.setFont(new Font("Arial", Font.ITALIC, 15));
        titrePanel.add(userTag, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 5, 0, 0), 0, 0));

        JLabel date = new JLabel(new Date(twit.getEmissionDate()).toString());
        date.setFont(new Font("Arial", Font.ITALIC, 15));
        titrePanel.add(date, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.EAST,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // JPANEL CONTENU
        JLabel twitContent = new JLabel(twit.getText());
        twitContent.setFont(new Font("Arial", Font.PLAIN, 17));
        contenuPanel.add(twitContent, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

        // JPANEL ICONS
        JLabel iconCom = new JLabel();
        iconCom.setIcon(new ImageIcon(new ImageIcon("src/main/resources/images/com_icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        JLabel iconTwit = new JLabel();
        iconTwit.setIcon(new ImageIcon(new ImageIcon("src/main/resources/images/retwit_icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        JLabel iconLove = new JLabel();
        iconLove.setIcon(new ImageIcon(new ImageIcon("src/main/resources/images/heart_icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));
        JLabel iconShare = new JLabel();
        iconShare.setIcon(new ImageIcon(new ImageIcon("src/main/resources/images/heart_icon.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT)));

        iconsPanel.add(iconCom, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        iconsPanel.add(iconTwit, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        iconsPanel.add(iconLove, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        iconsPanel.add(iconShare, new GridBagConstraints(3, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));

        // JPANEL PARENT
        twitPanel.setBorder(BorderFactory.createEtchedBorder());
        twitPanel.add(titrePanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
        twitPanel.add(contenuPanel, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 10, 10, 10), 0, 0));
        twitPanel.add(iconsPanel, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(10, 0, 10, 0), 0, 0));

        this.add(twitPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));
    }
}
