package fr.eisti.ing1.java.sudoku;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Classe Generateur, permettant la création de grilles de Sudoku de manière
 * aléatoire
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class Generateur {

	/**
	 * Fonction permettant de générer une matrice d'entiers avec n valeurs
	 * apparentes
	 * 
	 * @param n
	 *            nombre de valeurs à laisser apparente dans la grille de
	 *            Sudoku
	 * @return un tableau de tableau d'entiers (grille de taille 9*9)
	 */

	public static int[][] genereMatriceAleatoire(int n) {
		int i = 0;// variables de parcours du carré en haut a gauche
		int j = 0;
		int a = 0;// variables de parcours des carrés dans la grille
		int b = 0;
		int entierCourant = 0;
		int[][] matriceGeneree = new int[9][9];

		Random random = new Random(); // initialisation du générateur de
										// nombres
										// aléatoires.

		// On remplit la matrice de 0
		for (i = 0; i < 9; ++i) {
			for (j = 0; j < 9; ++j) {
				matriceGeneree[i][j] = 0;
			}
		}
		while (!estValide(matriceGeneree)) {
			// On remplit la matrice de 0
			for (i = 0; i < 9; ++i) {
				for (j = 0; j < 9; ++j) {
					matriceGeneree[i][j] = 0;
				}
			}
			// pour toutes les cases du carré en haut à droite
			for (i = 0; i < 3; i++) {
				for (j = 0; j < 3; j++) {
					// on choisit un chiffre non nul qui n'est pas dans le
					// carré
					entierCourant = random.nextInt(10);
					while (entierCourant != 0 && estDansCarre(entierCourant, matriceGeneree)) {
						entierCourant = random.nextInt(10);
					}
					// mainenant, on le place dans les 9 carrés.
					for (a = 0; a < 9; a = a + 3) {
						for (b = 0; b < 9; b = b + 3) {
							matriceGeneree[i + a][j + b] = entierCourant;
							entierCourant = (entierCourant + 1) % 10;
							if (entierCourant == 0) {
								entierCourant++;
							}
						}
					}
				}
			}
		}
		int valrand;
		int valrand2;
		for (int h = 0; h < 7; ++h) {
			permutationValeurs(matriceGeneree, (int) Math.floor(random.nextInt(9) + 1),
					(int) Math.floor(random.nextInt(9) + 1));
			valrand = (int) Math.floor(random.nextInt(9));
			valrand2 = (int) Math.floor(random.nextInt(2) + valrand % 3);
			permutationColonnes(matriceGeneree, valrand, valrand2);
			valrand = (int) Math.floor(random.nextInt(9));
			valrand2 = (int) Math.floor(random.nextInt(2) + valrand % 3);
			permutationLignes(matriceGeneree, valrand, valrand2);
		}
		int[][] res2;
		// do {
		res2 = enleveChiffres(matriceGeneree, n);
		// } while (res2 solvable);
		for (i = 0; i < 9; ++i) {
			for (j = 0; j < 9; ++j) {
			}
		}
		return res2;
	}

	/**
	 * Fonction qui cherche si un entier est déjà placé dans le carré en
	 * haut à gauche d'une matrice
	 * 
	 * @param n
	 *            entier à vérifier
	 * @param matrice
	 *            dans laquelle on fouille le premier carré
	 * @return vrai si n est dans le premier carré de la matrice, faux sinon
	 */
	private static boolean estDansCarre(int n, int[][] matrice) {
		int i = 0;
		int j = 0;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				if (matrice[i][j] == n) {
					return true;
				}
			}
		}
		return false;

	}

	/**
	 * Fonction qui détermine si une matrice de Sudoku est valide
	 * 
	 * @param matrice
	 *            dont on souhaite déterminer la validité
	 * @return vrai si la matrice est valide, faux sinon
	 */

	private static boolean estValide(int[][] matrice) {
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
				if (matrice[i][j] == 0) {
					return false;
				}
			}
		}

		/* on vérifie que chaque ligne contient bien ce qu'il faut */
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				templist.add(matrice[i][j]);
			}
			if (templist.size() != 9) {
				return false;
			}
			templist.clear(); // on vide le set

		}

		/* puis que chaque colonne aussi */
		for (i = 0; i < 9; i++) {
			for (j = 0; j < 9; j++) {
				templist.add(matrice[i][j]);
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
						templist.add(matrice[i + x][j + y]);
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
	 * Fonction qui génère une liste de grilles de Sudoku aléatoirement
	 * 
	 * @param n
	 *            nombre de chiffres à laisser apparent dans chaque grille
	 * @param m
	 *            nombre de grilles à générer
	 * @return une liste de grilles générées aléatoirement
	 */

	public static ArrayList<Grille> genereListeGrille(int n, int m) {
		// nombre de cases remplies dans chaque grille et le nombre de grilles
		ArrayList<Grille> listeResultat = new ArrayList<>();
		for (int i = 0; i < m; ++i) {
			listeResultat.add(new Grille(genereMatriceAleatoire(n)));
		}
		return listeResultat;
	}

	/**
	 * Fonction qui renvoie une nouvelle grille générée aléatoirement dont n
	 * chiffres sont apparents
	 * 
	 * @param n
	 *            nombre de chiffres apparents
	 * @return une grille générée aléatoirement avec n chiffres apparents
	 */
	public static Grille renvoieGrille(int n) {// nombre de cases remplies à la
												// fin qui
		int x = 0;
		int y = 0;
		// renvoie une grille générée
		// aléatoirement

		Grille grilleGeneree = new Grille(genereMatriceAleatoire(n));

		/*
		 * on s'assure que la grille générée est bien résoluble en
		 * résolvant une copie de celle-ci.
		 */
		Grille copie = grilleGeneree;
		copie.resout();
		// tant qu'elle n'est pas valide
		while (!copie.estValide()) {
			// on la régénère
			grilleGeneree = new Grille(genereMatriceAleatoire(n));
			// on la recopie
			copie = new Grille(grilleGeneree.getMatrice());
			// on résout la copie
			copie.resout();
		}
		for (x = 0; x < 9; x++) {
			for (y = 0; y < 9; y++) {
				grilleGeneree.setValeurUserNoVerify(x, y, 0);
			}
		}
		return grilleGeneree;
	}

	/**
	 * Fonction qui permute deux valeurs dans un tableau de tableau d'entiers
	 * 
	 * @param tab
	 *            matrice dans laquelle on souhaite permuter les valeurs
	 * @param val1
	 *            Valeur à permuter avec val2
	 * @param val2
	 *            Valeur à permuter avec val1
	 * @return une matrice identique à celle passée en paramètre, avec les
	 *         valeurs val1 et val2 interverties dans toute la grille
	 */

	private static int[][] permutationValeurs(int[][] tab, int val1, int val2) {
		int[][] res = new int[9][9];
		int tmpValeur;
		for (int k = 0; k < 9; ++k) {
			for (int l = 0; l < 9; ++l) {
				tmpValeur = tab[k][l];
				if (tmpValeur == val1) {
					res[k][l] = val2;
				} else if (tmpValeur == val2) {
					res[k][l] = val1;
				} else {
					res[k][l] = tmpValeur;
				}
			}
		}
		return res;
	}

	/**
	 * Fonction qui permute deux colonnes dans un tableau de tableau d'entiers
	 * 
	 * @param tab
	 *            matrice dans laquelle on souhaite permuter les colonnes
	 * @param i
	 *            Indice de la colonne à permuter avec la colonne j
	 * @param j
	 *            Indice de la colonne à permuter avec la colonne i
	 * @return une matrice identique à celle passée en paramètre, avec les
	 *         colonnes i et j interverties
	 */

	private static int[][] permutationColonnes(int[][] tab, int i, int j) {
		int[][] res = new int[9][9];
		for (int k = 0; k < 9; ++k) {
			for (int l = 0; l < 9; ++l) {
				if (l == i) {
					res[k][l] = tab[k][j];
				} else if (l == j) {
					res[k][l] = tab[k][i];
				} else {
					res[k][l] = tab[k][l];
				}
			}
		}
		return res;
	}

	/**
	 * Fonction qui permute deux lignes dans un tableau de tableau d'entiers
	 * 
	 * @param tab
	 *            matrice dans laquelle on souhaite permuter les lignes
	 * @param i
	 *            Indice de la ligne à permuter avec la ligne j
	 * @param j
	 *            Indice de la ligne à permuter avec la ligne i
	 * @return une matrice identique à celle passée en paramètre, avec les
	 *         lignes i et j interverties
	 */

	private static int[][] permutationLignes(int[][] tab, int i, int j) {
		int[][] res = new int[9][9];
		for (int k = 0; k < 9; ++k) {
			for (int l = 0; l < 9; ++l) {
				if (k == i) {
					res[k][l] = tab[j][l];
				} else if (k == j) {
					res[k][l] = tab[i][l];
				} else {
					res[k][l] = tab[k][l];
				}
			}
		}
		return res;
	}

	/**
	 * Fonction qui enlève des chiffres dans un tableau de tableau d'entiers
	 *
	 * @param matrice
	 *            dans laquelle on souhaite enlever des valeurs
	 * @param nombreFinalCasesVisibles
	 *            nombre de cases à laisser apparentes
	 * @return une matrice identique à celle passée en paramètre, avec
	 *         nombreFinalCasesVisibles cases non nulles
	 */

	private static int[][] enleveChiffres(int[][] matrice, int nombreFinalCasesVisibles) {
		Random random = new Random();
		int compteur = 81;
		int i;
		int j;
		while (compteur > nombreFinalCasesVisibles) {
			i = (int) Math.floor(random.nextInt(9));
			j = (int) Math.floor(random.nextInt(9));
			if (matrice[i][j] != 0) {
				matrice[i][j] = 0;
				--compteur;
			}
		}
		return matrice;
	}
}
