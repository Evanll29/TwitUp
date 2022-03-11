package com.iup.tp.twitup.ihm.users.components;

import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.navbar.NavigationObserver;
import com.iup.tp.twitup.ihm.users.UsersObserver;
import com.iup.tp.twitup.ihm.utils.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserPanel extends JPanel {

    protected List<UsersObserver> usersObservers;
    protected List<NavigationObserver> navigationObservers;
    JPanel userPanel;
    JPanel textPanel;
    JPanel photoPanel;
    JPanel buttonsPanel;

    public UserPanel(User user, boolean isFollowed) {

        super(new GridBagLayout());
        userPanel = new JPanel(new GridBagLayout());
        textPanel = new JPanel(new GridBagLayout());
        photoPanel = new JPanel(new GridBagLayout());
        buttonsPanel = new JPanel(new GridBagLayout());
        userPanel.setBackground(Color.white);
        textPanel.setBackground(Color.white);
        photoPanel.setBackground(Color.white);
        buttonsPanel.setBackground(Color.white);

        // JPANEL PHOTO
        JLabel photo = new JLabel();
        photo.setIcon(new ImageIcon(new ImageIcon(UserPanel.class.getResource(user.getAvatarPath())).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT)));

        photo.setBorder(new RoundedBorder(10));
        photoPanel.add(photo, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));

        // JPANEL TEXT
        JLabel userName = new JLabel(user.getName());
        userName.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        userName.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel userTag = new JLabel("@" + user.getUserTag());
        userTag.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        userTag.setHorizontalAlignment(SwingConstants.LEFT);

        textPanel.add(userName, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        textPanel.add(userTag, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        // JPANEL BUTTONS
        JButton followButton = new JButton(isFollowed ? "Ne plus suivre" : "Suivre");
        followButton.setOpaque(false);
        buttonsPanel.add(followButton, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        JButton checkProfileButton = new JButton("Consulter le profil");
        checkProfileButton.setOpaque(false);
        buttonsPanel.add(checkProfileButton, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        // JPANEL PARENT
        userPanel.add(photoPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        userPanel.add(textPanel, new GridBagConstraints(1, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        userPanel.add(buttonsPanel, new GridBagConstraints(2, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.NONE, new Insets(5, 0, 5, 5), 0, 0));

        userPanel.setBorder(BorderFactory.createEtchedBorder());

        this.add(userPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 10, 0, 10), 0, 0));

        followButton.addActionListener(a -> this.usersObservers.forEach(o -> o.setFollow(user.getUserTag(), isFollowed)));
        checkProfileButton.addActionListener(a -> this.navigationObservers.forEach(o -> o.goToProfile(user)));
    }

    public void setObservers(List<UsersObserver> usersObservers, List<NavigationObserver> navigationObservers) {
        this.usersObservers = usersObservers;
        this.navigationObservers = navigationObservers;
    }
}