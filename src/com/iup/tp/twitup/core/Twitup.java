package com.iup.tp.twitup.core;

import java.awt.event.WindowEvent;
import java.io.File;

import com.iup.tp.twitup.core.controllers.TwitController;
import com.iup.tp.twitup.core.controllers.UserController;
import com.iup.tp.twitup.datamodel.Database;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.ihm.MainViewObserver;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.TwitupMock;
import com.iup.tp.twitup.ihm.explorer.ExplorerPanel;
import com.iup.tp.twitup.ihm.navbar.NavigationObserver;
import com.iup.tp.twitup.ihm.navbar.NavbarPanel;
import com.iup.tp.twitup.ihm.twit.TwitsPanel;
import com.iup.tp.twitup.ihm.users.ProfilPanel;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.createUser.CreateUserPanel;
import com.iup.tp.twitup.ihm.users.UsersPanel;

import javax.swing.*;

/**
 * Classe principale l'application.
 * 
 * @author S.Lucas
 */
public class Twitup implements NavigationObserver,  MainViewObserver {
	/**
	 * Base de données.
	 */
	protected IDatabase mDatabase;

	/**
	 * Gestionnaire des entités contenu de la base de données.
	 */
	protected EntityManager mEntityManager;

	/**
	 * Vue principale de l'application.
	 */
	protected TwitupMainView mMainView;

	/**
	 * Classe de surveillance de répertoire
	 */
	protected IWatchableDirectory mWatchableDirectory;

	/**
	 * Répertoire d'échange de l'application.
	 */
	protected String mExchangeDirectoryPath;

	/**
	 * Idnique si le mode bouchoné est activé.
	 */
	protected boolean mIsMockEnabled = false;

	/**
	 * Nom de la classe de l'UI.
	 */
	protected String mUiClassName;

	protected UserController userController;
	protected TwitController twitController;

	/**
	 * Constructeur.
	 */
	public Twitup() {
		// Init du look and feel de l'application
		this.initLookAndFeel();

		// Initialisation de la base de données
		this.initDatabase();

		if (this.mIsMockEnabled) {
			// Initialisation du bouchon de travail
			this.initMock();
		}

		//Initialisation des contrôleurs
		this.userController = new UserController(this.mDatabase, this.mEntityManager, this);
		this.twitController = new TwitController(this.mDatabase, this.mEntityManager, this.mMainView);

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du répertoire d'échange
		this.initDirectory();
	}

	/**
	 * Initialisation du look and feel de l'application.
	 */
	protected void initLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialisation de l'interface graphique.
	 */
	protected void initGui() {
		mMainView = new TwitupMainView("Twitup");
		this.mMainView.showGUI();
		this.mMainView.addMainViewObserver(this);
		this.mMainView.addConnexionObserver(this.userController);
		this.goToConnexion(null);
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
		String directory = mMainView.chooseDirectory(".");
		this.initDirectory(directory);
	}

	/**
	 * Indique si le fichier donné est valide pour servire de répertoire
	 * d'échange
	 * 
	 * @param directory
	 *            , Répertoire à tester.
	 */
	protected boolean isValideExchangeDirectory(File directory) {
		// Valide si répertoire disponible en lecture et écriture
		return directory != null && directory.exists() && directory.isDirectory() && directory.canRead()
				&& directory.canWrite();
	}

	/**
	 * Initialisation du mode bouchoné de l'application
	 */
	protected void initMock() {
		TwitupMock mock = new TwitupMock(this.mDatabase, this.mEntityManager);
		mock.showGUI();
	}

	/**
	 * Initialisation de la base de données
	 */
	protected void initDatabase() {
		mDatabase = new Database();
		mEntityManager = new EntityManager(mDatabase);
	}

	/**
	 * Initialisation du répertoire d'échange.
	 * 
	 * @param directoryPath
	 */
	public void initDirectory(String directoryPath) {
		mExchangeDirectoryPath = directoryPath;
		mWatchableDirectory = new WatchableDirectory(directoryPath);
		mEntityManager.setExchangeDirectory(directoryPath);

		mWatchableDirectory.initWatching();
		mWatchableDirectory.addObserver(mEntityManager);
	}

	public void show() {
		// ... setVisible?
	}



	private void goToHomePage() {
		this.goToTwits();
	}


	@Override
	public void closeApp() {
		mMainView.dispatchEvent(new WindowEvent(mMainView, WindowEvent.WINDOW_CLOSING));
	}


	@Override
	public void goToRegister(String error) {
		CreateUserPanel creationPanel = new CreateUserPanel();
		creationPanel.addObserver(this.userController);
		if(error != null) {
			creationPanel.addErrorMessage(error);
		}
		this.mMainView.setPanel(creationPanel, null);
	}

	@Override
	public void goToConnexion(String error) {
		ConnexionPanel connexionPanel = new ConnexionPanel();
		connexionPanel.addConnexionObserver(this.userController);
		if(error != null) {
			connexionPanel.addErrorMessage(error);
		}
		this.mMainView.setPanel(connexionPanel, null);
	}


	@Override
	public void goToProfile() {
		ProfilPanel profilPanel = new ProfilPanel(this.userController.getConnectedUserModel());
		profilPanel.addObserver(this.userController);
		mMainView.setPanel(profilPanel, getNavBar());
	}

	@Override
	public void goToUsers() {
		UsersPanel usersPanel = new UsersPanel(this.userController.getUsersModel(), this.userController.getConnectedUserModel());
		usersPanel.addObserver(this.userController);
		mMainView.setPanel(usersPanel, getNavBar());
	}

	@Override
	public void goToTwits() {
		TwitsPanel createTwitPanel = new TwitsPanel(this.twitController.getTwitsModel(), this.userController.getConnectedUserModel());
		createTwitPanel.addObserver(this.twitController);
		mMainView.setPanel(createTwitPanel, getNavBar());
	}

	@Override
	public void goToExplorer() {
		ExplorerPanel explorerPanel = new ExplorerPanel();
		explorerPanel.addObserver(this.twitController);
		mMainView.setPanel(explorerPanel, getNavBar());
	}

	public NavbarPanel getNavBar() {
		NavbarPanel navbarPanel = new NavbarPanel();
		navbarPanel.addObserver(this);
		return navbarPanel;
	}
}
