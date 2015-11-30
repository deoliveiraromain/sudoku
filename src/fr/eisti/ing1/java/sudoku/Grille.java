package fr.eisti.ing1.java.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe grille contenant les nombres de la grille du sudoku
 * 
 * @author De Oliveira Méric Zullo
 * 
 */

public class Grille {
	/**
	 * la matrice 9*9 contenant les chiffres actuels de ma grille
	 */
	private int grille[][] = new int[9][9];
	/**
	 * la matrice 9*9 qui est importée depuis un fichier, nous n'aurons pas le
	 * droit de modifier ces valeurs-ci.
	 */
	private int grilleDebut[][] = new int[9][9];
	/**
	 * la matrice cubique 9*9*9 de booléens qui contient les possibilités
	 */
	private boolean cubePossibles[][][] = new boolean[9][9][9];
	/**
	 * l'entier qui indique le nombre de solutions trouvées par bruteforce.
	 */
	public int nombreDeCheminsTerminauxBruteforce = 0;

	/**
	 * constructeur qui donne des valeurs à grilleDebut et à grille à partir
	 * d'une matrice
	 * 
	 * @param matriceDebut
	 *            la matrice 9*9 d'entiers qui contient les chiffres de départ
	 */
	public Grille(int[][] matriceDebut) {
		int x = 0;
		int y = 0;
		int z = 0;
		int valcourante = 0;
		// on remplit le cube de true;
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				for (z = 0; z < 9; z++) {
					this.cubePossibles[x][y][z] = true;
				}
			}
		}
		// pour chaque case
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				valcourante = matriceDebut[x][y];
				// on met sa valeur où il faut.
				this.grilleDebut[x][y] = valcourante;
				this.grille[x][y] = valcourante;
				// si on a pas un zéro
				if (valcourante != 0) {
					// on met des falses dans le cube
					this.setFalse(x, y, valcourante);
				}
			}
		}
	}

	/**
	 * constructeur a partir d'une grille pour la récursivité
	 * 
	 * @param grille
	 *            la grille des chiffres actuels
	 * @param cube
	 *            le cube de booléens des possibilités
	 */
	private Grille(int[][] grille, boolean[][][] cube) {
		this.grille = grille;
		this.cubePossibles = cube;
	}

	/**
	 * fonction qui vide la matrice cubique des booléens (la remplit de true en
	 * fait) pour que la fenêtre puisse faire "résout" plusieurs fois.
	 */
	public void resoutMatriceWindow() {
		int x = 0;
		int y = 0;
		int z = 0;
		// on remplit le cube de true;
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				for (z = 0; z < 9; z++) {
					this.cubePossibles[x][y][z] = true;
				}
			}
		}
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				if (this.grille[x][y] != 0) {
					// on met des falses dans le cube
					this.setFalse(x, y, this.grille[x][y]);
				}
			}
		}
		this.resout();
	}

	/** fonction qui copie les valeurs de la matrice d'origine dans la grille */
	public void resetValeurs() {
		int x = 0;
		int y = 0;
		// pour chaque case
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				this.grille[x][y] = this.grilleDebut[x][y]; // on réinitialise
															// la valeur dans
															// grille
			}
		}
	}

	/**
	 * 
	 * @return la matrice courante de la grille
	 */
	public int[][] getMatrice() {
		int[][] a = new int[9][9];
		int x = 0;
		int y = 0;
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				a[x][y] = this.getValeur(x, y);
			}
		}
		return a;
	}

	/**
	 * fonction pour changer une valeur de la grille SANS VERIFICATION
	 * 
	 * WARNING !! on ne l'utilise que dans les méthodes de résolution, JAMAIS
	 * l'utilisateur ne doit y avoir accès
	 * 
	 * @param x
	 *            la ligne de la case a poser
	 * @param y
	 *            la colonne de la case a poser
	 * @param z
	 *            le chiffre a ajouter dans cette case
	 */
	private void setValeurNoCheck(int x, int y, int z) {
		this.grille[x][y] = z;
		this.setFalse(x, y, z);
	}

	/**
	 * fonction qui met des falses ou il faut dans le cube
	 * 
	 * @param x
	 *            la ligne de la case posée précédemment
	 * @param y
	 *            la colonne de la case posée précédemment
	 * @param p
	 *            la profondeur à atteindre (=le chiffre posé précédemment)
	 */
	private void setFalse(int x, int y, int p) {
		int i = 0;
		int j = 0;
		int z = p - 1; // les chiffres vont de 1 à 9, mais les index de 0 à 8,
						// donc on fait une fois -1 au début.
		int debCarreX = 3 * (int) (x / 3);
		int debCarreY = 3 * (int) (y / 3);
		for (i = 0; i < 9; i++) {
			this.cubePossibles[x][y][i] = false; // on élimine les autres
													// chiffres possibles à
													// cette case
			this.cubePossibles[x][i][z] = false; // on élimine ce chiffre dans
													// sa ligne
			this.cubePossibles[i][y][z] = false; // on élimine ce chiffre dans
													// sa colonne
		}
		for (i = debCarreX; i < debCarreX + 3; i++) {
			for (j = debCarreY; j < debCarreY + 3; j++) {
				this.cubePossibles[i][j][z] = false; // on élimine ce chiffre
														// dans son carré
			}
		}

	}

	/**
	 * Fonction pour changer le valeur en [x,y] par un z fonction utilisée par
	 * le mode graphique uniquement
	 * 
	 * 
	 * @param x
	 *            la ligne de la case ou l'on va poser une valeur
	 * @param y
	 *            la colonne de la case ou l'on veut poser une valeur
	 * @param z
	 *            le chiffre que l'on veut poser en x,y
	 * @return true si on a pu poser le chiffre dans cette case
	 */
	public boolean setValeur(int x, int y, int z) {
		/* on vérifie qu'on peut bien ajouter z en [x,y] */
		if (this.peutAjouter(x, y, z)) {
			this.grille[x][y] = z;
			return true;
		} else {
			/* si non, on renvoie faux */
			return false;
		}
	}

	/**
	 * fonction qui permet a l'utilisateur de faire des bêtises sans pour
	 * autant changer des nombres originaux.
	 * 
	 * @param x
	 *            la ligne de la case ou l'on va poser une valeur
	 * @param y
	 *            la colonne de la case ou l'on veut poser une valeur
	 * @param z
	 *            le chiffre que l'on veut poser en x,y
	 * @return true si on a pu poser le chiffre dans cette case
	 */
	public boolean setValeurUserNoVerify(int x, int y, int z) {
		if (this.checkeModifiable(x, y)) {
			this.grille[x][y] = z;
			return true;// on a écrit
		} else {
			return false;// c'était dans la grille de départ, on n'a pas
							// écrit
		}
	}

	/**
	 * Fonction qui renvoie la valeur de la grille en [x,y]
	 * 
	 * @param x
	 *            la ligne de la case dont on veut la valeur
	 * @param y
	 *            la colonne de la case dont on veut la valeur
	 * @return la valeur de la case en [x,y]
	 */
	public int getValeur(int x, int y) {
		return this.grille[x][y];
	}

	/**
	 * fonction toString qui affiche la matrice dans le terminal avec des points
	 * a la place des zéros pour y voir mieux *
	 */
	@Override
	public String toString() {
		String resultat = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (this.getValeur(i, j) == 0) {
					resultat += ".";
				} else {
					resultat += this.getValeur(i, j);
				}
			}
			resultat += "\n";
		}
		return resultat;
	}

	/**
	 * fonction toStringZeros qui affiche la matrice dans le terminal
	 * 
	 * @return la chaine de caracètre crée
	 */

	public String toStringZeros() {
		String resultat = "";
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				resultat += this.getValeur(i, j);
				if (j != 8) {
					resultat += " ";
				}
			}
			resultat += "\n";
		}
		return resultat;
	}

	/**
	 * fonction qui renvoie vrai si on peut modifier une case (elle n'était
	 * donc pas dans la matrice importée.)
	 * 
	 * @param x
	 *            la ligne de la case
	 * @param y
	 *            la colonne de la case
	 * @return true si on peut modifier cette case, false sinon
	 */
	public boolean checkeModifiable(int x, int y) {
		return (this.grilleDebut[x][y] == 0);
	}

	/**
	 * fonction qui renvoie vrai si on peut ajouter un z en [x,y] fonction
	 * utilisée par le mode graphique via setValeur
	 * 
	 * @param x
	 *            la ligne de la case
	 * @param y
	 *            la colonne de la case
	 * @param z
	 *            le chiffe que l'on veut poser dans la case
	 * @return true si on peut ajouter un z en [x,y] en respectant les règles
	 *         du sudoku
	 */
	private boolean peutAjouter(int x, int y, int z) {
		int i = 0;
		int j = 0;
		int debCarreX = 3 * (int) (x / 3);
		int debCarreY = 3 * (int) (y / 3);

		/* on vérifie que le nombre n'est pas un donné a l'origine */
		if (!this.checkeModifiable(x, y)) {
			return false;
		}
		if (z == 0) {
			return true;
		}
		for (i = 0; i < 9; i++) {
			// si on a un Z sur la ligne ou la colonne
			if (this.grille[i][y] == z || this.grille[x][i] == z) {
				return false;
			}
		}
		// on vérifie le carré
		for (i = debCarreX; i < debCarreX + 3; i++) {
			for (j = debCarreY; j < debCarreY + 3; j++) {
				if (this.grille[i][j] == z) {
					return false;
				}
			}
		}
		// sinon, on renvoie true
		return true;
	}

	/**
	 * fonction qui renvoie vrai si la grille est complétée en respectant la
	 * règle du sudoku
	 * 
	 * @return true si la grille est complétée comme il faut
	 */
	public boolean estValide() {
		Set<Integer> templist = new HashSet<Integer>(); // le set que l'on
														// manipulera pour
														// compter les nombres
		int i = 0; // les variables de parcours de la matrice dans tous les sens
		int j = 0;
		int x = 0; // deux variables pour remplir les carrés
		int y = 0;

		/*
		 * cette fonction renvoie true à la fin, et false avant a la première
		 * erreur détectée
		 */

		/* on commence par vérifier si on a des zéros dans la matrice */
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				if (this.getValeur(i, j) == 0) {
					return false;
				}
			}
		}

		/* on vérifie que chaque ligne contient bien ce qu'il faut */
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				templist.add(this.getValeur(i, j));
			}
			/* si il n'y a pas neuf chiffres dans le set */
			if (templist.size() != 9) {
				/* c'est mal. */
				return false;
			}
			templist.clear(); // on vide le set

		}

		/* puis que chaque colonne aussi */
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				templist.add(this.getValeur(j, i));
			}
			if (templist.size() != 9) {
				return false;
			}
			templist.clear(); // on vide le set
		}
		/* et enfin que chaque carré est bien fait */

		for (i = 0; i < 9; i = i + 3) {
			for (j = 0; j < 9; j = j + 3) {
				for (x = 0; x < 3; x++) {
					for (y = 0; y < 3; y++) {
						templist.add(this.getValeur(i + x, j + y));
					}
				}
				if (templist.size() != 9) {
					return false;
				}
				templist.clear(); // on vide le set.
			}
		}
		return true;
	}

	/**
	 * fonction qui sert à vérifier qu'il n'y a bien que des falses dans le
	 * cube des possibles. par conséquent, dans ce cas, on n'a plus de chiffres
	 * posables
	 * 
	 * @return true si le cube ne contient que des false, false sinon
	 */
	public boolean estValideRapide() {
		int i = 0; // les variables de parcours de la matrice cubique dans tous
					// les sens
		int j = 0;
		int k = 0;
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				for (k = 0; k < 9; k++) {
					if (this.cubePossibles[i][j][k]) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * fonction qui résout une grille en affichant les étapes en affichant le
	 * temps nécéssaire à leur résolution et le temps total.
	 * 
	 * à utiliser avec '-t' du main.
	 * 
	 * @return un tableau de deux double, respectivement le nombre de grilles
	 *         résolues et le temps nécéssaire (en ms)
	 */
	public double[] resoutTest() {
		double[] resultat = { 0, 0 };
		double timer = System.nanoTime();
		this.resout();
		timer = System.nanoTime() - timer;
		System.out.println(this.estValide() + "\n" + timer / 1000000 + " ms.");
		System.out.println(this.nombreDeCheminsTerminauxBruteforce);
		System.out.println(this);
		resultat[1] = timer / 1000000;
		if (this.estValide()) {
			resultat[0] = 1;
		} else {
			resultat[0] = 0;
		}
		return resultat;
	}

	/**
	 * fonction qui résout une grille de sudoku en combiannt dans un ordre
	 * empirique toutes les techniques dont nous disposons.
	 */
	public void resout() {
		// on fait un singleton nu a chaque fois
		this.remplitSingletonNu();
		// on tente un singleton cache a chaque fois
		if (!this.estValideRapide() && this.remplitSingletonCache()) {
			// si il a fait des choses, on recommence au début
			this.resout();
		} else {
			// sinon, on tente une élimination indirecte
			if (!this.estValideRapide() && this.remplitEliminationIndirecte()) {
				// si elle a fait des choses, on recommence au début
				this.resout();
			} else {
				if (!this.estValideRapide()) {
					// sinon, comme on n'a pas de meilleure méthode, on
					// bruteforce
					this.bruteforce();
				}
			}
		}
	}

	/**
	 * fonction utilisée par resout en dernier recours quand plus aucune des
	 * fonctions rapide ne fonctionne. elle va récursivement tenter toutes les
	 * possibilités restantes dans la grille courante en rappellant resout()
	 * pour profiter des fonctions rapides
	 */
	private void bruteforce() {
		int i = 0;
		int j = 0;
		int indexPilier = 0;
		// si la grille n'est pas finie
		if (!this.estValideRapide()) {
			// on va tenter tous les nombres possibles là ou ils le sont.
			for (i = 0; i < 9; i++) {
				for (j = 0; j < 9; j++) {
					// pour chaque nombre possible (on ne fait pas de liste, on
					// reparcourt juste le pilier de la case
					for (indexPilier = 0; indexPilier < 9; indexPilier++) {
						// on essaye en posant un chiffre
						if (this.cubePossibles[i][j][indexPilier]) {
							// on crée une nouvelle grille similaire à
							// l'actuelle
							Grille grilleActuelle = new Grille(this.grille, this.cubePossibles);
							// on y met notre supposition
							grilleActuelle.setValeurNoCheck(i, j, indexPilier + 1);
							grilleActuelle.setFalse(i, j, indexPilier + 1);
							grilleActuelle.resout();
							// on regarde si la grille qu'on a tenté de
							// résoudre
							// est valide
							if (grilleActuelle.estValide()) {
								this.grille = grilleActuelle.grille;
								this.cubePossibles = grilleActuelle.cubePossibles;
								this.nombreDeCheminsTerminauxBruteforce = this.nombreDeCheminsTerminauxBruteforce
										+ grilleActuelle.nombreDeCheminsTerminauxBruteforce + 1;
							}
						}
					}
				}
			}

		} else {
			/*
			 * condition terminale de la récursivité,
			 * 
			 * c'est qu'on a une grille dont le cube est plein de false. du
			 * coup, on regarde si il reste un zéro dans la grille
			 */
			for (i = 0; i < 9; i++) {
				for (j = 0; j < 9; j++) {
					// on ne passe pas par getValeur pour des raisons de
					// vitesse.
					if (this.grille[i][j] == 0) {
					}
				}
			}
			System.out.println(this);
		}
	}

	/**
	 * fonction qui remplit ce qui peut l'être dans une grille sans enregistrer
	 * les opérations effectuées
	 * 
	 * @return true si la fonction a apporté des changements au système
	 */
	private boolean remplitSingletonNu() {
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		int coordPossible = 0;
		boolean aModifie = false;
		boolean fini = false;
		// tant qu'on croit qu'on peut faire une opération
		while (!fini) {
			fini = true;
			// pour chaque case
			for (i = 0; i < 9; i++) {
				for (j = 0; j < 9; j++) {
					// si la case est vide
					if (this.getValeur(i, j) == 0) {

						// on regarde ce que l'on peut mettre dans le cube des
						// possibilités
						y = 0;// nombre de true;

						for (x = 0; x < 9; x++) {
							if (this.cubePossibles[i][j][x]) {
								y++;
								coordPossible = x + 1;
							}
						}
						// si il ne reste qu'un chiffre possible, on l'y met.
						if (y == 1) {
							this.setValeurNoCheck(i, j, coordPossible);
							// on dit que l'on a fait quelque chose
							if (!aModifie) {
								aModifie = true;
							}
							// et on dit qu'il faut peut être encore faire des
							// choses
							fini = false;
						}
					}
				}
			}
		}

		// System.out.println(this.toString());
		return aModifie;
	}

	/**
	 * procédure qui replit la grille de manière juste en trouvant les
	 * singletons cachés
	 * 
	 * @return si la fonction a apporté des changements au système
	 */
	private boolean remplitSingletonCache() {
		boolean aModifie = false;
		int chiffreTeste = 0;
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		int xCoordZero = 0;
		int yCoordZero = 0;
		int nombreZeros = 0;
		// pour chaque chiffre (plan du cube)
		for (chiffreTeste = 0; chiffreTeste < 9; chiffreTeste++) {
			// dans chaque carré
			for (i = 0; i < 9; i += 3) {
				for (j = 0; j < 9; j += 3) {
					nombreZeros = 0;
					// on compte le nombre de possibilités (de true )
					for (x = 0; x < 3; x++) {
						for (y = 0; y < 3; y++) {
							if (this.cubePossibles[i + x][j + y][chiffreTeste]) {
								nombreZeros++;
								xCoordZero = i + x;
								yCoordZero = j + y;
							}
						}
					}
					// si on ne trouve qu'un zéro
					if (nombreZeros == 1) {
						// on retrouve ses coordonnees gràce à (x|y)CoordZero
						// et
						// on fait ce qu'il faut
						this.setValeurNoCheck(xCoordZero, yCoordZero, chiffreTeste + 1);
						// on met des false dans la ligne, la colonne et le
						// carré
						this.setFalse(xCoordZero, yCoordZero, chiffreTeste + 1);
						// et on dit qu'on a fait un truc.
						aModifie = true;
					}
				}
			}
			// de même pour chaque ligne
			for (i = 0; i < 9; i++) {
				// on compte le nombre de true
				nombreZeros = 0;
				for (j = 0; j < 9; j++) {
					if (this.cubePossibles[i][j][chiffreTeste]) {
						nombreZeros++;
						xCoordZero = j; // on enregistre la colonne du dernier
										// true rencontré
					}
				}
				// si on n'a qu'un seul zero
				if (nombreZeros == 1) {
					// on y met un dans la colonne et on écrit le chiffre
					// idoine
					// dans la matrice
					this.setValeurNoCheck(i, xCoordZero, chiffreTeste + 1);
					this.setFalse(i, xCoordZero, chiffreTeste + 1);
					aModifie = true;
				}
			}

			// et pour chaque colonne.
			for (i = 0; i < 9; i++) {
				// on compte le nombre de true
				nombreZeros = 0;
				for (j = 0; j < 9; j++) {
					if (this.cubePossibles[j][i][chiffreTeste]) {
						nombreZeros++;
						xCoordZero = j; // on enregistre la colonne du dernier
										// true rencontré
					}
				}
				// si on n'a qu'un seul zero
				if (nombreZeros == 1) {
					// on y met un dans la colonne et on écrit le chiffre
					// idoine
					// dans la matrice
					this.setValeurNoCheck(xCoordZero, i, chiffreTeste + 1);
					this.setFalse(xCoordZero, i, chiffreTeste + 1);
					aModifie = true;
				}
			}
		}

		// System.out.println(this.toString());
		return aModifie;
	}

	/**
	 * il s'agit ici de détécter, pour chaque chiffre, les carrés dans
	 * lesquels on n'a que deux possibilités alignées. Dans ces cas là, on
	 * sait que les autres emplacements de la ligne sont impossibles a remplir
	 * avec ce chiffre.
	 * 
	 * @return true uniquement si la fonction a apporté des modifications au
	 *         système
	 */
	private boolean remplitEliminationIndirecte() {
		boolean aModifie = false;

		int xCarre = 0;
		int yCarre = 0;
		int xLocal = 0;
		int yLocal = 0;
		int profondeur = 0;
		int nombreZeros = 0;
		int placeurFalse = 0;
		ArrayList<Integer> coordonneesZeros = new ArrayList<Integer>();
		// pour chaque chiffre
		for (profondeur = 0; profondeur < 9; profondeur++) {
			// dans chaque carré
			for (xCarre = 0; xCarre < 9; xCarre = xCarre + 3) {
				for (yCarre = 0; yCarre < 9; yCarre = yCarre + 3) {
					// on compte le nombre de zéros (de true)
					nombreZeros = 0;
					for (xLocal = 0; xLocal < 3; xLocal++) {
						for (yLocal = 0; yLocal < 3; yLocal++) {
							if (this.cubePossibles[xCarre + xLocal][yCarre + yLocal][profondeur]) {
								nombreZeros++;
							}
						}
					}
					// si on en a deux
					if (nombreZeros == 2) {
						// on va vérifier leur alignement
						// on commence par mettre leurs quatre coordonnées dans
						// coordonneesZeros
						coordonneesZeros.clear();
						for (xLocal = 0; xLocal < 3; xLocal++) {
							for (yLocal = 0; yLocal < 3; yLocal++) {
								if (this.cubePossibles[xCarre + xLocal][yCarre + yLocal][profondeur]) {
									coordonneesZeros.add(xCarre + xLocal);
									coordonneesZeros.add(yCarre + yLocal);
								}
							}
						}
						// si les deux lignes sont les mêmes
						if (coordonneesZeros.get(0) == coordonneesZeros.get(2)) {
							// on place des false sur le reste de la ligne
							for (placeurFalse = 0; placeurFalse < 9; placeurFalse++) {
								// si on n'est pas dans les colonnes de nos deux
								// zéros, on met un false
								if (placeurFalse != coordonneesZeros.get(1)
										&& placeurFalse != coordonneesZeros.get(3)) {
									// on met un false dans la bonne ligne, la
									// colonne courante, et la profondeur
									// actuelle
									if (this.cubePossibles[coordonneesZeros.get(0)][placeurFalse][profondeur]) {
										this.cubePossibles[coordonneesZeros.get(0)][placeurFalse][profondeur] = false;
										aModifie = true;
									}
								}
							}
						}
						// de même pour les colonnes
						if (coordonneesZeros.get(1) == coordonneesZeros.get(3)) {
							// on place des false sur le reste de la colonne
							for (placeurFalse = 0; placeurFalse < 9; placeurFalse++) {
								// si on n'est pas dans les lignes de nos deux
								// zéros, on met un false
								if (placeurFalse != coordonneesZeros.get(0)
										&& placeurFalse != coordonneesZeros.get(2)) {
									// on met un false dans la ligne courante,
									// la bonne colonne, et la profondeur
									// actuelle
									if (this.cubePossibles[placeurFalse][coordonneesZeros.get(1)][profondeur]) {
										this.cubePossibles[placeurFalse][coordonneesZeros.get(1)][profondeur] = false;
										aModifie = true;
									}
								}
							}
						}
					}

				}
			}
		}
		return aModifie;
	}
}