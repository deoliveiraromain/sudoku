package fr.eisti.ing1.java.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe SwitchGrilleListener, permettant d'écouter les clics sur le bouton de
 * changement de grille associé
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class SwitchGrilleListener implements ActionListener {

	/**
	 * La fenetre de Sudoku sur laquelle le listener est appliqué
	 */

	private SudokuWindow win;

	/**
	 * Fonction permettant de créer une entité de SwitchGrilleListener
	 * 
	 * @param win
	 *            Fenetre de Sudoku sur laquelle le listener est appliqué
	 * 
	 */

	public SwitchGrilleListener(SudokuWindow win) {
		this.win = win;

	}

	/**
	 * Procédure permettant de réagir à une action sur le bouton de
	 * changement de grilleassocié
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		win.switchGrille();

	}

}
