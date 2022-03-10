package com.iup.tp.twitup.ihm.navbar;

import com.iup.tp.twitup.ihm.utils.RoundedBorder;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NavbarPanel extends JPanel {

    protected List<NavigationObserver> navbarObservers;

    protected JPanel jPanelNavbar;

    public NavbarPanel() {
        super(new GridBagLayout());
        navbarObservers = new ArrayList<>();
        jPanelNavbar = new JPanel(new GridBagLayout());
        jPanelNavbar.setBackground(Color.BLACK);
        this.setBackground(Color.BLACK);

        // JPANEL NAVBAR
        JButton accueil = new JButton("", new ImageIcon(NavbarPanel.class.getResource("/images/icon_home.png")));
        JButton explorer = new JButton("", new ImageIcon(NavbarPanel.class.getResource("/images/icon_loupe.png")));
        JButton profile = new JButton("", new ImageIcon(NavbarPanel.class.getResource("/images/icon_user.png")));
        JButton users = new JButton("", new ImageIcon(NavbarPanel.class.getResource("/images/icon_users.png")));

        accueil.setBorder(new RoundedBorder(10));
        explorer.setBorder(new RoundedBorder(10));
        profile.setBorder(new RoundedBorder(10));
        users.setBorder(new RoundedBorder(10));

        accueil.addActionListener(a -> navbarObservers.forEach(NavigationObserver::goToTwits));
        explorer.addActionListener(a -> navbarObservers.forEach(NavigationObserver::goToExplorer));
        profile.addActionListener(a -> navbarObservers.forEach(NavigationObserver::goToProfile));
        users.addActionListener(a -> navbarObservers.forEach(NavigationObserver::goToUsers));


        jPanelNavbar.add(accueil, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        jPanelNavbar.add(explorer, new GridBagConstraints(0, 1, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        jPanelNavbar.add(profile, new GridBagConstraints(0, 2, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        jPanelNavbar.add(users, new GridBagConstraints(0, 3, 1, 1, 1, 1, GridBagConstraints.CENTER,
                GridBagConstraints.NONE, new Insets(10, 10, 0, 10), 0, 0));
        this.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder()));
        this.add(jPanelNavbar, new GridBagConstraints(0, 0, 1, 1, 1, 1, GridBagConstraints.WEST,
                GridBagConstraints.WEST, new Insets(0, 0, 0, 0), 50, 1000));

    }

    public void addObserver(NavigationObserver observer) {
        this.navbarObservers.add(observer);
    }

}
