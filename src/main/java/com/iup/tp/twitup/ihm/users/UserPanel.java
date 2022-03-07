package com.iup.tp.twitup.ihm.users;

import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.utils.RoundedBorder;

import javax.swing.*;
import java.awt.*;

public class UserPanel extends JPanel {

    JPanel userPanel;

    public UserPanel(User user) {

        super(new GridBagLayout());
        userPanel = new JPanel(new GridBagLayout());
        JPanel textPanel = new JPanel(new GridBagLayout());
        JPanel photoPanel = new JPanel(new GridBagLayout());
        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        // JPANEL PHOTO
        JLabel photo = new JLabel(new ImageIcon(user.getAvatarPath()));
        photo.setPreferredSize(new Dimension(50, 50));
        photo.setBorder(new RoundedBorder(10));
        photoPanel.add(photo, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,5,5,5), 0, 0));

        // JPANEL TEXT
        JLabel userName = new JLabel(user.getName());
        userName.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        userName.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel userTag = new JLabel("@" + user.getUserTag());
        userTag.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        userTag.setHorizontalAlignment(SwingConstants.LEFT);

        textPanel.add(userName, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        textPanel.add(userTag, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        // JPANEL BUTTONS
        JButton followButton = new JButton("Suivre");

        buttonsPanel.add(followButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        JButton checkProfileButton = new JButton("Consulter le profil");

        buttonsPanel.add(checkProfileButton, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        // JPANEL PARENT
        userPanel.add(photoPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        userPanel.add(textPanel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        userPanel.add(buttonsPanel, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5,0,5,5), 0, 0));

        userPanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(userPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0,10,0,0), 0, 0));
    }
}