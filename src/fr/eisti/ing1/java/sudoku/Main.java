package fr.eisti.ing1.java.sudoku;

import java.util.ArrayList;

/**
 * Classe principale permettant l'éxécution du programme
 * 
 * @author De Oliveira, Méric, Zullo
 * 
 *
 *
 */
public class Main {

	/**
	 * @param args
	 *            les arguments de la ligne de commande, varient suivant
	 *            l'option utilisée
	 * 
	 * 
	 */
	public static void main(String[] args) {

		if (args.length == 0) {
			aide();
		} else {
			switch (args[0]) {
			// mode graphique
			case "-a":
				interfaceGraphique();
				break;
			// résolution
			case "-r":
				if (args.length < 3) {
					aide();
				}
				resolutionGrilles(args[1], args[2]);
				break;
			// résolution avec le détail pour chaque grille (temps et état)
			case "-t":
				if (args.length < 3) {
					aide();
				}
				resolutionGrillesDetail(args[1], args[2]);
				break;
			// génération
			case "-g":
				if (args.length < 4) {
					aide();
				}
				generationGrilles(args[3], Integer.valueOf(args[1]), Integer.valueOf(args[2]));
				break;
			default:
				aide();
			}
		}
	}

	/**
	 * Procédure initiant une instance de l'interface graphique
	 * 
	 */
	private static void interfaceGraphique() {
		SudokuWindow win = new SudokuWindow();
	}

	/**
	 * procédure permettant la génération de grilles valides
	 * 
	 * @param nomFichierSortie
	 *            nom du fichier dans lequel seront enregistrés les grilles
	 * @param nombreGrilles
	 *            nombres de grilles à générer
	 * @param nbCasesVisibles
	 *            nombres de cases qui seront visibles à l'utilisateur lors de
	 *            la résolution
	 */
	private static void generationGrilles(String nomFichierSortie, int nombreGrilles, int nbCasesVisibles) {
		System.out.println("Génération de " + nombreGrilles + " grilles dans le fichier '" + nomFichierSortie + ".");

		ArrayList<Grille> listeGrilles = Generateur.genereListeGrille(nbCasesVisibles, nombreGrilles);
		Importeur.exporteGrille(listeGrilles, nomFichierSortie);

	}

	/**
	 * Procédure permettant la résolution automatique de grilles à partir
	 * d'un fichier
	 * 
	 * @param nomFichierEntree
	 *            le nom du fichier où il faut aller chercher les grilles à
	 *            résoudre
	 * @param nomFichierSortie
	 *            le nom du fichier dans lesquelles seront enregistrées les
	 *            grilles résolues
	 */
	private static void resolutionGrilles(String nomFichierEntree, String nomFichierSortie) {

		// on charge la liste
		ArrayList<Grille> listeGrilles = Importeur.importeGrille(nomFichierEntree);
		// pour chaque grille de la liste:
		for (Grille grilleCourante : listeGrilles) {
			// on la résout
			grilleCourante.resout();
		}
		// ensuite, on enregistre la liste
		Importeur.exporteGrille(listeGrilles, nomFichierSortie);
	}

	/**
	 * Procédure permettant la résolution Détaillée de grilles
	 * 
	 * @param nomFichierEntree
	 *            nom du fichier où les grilles à résoudre sont contenues
	 * @param nomFichierSortie
	 *            nom du fichier où les solutions seront stockées
	 */
	private static void resolutionGrillesDetail(String nomFichierEntree, String nomFichierSortie) {

		ArrayList<Grille> listeGrilles = Importeur.importeGrille(nomFichierEntree);
		// les deux couples de double qui vont stocker les statuts et les temps.
		double[] stock = { 0, 0 };
		double[] total = { 0, 0 };
		// pour chaque grille :
		for (Grille grilleCourante : listeGrilles) {
			// on la résout
			stock = grilleCourante.resoutTest();
			total[0] += stock[0];
			total[1] += stock[1];
		}
		System.out.println(total[0] + " grilles résolues en " + total[1] + " ms.");

		// on les enregistre
		Importeur.exporteGrille(listeGrilles, nomFichierSortie);
	}

	/**
	 * Procédure permettant l'affichage de l'aide au cas où l'utilisateur
	 * s'est trompé dans les options d'éxécution
	 * 
	 */
	private static void aide() {
		String help = "Usages :\n" + "Jeu a la souris          : java -jar Sudoku.jar -a \n"
				+ "Résolution automatique   : java -jar Sudoku.jar -r fileInput fileOutput\n"
				+ "Génération de grilles    : java -jar Sudoku.jar -g nombreGrilles nombresCasesVisibles fileOutput\n";
		System.out.println(help);
		System.exit(-1);
	}
}
