package fr.eisti.ing1.java.sudoku;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
//import java.util.ArrayList;
import java.util.Random;

/**
 * Classe implémentant l'interface graphique et toutes les méthodes pouvant
 * être appelées par l'utilisateur
 * 
 * @author De Oliveira Méric Zullo
 *
 *
 */
public class SudokuWindow extends JFrame {

	/**
	 * attribut permettant la sérialisation de la classe SudokuWindow
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Attribut grille permettant d'associer une grille à la fenêtre
	 */
	private Grille grille;

	/**
	 * Attribut représentant un tableau de labels, chaque case de ce tableau va
	 * contenir un label représentant la valeur de la case correspondante à ce
	 * label dans la grille
	 */
	private LabelGrille[][] tabLabel;

	/**
	 * Attribut représentant un tableau de boutons, ces boutons représenteront
	 * des chiffres sur lesquels l'utilisateur pourra appuyer afin de modifier
	 * le contenu d'une case
	 */
	private JButton[] tabBoutonsChiffre;

	/**
	 * Attribut représentant un tableau de boutons, ces boutons représenteront
	 * les différentes options que l'utilisateur pourra appeller
	 */
	private JButton[] tabBoutonsOptions;

	/**
	 * Attribut représentant le label selectionné par l'utilisateur
	 */
	private LabelGrille labelSelected;

	/**
	 * Attribut représentant un booléen qui permet de savoir si on est en mode
	 * saisie sécurisée ou pas
	 */
	private boolean modesecu;

	/**
	 * Attribut booléen qui informe sur la situation de la partie : gagnée ou
	 * en cours
	 * 
	 */
	private boolean partieGagnee;

	/**
	 * Constructeur de l'interface graphique, permet l'initialisation de la
	 * fenetre
	 */
	public SudokuWindow() {

		// les 3 sous méthodes amenant la construction de l'interface
		this.initModele();
		this.initComposants(grille);
		this.initEvents();

	}

	/**
	 * Méthode permettant d'initialiser les valeur appartenant au modèle
	 * 
	 * @see Generateur
	 */
	private void initModele() {

		this.grille = Generateur.renvoieGrille((int) (20 + (new Random().nextInt(8))));

		this.modesecu = false;

		this.partieGagnee = false;

	}

	/**
	 * Méthode d'initialisation des composants de la fenetre
	 * 
	 * @param grille
	 *            permet d'initialiser la fenetre à partir d'une grille en
	 *            paramètre
	 */
	private void initComposants(Grille grille) {

		this.setTitle("Sudoku ! by DeOliveira_Méric_Zullo");
		this.setPreferredSize(new Dimension(650, 600));

		// panel principal qui contient la zone des carrés et la zone des
		// boutons
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		Border whiteline = BorderFactory.createLineBorder(Color.white, 2);
		Border grayline = BorderFactory.createLineBorder(Color.gray, 1);

		// panel contenant les 9 carrés de jeu
		JPanel grillePanel = new JPanel();
		grillePanel.setLayout(new GridLayout(3, 3));
		grillePanel.setBorder(whiteline);

		// les 9 carrés sont des Panels qui vont contenir leur 9 labels
		// correspondants
		JPanel[] tabPanel = new JPanel[9];

		// on initialise les 9 panels, on les ajoute au panel contenant les
		// carrés

		for (int i = 0; i <= 8; ++i) {
			tabPanel[i] = new JPanel();
			tabPanel[i].setBorder(whiteline);
			tabPanel[i].setLayout(new GridLayout(3, 3));
			grillePanel.add(tabPanel[i]);
		}

		// on ajoute le panel des 9 carrés au panel principal
		panel.add(grillePanel, BorderLayout.CENTER);

		this.tabLabel = new LabelGrille[9][9];

		// BOUCLE D'INITIALISATION des paramètres des labels qui ne changeront
		// jamais

		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; ++j) {

				this.tabLabel[i][j] = new LabelGrille("", i, j);

				this.tabLabel[i][j].setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

				this.tabLabel[i][j].setBorder(grayline);

			}

		}

		// fonction d'initialisation des valeurs des labels et des paramètres
		// restants
		this.initLabels();

		// BOUCLE D'AJOUT des Labels dans leur Carré correspondant
		// on ajoute à chaque panel les numéros des labels qui lui
		// correspondent
		// pour ça on utilise la fonction de conversion définie plus bas
		for (int i = 0; i <= 8; i++) {
			for (int j = 0; j <= 8; ++j) {

				int x = convertXtoMatrice(i, j);
				int y = convertYtoMatrice(i, j);

				tabPanel[i].add(tabLabel[x][y]);

			}

		}

		// Panel contenant tous les boutons

		JPanel boutons = new JPanel();
		boutons.setLayout(new GridLayout(2, 1));

		// panel clavier contenant les chiffres
		JPanel clavier = new JPanel();

		// panel options contenant les différentes options
		JPanel options = new JPanel();
		options.setLayout(new GridLayout(2, 3));

		// tableau de boutons de chiffres et tableau de boutons pour les options
		tabBoutonsChiffre = new JButton[9];
		tabBoutonsOptions = new JButton[6];

		// les différents boutons d'options
		tabBoutonsOptions[0] = new JButton("Changer de grille");

		// le symbole => veut dire passer en ...
		tabBoutonsOptions[1] = new JButton("=> saisie sécurisée");
		tabBoutonsOptions[2] = new JButton("Résolution");

		tabBoutonsOptions[3] = new JButton("Vérification");
		tabBoutonsOptions[4] = new JButton("Reset");
		tabBoutonsOptions[5] = new JButton("Effacer");

		// on rajoute chaque bouton d'option à son panel
		for (int i = 0; i <= 5; ++i) {
			options.add(tabBoutonsOptions[i]);
		}

		// on crée et rajoute chaque bouton de chiffre à son panel
		for (int i = 0; i <= 8; ++i) {
			tabBoutonsChiffre[i] = new JButton(i + 1 + "");
			clavier.add(tabBoutonsChiffre[i]);
		}

		// on rajoute les 2 sous panels de boutons au panel principal de boutons
		boutons.add(clavier);
		boutons.add(options);
		panel.add(boutons, BorderLayout.SOUTH);

		// on rajoute le panel principal à la fenetre
		this.add(panel);
		this.pack();

	}

	// fonction d'initialisation de la gestion des événements
	private void initEvents() {

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new ConfirmationListener(this));
		// on rajoute à la fenetre le fait de gérer l'événement
		// "cliquer sur la croix ou faire alt+f4"

		// on rajoute à chaque label le fait de pouvoir être selectionné
		for (int i = 0; i <= 8; ++i) {
			for (int j = 0; j <= 8; ++j) {
				this.tabLabel[i][j].addMouseListener(new GrilleLabelListener(this));
			}
		}

		// on rajoute à chaque bouton d'options le fait de gérer son
		// événement
		tabBoutonsOptions[0].addActionListener(new SwitchGrilleListener(this));
		tabBoutonsOptions[1].addActionListener(new SecurityListener(this));
		tabBoutonsOptions[2].addActionListener(new ResolveListener(this));
		tabBoutonsOptions[3].addActionListener(new VictoryListener(this));
		tabBoutonsOptions[4].addActionListener(new ResetListener(this));
		tabBoutonsOptions[5].addActionListener(new DeleteListener(this));

		// on rajoute à chaque bouton chiffre la gestion de son chiffre
		for (int i = 0; i <= 8; ++i) {
			this.tabBoutonsChiffre[i].addActionListener(new ChiffreListener(this));
		}

		this.setVisible(true);
	}

	// fonction d'initialisation des labels
	// on remplit les labels des valeurs contenues dans la matrice grille
	// utilisée

	/**
	 * Méthode d'initialisation des labels
	 * 
	 * @see Grille
	 * 
	 */
	private void initLabels() {
		int valtmp;

		for (int i = 0; i <= 8; ++i) {
			for (int j = 0; j <= 8; ++j) {

				valtmp = this.grille.getValeur(i, j);

				// si dans la grille la valeur est 0, cela veut dire inconnu
				// du coup on affiche rien
				if (valtmp == 0) {
					this.tabLabel[i][j].setText("");

				}
				// sinon on remplit avec le chiffre contenu
				else {
					this.tabLabel[i][j].setText(valtmp + "");

				}
				// Si la valeur est modifiable, on la passe en noir,
				// si elle est de base et non changeable, elle sera en rouge
				if (!this.grille.checkeModifiable(i, j)) {
					this.tabLabel[i][j].setForeground(Color.RED);
				} else {
					this.tabLabel[i][j].setForeground(Color.black);
				}

			}
		}

	}

	// Méthode pour donner une valeur à labelselected
	// méthode appelée par chaque label lorsque l'utilisateur clique dessus

	/**
	 * Méthode permettant de mettre en place le label choisi par l'utilisateur
	 * 
	 * @param labelSelected
	 *            le label passé en paramètre et qui deviendra le
	 *            labelSelectionné dans la fenetre
	 * @see Grille
	 */
	public void setLabelGrilleSelectionne(LabelGrille labelSelected) {

		// SI on avait déjà un label selectionné, on le repasse en couleur
		// normale
		if (this.labelSelected != null) {
			this.labelSelected.setOpaque(false);
			this.labelSelected.setBackground(Color.GRAY);
		}

		// on associe l'attribut labelchoisi de la fenetre au label choisir par
		// l'utilisateur
		this.labelSelected = labelSelected;
		this.labelSelected.setOpaque(true);

		// on récupère les coordonnées du label dans la grille par ses
		// méthodes
		// getters

		int i = this.labelSelected.getI();
		int j = this.labelSelected.getJ();

		// SI le label selectionné renvoit à une valeur de base non
		// modifiable,
		// il sera gris
		if (!this.grille.checkeModifiable(i, j)) {

			this.labelSelected.setBackground(Color.LIGHT_GRAY);
		}
		// si par contre il renvoit à une valeur modifiable il sera vert
		else {
			this.labelSelected.setBackground(new Color(0, 200, 0));

		}
	}

	// Méthode pour supprimer le contenu d'une case,
	// permet à l'utilisateur d'y voir plus clair s'il le souhaite
	/**
	 * Méthode permettant la suppression du contenu de la case selectionnée
	 * par l'utilisateur
	 * 
	 * @see Grille
	 */
	public void deleteCaseSelectionnee() {

		// Si aucune case n'est selectionnée on ne fait rien
		if (labelSelected != null) {

			// on récupère les coordonnées dans la grille du label
			// selectionné

			int i = this.labelSelected.getI();
			int j = this.labelSelected.getJ();

			// Si on peut mettre à la case selectionnée,
			// c'est qu'elle est modifiable et qu'on a réussit à changer sa
			// valeur

			if (grille.setValeur(i, j, 0)) {

				// si l'affectation a réussit, on remet le label à vide
				this.labelSelected.setText("");
				// SI jamais on avait gagné la partie, on recontrole l'état de
				// la partie après cette opération
				this.setEtatPartie();
			}
		}
	}

	// Méthode pour reset la grille si l'utilisateur en a envie

	/**
	 * Méthode permettant de remettre à 0 la grille, si l'utilisateur en a
	 * l'envie
	 * 
	 * @see Grille
	 */
	public void resetGrille() {

		// on demande confirmation
		int rep = JOptionPane.showConfirmDialog(this, "êtes vous sûr de vouloir remettre la grille à 0 ?");

		// s'il répond oui, on reset
		if (rep == JOptionPane.YES_OPTION) {

			// on reset la grille
			this.grille.resetValeurs();
			// on réinitialise ensuite les labels
			this.initLabels();
			// on controle l'état de la partie après cette opération
			setEtatPartie();
		}
	}

	// Méthode pour ajouter un chiffre à la grille (ou modifier un chiffre
	// déjà
	// présent)

	/**
	 * Méthode permettant de rajouter un chiffre passé en paramètre à la
	 * case actuellement selectionnée
	 * 
	 * @param chiffre
	 * @see Grille
	 */
	public void ajouterChiffre(int chiffre) {

		// Si aucune case n'est selectionnée, on ne fait rien
		if (labelSelected != null) {

			// on récupère les coordonnées dans la grille de la case
			// selectionnée dans la fenetre
			int i = this.labelSelected.getI();
			int j = this.labelSelected.getJ();

			// Suivant le mode de jeu on n'ajoute pas de la meme manière

			// Si l'ajout est sécurisé,
			// on utilise la fonction de modification de la grille qui regarde
			// si l'ajout est possible
			if (this.modesecu) {

				// Si l'ajout a réussi, on modifie le label en conséquence
				if (grille.setValeur(i, j, chiffre)) {

					this.labelSelected.setText(grille.getValeur(i, j) + "");
				} else {
					// Si on a pas réussit à ajouter mais que la valeur est
					// quand même modifiable
					// c'est que la valeur n'est pas de base, mais que par
					// rapport à la situation et aux règles
					// l'ajout du chiffre voulu est impossible
					if (this.grille.checkeModifiable(i, j)) {

						// on affiche donc un message
						JOptionPane.showMessageDialog(null, "Vous ne pouvez pas ajouter de " + chiffre + " ici",
								"Attention !", JOptionPane.INFORMATION_MESSAGE);

					}
				}
			}
			// Le mode d'ajout libre permet de mettre une valeur dans la grille
			// sans rien vérifier d'autre que
			// le fait que la case ne soit pas de base
			else {
				if (this.grille.setValeurUserNoVerify(i, j, chiffre)) {
					// si on a réussit à modifier la casen on modifie le label
					// en conséquence
					this.labelSelected.setText(grille.getValeur(i, j) + "");
				}
			}

			// on controle l'état de la partie après l'ajout du chiffre
			this.setEtatPartie();
		}
	}

	// Méthode permettant de controler l'état de la partie
	/**
	 * Méthode permettant de mettre à jour l'état de la partie représenté
	 * par le booléen partiGagnee suivant la situation actuelle (partie en
	 * cours ou partie gagnée)
	 * 
	 * @see Grille
	 * 
	 */
	private void setEtatPartie() {
		// On cotrole l'état de la partie en appelant la méthode qui permet de
		// dire si la grille est valide ou pas
		// SI oui on passe le booléen à vrai
		if (this.grille.estValide()) {
			this.partieGagnee = true;
		} else {
			// Sinon on le passe à faux (au cas où la partie était finie mais
			// qu'une modification a été faite après)
			this.partieGagnee = false;
		}
	}

	// Méthode permettant de changer de mode de saisie
	/**
	 * Méthode permettant de changer le mode de saisie permet à l'utilisateur
	 * de passer en mode saisie sécurisée ou saisie libre
	 * 
	 */
	public void changerModeSaisie() {

		// on passe à l'autre mode en changeant la valeur du booléen
		this.modesecu = !this.modesecu;

		// Si on est en mode sécurisé, on change le nom du bouton pour qu'il
		// permette de passer en saisie libre
		if (this.modesecu) {
			this.tabBoutonsOptions[1].setText("=> saisie libre");
		}
		// Si on est en mode saisie libre, on change le nom du bouton pour qu'il
		// permette de passer en saisie sécurisée
		else {
			this.tabBoutonsOptions[1].setText("=> saisie sécurisée");
		}
	}

	// Méthode signalant à l'utilisateur si la partie est terminée ou pas
	// par l'affichage d'un message suivant la situation

	/**
	 * Méthode permettant d'afficher un message suivant la situation de la
	 * partie lorsque l'utilisateur le demande
	 * 
	 */
	public void checkEtatPartie() {
		if (this.partieGagnee) {
			JOptionPane.showMessageDialog(null, "Vous avez gagné !", "Victoire", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "La partie n'est pas finie...", "Pas fini !",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// Méthode permettant à l'utilisateur de voir la solution de la grille
	// s'il
	// le souhaite
	/**
	 * Méthode permettant lorsque l'utilisateur le souhaite, de résoudre la
	 * grille affichée
	 * 
	 */
	public void resolve() {

		// on demande confirmation
		int rep = JOptionPane.showConfirmDialog(this,
				"êtes vous sûr de vouloir résoudre la grille d'après votre jeu ?");

		// S'il dit oui, on résout la grille et en réinitialise les labels en
		// conséquence
		if (rep == JOptionPane.YES_OPTION) {
			this.grille.resoutMatriceWindow();

			this.initLabels();
			// on controle l'état de la partie
			this.setEtatPartie();
		}
	}

	// Méthode demandant à l'utilisateur une confirmation s'il souhaite
	// quitter
	// le jeu
	/**
	 * Méthode permettant de demander une confirmation à l'utilisateur
	 * lorsqu'il veut quitter la fenetre
	 * 
	 */
	public void confirmation() {

		int rep = JOptionPane.showConfirmDialog(this, "êtes vous sûr :-( ?!?!");
		if (rep == JOptionPane.YES_OPTION) {

			this.dispose();

		}
	}

	// Méthode permettant à l'utilisateur de changer de grille
	/**
	 * Méthode permettant à l'utilisateur de changer de grille le programme
	 * génerera alors une nouvelle grille
	 * 
	 * @see Grille
	 * @see Generateur
	 */
	public void switchGrille() {
		// on demande confirmation
		int rep = JOptionPane.showConfirmDialog(this, "êtes vous sûr de vouloir changer de grille ?");
		if (rep == JOptionPane.YES_OPTION) {
			// s'il répond oui, on reset la grille (car s'il rechange de grille
			// plus tard et qu'il retombe sur la grille
			// actuelle, il faut qu'elle soit remise à 0)
			this.grille.resetValeurs();

			// on choisit une grille aléatoire parmi la liste de grilles que la
			// fenetre à sa disposition

			// this.grille=listeGrilles.get(new
			// Random().nextInt(listeGrilles.size()));

			this.grille = Generateur.renvoieGrille((int) (20 + (new Random().nextInt(8))));

			// on réinitialise les labels
			this.initLabels();
			// on recontrole encore l'état de la partie
			this.setEtatPartie();
		}
	}

	// Méthode servant à l'ajout aux panels correpondant des labels de la
	// fenetre
	/**
	 * Méthode permettant de passer des cordonnées de la fenetre ( blocs
	 * ajoutés en GridLayout ) aux coordonées correspondantes dans la matrice
	 * 
	 * @param i
	 *            l'abscisse dans la fenetre
	 * @param j
	 *            l'ordonnée dans la fenetre
	 * @return l'abscisse dans la matrice
	 */
	private int convertXtoMatrice(int i, int j) {
		int resultat = 3 * (int) (i / 3) + (int) (j / 3);
		return resultat;
	}

	/**
	 * Méthode permettant de passer des cordonnées de la fenetre ( blocs
	 * ajoutés en GridLayout ) aux coordonées correspondantes dans la matrice
	 * 
	 * @param i
	 *            l'abscisse dans la fenetre
	 * @param j
	 *            l'ordonnée dans la fenetre
	 * @return l'ordonnée dans la matrice
	 */

	private int convertYtoMatrice(int i, int j) {
		return (int) ((j % 3 + 3 * i) % 9);
	}

}
