package fr.eisti.ing1.java.sudoku;

import javax.swing.JLabel;

/**
 * Classe représentant un label, elle étend le JLabel de base en rajoutant des
 * coordonnées
 * 
 * @author De Oliveira Méric Zullo
 *
 */
public class LabelGrille extends JLabel {

	/**
	 * un label qui contient en plus des coordonnées afin d'être associé à
	 * la matrice Grille
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Attribut entier représentant la coordonnée en ligne qu'aura le label
	 * qui correspondra à une certaine case dans la matrice
	 */
	private int i;
	/**
	 * Attribut entier représentant la coordonnée en colonne qu'aura le label
	 * qui correspondra à une certaine case dans la matrice
	 * 
	 */
	private int j;

	/**
	 * Constructeur d'un labelGrille
	 * 
	 * @param nom
	 *            le nom que prendra le label
	 * @param i
	 *            cordonnée en ligne du label
	 * @param j
	 *            cordonnée en colonne du label
	 */
	public LabelGrille(String nom, int i, int j) {

		super(nom);

		this.i = i;
		this.j = j;
	}

	/**
	 * Fonction retournant la valeur de la cordonnée en ligne
	 * 
	 * @return i la valeur de la cordonnée en ligne du label
	 */
	public int getI() {
		return i;
	}

	/**
	 * Fonction retournant la valeur de la cordonnée en ligne
	 * 
	 * @return j la valeur de la cordonnée en ligne du label
	 */
	public int getJ() {
		return j;
	}

}
