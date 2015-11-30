package fr.eisti.ing1.java.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe SecurityListener, permettant d'écouter les clics sur le bouton de
 * changement de mode de saisie
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class SecurityListener implements ActionListener {

	/**
	 * La fenetre de Sudoku sur laquelle le listener est appliqué
	 */

	private SudokuWindow win;

	/**
	 * Fonction permettant de créer une entité de SecurityListener
	 * 
	 * @param win
	 *            Fenetre de Sudoku sur laquelle le listener est appliqué
	 * 
	 */

	public SecurityListener(SudokuWindow win) {
		this.win = win;

	}

	/**
	 * Procédure permettant de réagir à une action sur le bouton de
	 * changement de mode de saisie associé
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		win.changerModeSaisie();

	}

}
