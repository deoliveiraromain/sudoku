package fr.eisti.ing1.java.sudoku;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe importeur, gérant les dialogues entre les fichiers d'entrée/sortie
 * et l'application
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class Importeur {

	/**
	 * Fonction permettant d'importer une liste de grilles depuis un fichier
	 * passé en paramètre
	 * 
	 * @param nomFichier
	 *            nom du fichier d'entrée
	 * @return une liste de grilles rassemblant l'ensemble des grilles lisibles
	 *         du fichier d'entrée
	 */
	public static ArrayList<Grille> importeGrille(String nomFichier) {

		ArrayList<Grille> tempSet = new ArrayList<Grille>();
		char[][] tabDebut = new char[9][9];
		// int cpt=0;
		try {
			Scanner sc = new Scanner(new File(nomFichier));
			String ligne;
			char tmpValeur;
			int i;
			// Tant qu'il y a une ligne à lire
			while (sc.hasNextLine()) {
				// Compteur de lignes valides
				i = 0;
				// Tant qu'on n'a pas une grille de 9 lignes et qu'on a une
				// ligne suivante
				while (i < 9 && sc.hasNextLine()) {
					ligne = sc.nextLine();
					// On vérifie si la ligne est de bonne dimension et qu'elle
					// contient des chiffres et des espaces uniquement
					boolean ligneBienFormee = true;
					if (ligne.length() >= 17) {
						for (int k = 0; k < 17; ++k) {
							// On vérifie que dans la ligne, les caractères
							// d'index impair sont des chiffres, que les autres
							// sont des espaces.
							if (k % 2 == 0) {
								if (!Character.isDigit(ligne.charAt(k))) {
									ligneBienFormee = false;
									System.err.println("Caractère pas un nombre");
								}
							} else {
								if (!Character.isSpaceChar(ligne.charAt(k))) {
									ligneBienFormee = false;
									System.err.println("Caractère pas un espace");
								}

							}
						}
					} else {
						ligneBienFormee = false;

					}
					if (!ligneBienFormee) {
						// Si une ligne n'est pas exploitable, on jette toute la
						// grille, en passant sur les lignes de la fin de la
						// grille
						for (int h = 9; h > i + 1; --h) {
							ligne = sc.nextLine();
						}
						i = 0;
					} else {
						// Si la ligne est bien formée, on parcourt chacun des
						// caractères de la ligne, et on en prend un sur deux
						// (on prend les chiffres, on laisse les espaces)
						for (int j = 0; j < 17; j += 2) {
							tmpValeur = ligne.charAt(j);
							// On met le résultat dans un tableau
							tabDebut[i][j / 2] = tmpValeur;
						}
						// On incrémente le compteur de lignes correctes
						++i;
					}
				}
				// Si on a un nombre correct de lignes pour une grille
				if (!(i < 9)) {
					int[][] tabRes = new int[9][9];
					// On remplit une grille
					for (int k = 0; k < 9; ++k) {
						for (int j = 0; j < 9; ++j) {
							tmpValeur = tabDebut[k][j];
							tabRes[k][j] = Character.digit(tmpValeur, 10);
						}
					}
					tempSet.add(new Grille(tabRes));
				}
			}

		} catch (FileNotFoundException e) {
			System.err.println("Le fichier d'entrée n'a pu être lu, fermeture du programme");
			System.exit(-1);

		} finally {
		}

		if (tempSet.isEmpty()) {
			System.err.println("Aucune grille valide n'a été trouvée");
		}
		return tempSet;
	}

	/**
	 * Procédure permettant d'exporter une liste de grilles dans un fichier
	 * passé en paramètre
	 * 
	 * @param grilles
	 *            la liste de grilles à exporter
	 * @param nomFichier
	 *            nom du fichier de sortie
	 */

	public static void exporteGrille(ArrayList<Grille> grilles, String nomFichier) {
		try {
			FileWriter fw = new FileWriter(nomFichier);
			BufferedWriter output = new BufferedWriter(fw);
			for (Grille g : grilles) {
				output.write(g.toStringZeros());
				output.flush();
			}
			output.close();

		} catch (IOException e) {
			System.err.println("Le fichier n'a pu être édité, fermeture du programme");
			System.exit(-1);
		} finally {
		}

	}

}
