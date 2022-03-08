package com.iup.tp.twitup.core.controllers;

import com.iup.tp.twitup.common.FilesUtils;
import com.iup.tp.twitup.core.EntityManager;
import com.iup.tp.twitup.core.Twitup;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.ihm.MainViewObserver;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.connexion.ConnectedUserModel;
import com.iup.tp.twitup.ihm.connexion.ConnexionObserver;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.createUser.CreateUserPanel;
import com.iup.tp.twitup.ihm.createUser.CreationObserver;
import com.iup.tp.twitup.ihm.navbar.NavigationObserver;
import com.iup.tp.twitup.ihm.users.ProfilPanel;
import com.iup.tp.twitup.ihm.users.UsersModel;
import com.iup.tp.twitup.ihm.users.UsersObserver;

import java.io.File;
import java.util.HashSet;
import java.util.UUID;

public class UserController implements ConnexionObserver, CreationObserver, UsersObserver {
    /**
     * Base de données.
     */
    protected IDatabase mDatabase;

    /**
     * Gestionnaire des entités contenu de la base de données.
     */
    protected EntityManager mEntityManager;

    /**
     * Utilisateur actuellement connecté
     */
    protected ConnectedUserModel connectedUserModel;

    protected UsersModel usersModel;

    protected NavigationObserver navigationObserver;

    public UserController(IDatabase mDatabase, EntityManager mEntityManager, NavigationObserver navigationObserver) {
        this.mDatabase = mDatabase;
        this.mEntityManager = mEntityManager;
        this.usersModel = new UsersModel();
        this.navigationObserver = navigationObserver;
        this.connectedUserModel = new ConnectedUserModel();
        this.usersModel.setUsers(mDatabase.getUsers());
    }

    @Override
    public void connect(String username, String password) {
        boolean userExists = mDatabase.getUsers().stream().anyMatch(u -> u.getUserTag().equals(username) && u.getUserPassword().equals(password));
        if(userExists) {
            this.connectedUserModel.setUserConnected(mDatabase.getUsers().stream().filter(u -> u.getUserTag().equals(username)).findFirst().get());
            navigationObserver.goToTwits();
        } else {
            navigationObserver.goToConnexion("Erreur: tag ou mot de passe incorrect");
        }
    }

    @Override
    public void register(String tag, String password, String name, String pathToAvatar) {
        if(!validateString(tag, 5)) {
            navigationObserver.goToRegister("Erreur: tag incorrect, il doit faire au moins 5 caractères");
        }
        else if(mDatabase.getUsers().stream().anyMatch(u -> u.getUserTag().equals(tag))) {
            navigationObserver.goToRegister("Erreur: ce tag est déjà utilisé");
        }
        else if(!validateString(password, 8)) {
            navigationObserver.goToRegister("Erreur: mot de passe incorrect, il doit faire au moins 8 caractères");
        }
        else if(!validateString(name, 3)) {
            navigationObserver.goToRegister("Erreur: nom d'utilisateur incorrect, il doit faire au moins 3 caractères");
        } else if(mDatabase.getUsers().stream().anyMatch(u -> u.getName().equals(name))) {
            navigationObserver.goToRegister("Erreur: ce nom d'utilisateur est déjà utilisé");
        }else {
            if(!validateString(pathToAvatar, 0)){
                pathToAvatar = "images\\user_icon.png";
            } else {
                String newFileName ="images\\profiles\\" + (new File(pathToAvatar).getName());
                FilesUtils.copyFile(pathToAvatar, newFileName);
                pathToAvatar = newFileName;
            }

            User newUser = new User(UUID.randomUUID(), tag, password, name, new HashSet<>(), pathToAvatar);
            this.mEntityManager.sendUser(newUser);
            this.connectedUserModel.setUserConnected(newUser);
        }
    }

    @Override
    public void follow(String tag) {
        User connectedUser = this.connectedUserModel.getUserConnected();
        connectedUser.getFollows().add(tag);
        connectedUserModel.setUserConnected(connectedUser);
        this.mDatabase.modifiyUser(connectedUser);
    }

    @Override
    public void disconnectUser() {
        this.connectedUserModel.setUserConnected(null);
        navigationObserver.goToConnexion(null);
    }

    private boolean validateString(String s, int minLength) {
        return s != null && !s.equals("") && s.length() >= minLength;
    }

    public ConnectedUserModel getConnectedUserModel() {
        return connectedUserModel;
    }

    public UsersModel getUsersModel() {
        return usersModel;
    }
}
