package com.iup.tp.twitup.core;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashSet;
import java.util.UUID;

import com.iup.tp.twitup.common.FilesUtils;
import com.iup.tp.twitup.datamodel.Database;
import com.iup.tp.twitup.datamodel.IDatabase;
import com.iup.tp.twitup.datamodel.Twit;
import com.iup.tp.twitup.datamodel.User;
import com.iup.tp.twitup.events.file.IWatchableDirectory;
import com.iup.tp.twitup.events.file.WatchableDirectory;
import com.iup.tp.twitup.ihm.MainViewObserver;
import com.iup.tp.twitup.ihm.TwitupMainView;
import com.iup.tp.twitup.ihm.TwitupMock;
import com.iup.tp.twitup.ihm.explorer.ExplorerObserver;
import com.iup.tp.twitup.ihm.explorer.ExplorerPanel;
import com.iup.tp.twitup.ihm.navbar.NavbarObserver;
import com.iup.tp.twitup.ihm.navbar.NavbarPanel;
import com.iup.tp.twitup.ihm.twit.CreateTwitObserver;
import com.iup.tp.twitup.ihm.twit.TwitsModel;
import com.iup.tp.twitup.ihm.twit.TwitsPanel;
import com.iup.tp.twitup.ihm.users.ProfilPanel;
import com.iup.tp.twitup.ihm.users.UsersModel;
import com.iup.tp.twitup.ihm.users.UsersObserver;
import com.iup.tp.twitup.ihm.connexion.ConnexionObserver;
import com.iup.tp.twitup.ihm.connexion.ConnexionPanel;
import com.iup.tp.twitup.ihm.createUser.CreationObserver;
import com.iup.tp.twitup.ihm.createUser.CreateUserPanel;
import com.iup.tp.twitup.ihm.users.UsersPanel;

import javax.swing.*;

/**
 * Classe principale l'application.
 *
 * @author S.Lucas
 */
public class Twitup implements MainViewObserver, ConnexionObserver, CreationObserver, UsersObserver, CreateTwitObserver, NavbarObserver, ExplorerObserver {
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

	/**
	 * Utilisateur actuellement connecté
	 */
	protected User connectedUser;

	protected TwitsModel twitsModel;
	protected UsersModel usersModel;

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

		// Initialisation de l'IHM
		this.initGui();

		// Initialisation du répertoire d'échange
		this.initDirectory();

		// Initialisationd des modèles
		this.twitsModel = new TwitsModel();
		this.usersModel = new UsersModel();
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
		this.mMainView.addObserver(this);
		ConnexionPanel c = new ConnexionPanel();
		c.addObserver(this);
		this.mMainView.setPanel(c, null);
	}

	/**
	 * Initialisation du répertoire d'échange (depuis la conf ou depuis un file
	 * chooser). <br/>
	 * <b>Le chemin doit obligatoirement avoir été saisi et être valide avant de
	 * pouvoir utiliser l'application</b>
	 */
	protected void initDirectory() {
		String directory = mMainView.chooseDirectory("src\\resources\\database");
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

	public void setConnectedUser(User user) {
		this.connectedUser = user;
		System.out.println("Nouvel utilisateur connecté: " + user.getUserTag());
	}

	private void goToHomePage() {
		this.goToTwits();
	}

	@Override
	public void disconnectUser() {
		this.connectedUser = null;
		this.goToConnexion(null);
	}

	@Override
	public void closeApp() {
		mMainView.dispatchEvent(new WindowEvent(mMainView, WindowEvent.WINDOW_CLOSING));
	}

	@Override
	public void connect(String username, String password) {
		boolean userExists = mDatabase.getUsers().stream().anyMatch(u -> u.getUserTag().equals(username) && u.getUserPassword().equals(password));
		if(userExists) {
			this.setConnectedUser(mDatabase.getUsers().stream().filter(u -> u.getUserTag().equals(username)).findFirst().get());
			this.twitsModel.setConnectedUser(connectedUser);
			this.usersModel.setConnectedUser(this.connectedUser);
			this.goToHomePage();
		} else {
			goToConnexion("Erreur: tag ou mot de passe incorrect");
		}
	}

	@Override
	public void goToRegister(String error) {
		CreateUserPanel creationPanel = new CreateUserPanel();
		creationPanel.addObserver(this);
		if(error != null) {
			creationPanel.addErrorMessage(error);
		}
		this.mMainView.setPanel(creationPanel, null);
	}

	@Override
	public void register(String tag, String password, String name, String pathToAvatar) {
		if(!validateString(tag, 5)) {
			goToRegister("Erreur: tag incorrect, il doit faire au moins 5 caractères");
		}
		else if(mDatabase.getUsers().stream().anyMatch(u -> u.getUserTag().equals(tag))) {
			goToRegister("Erreur: ce tag est déjà utilisé");
		}
		else if(!validateString(password, 8)) {
			goToRegister("Erreur: mot de passe incorrect, il doit faire au moins 8 caractères");
		}
		else if(!validateString(name, 3)) {
			goToRegister("Erreur: nom d'utilisateur incorrect, il doit faire au moins 3 caractères");
		} else if(mDatabase.getUsers().stream().anyMatch(u -> u.getName().equals(name))) {
			goToRegister("Erreur: ce nom d'utilisateur est déjà utilisé");
		}else {
			if(!validateString(pathToAvatar, 0)){
				pathToAvatar = "src\\resources\\images\\user_icon.png";
			} else {
				String newFileName ="src\\resources\\images\\profiles\\" + (new File(pathToAvatar).getName());
				FilesUtils.copyFile(pathToAvatar, newFileName);
				pathToAvatar = newFileName;
			}

			User newUser = new User(UUID.randomUUID(), tag, password, name, new HashSet<>(), pathToAvatar);
			this.mEntityManager.sendUser(newUser);
			this.setConnectedUser(newUser);
			this.usersModel.setConnectedUser(newUser);
			this.twitsModel.setConnectedUser(newUser);
			goToHomePage();
		}
	}

	@Override
	public void goToConnexion(String error) {
		ConnexionPanel connexionPanel = new ConnexionPanel();
		connexionPanel.addObserver(this);
		if(error != null) {
			connexionPanel.addErrorMessage(error);
		}
		this.mMainView.setPanel(connexionPanel, null);
	}

	private boolean validateString(String s, int minLength) {
		return s != null && !s.equals("") && s.length() >= minLength;
	}

	@Override
	public void goToProfile() {
		ProfilPanel profilPanel = new ProfilPanel(this.connectedUser);
		profilPanel.addObserver(this);
		mMainView.setPanel(profilPanel, getNavBar());
	}

	@Override
	public void goToUsers() {
		usersModel.setUsers(mDatabase.getUsers());
		UsersPanel usersPanel = new UsersPanel(usersModel);
		usersPanel.addObserver(this);
		mMainView.setPanel(usersPanel, getNavBar());
	}

	@Override
	public void goToTwits() {
		twitsModel.setTwits(mDatabase.getTwits());
		TwitsPanel createTwitPanel = new TwitsPanel(this.twitsModel);
		createTwitPanel.addObserver(this);
		mMainView.setPanel(createTwitPanel, getNavBar());
	}

	@Override
	public void createTwit(User user, String twit) {
		mEntityManager.sendTwit(new Twit(user, twit));
		twitsModel.setTwits(mDatabase.getTwits());
	}

	@Override
	public void goToExplorer() {
		ExplorerPanel explorerPanel = new ExplorerPanel();
		explorerPanel.addObserver(this);
		mMainView.setPanel(explorerPanel, getNavBar());
	}

	public NavbarPanel getNavBar() {
		NavbarPanel navbarPanel = new NavbarPanel();
		navbarPanel.addObserver(this);
		return navbarPanel;
	}
}
