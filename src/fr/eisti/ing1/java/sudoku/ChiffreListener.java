package fr.eisti.ing1.java.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Classe ChiffreListener, permettant d'écouter les clics sur les boutons
 * chiffres
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class ChiffreListener implements ActionListener {

	/**
	 * La fenetre de Sudoku sur laquelle le listener est appliqué
	 */

	private SudokuWindow win;

	/**
	 * Fonction permettant de créer une entité de ChiffreListener
	 * 
	 * @param win
	 *            Fenetre de Sudoku sur laquelle le listener est appliqué
	 * 
	 */

	public ChiffreListener(SudokuWindow win) {

		this.win = win;
	}

	/**
	 * Procédure permettant de réagir à une action sur un bouton associé
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		// appelle la méthode de SudokuWindow qui va tester le chiffre cliqué

		int chiffre;

		chiffre = Integer.parseInt(((JButton) e.getSource()).getText());

		win.ajouterChiffre(chiffre);

	}

}
