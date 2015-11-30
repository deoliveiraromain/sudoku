package fr.eisti.ing1.java.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe DeleteListener, permettant d'écouter les clics sur le bouton delete
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class DeleteListener implements ActionListener {

	/**
	 * La fenetre de Sudoku sur laquelle le listener est appliqué
	 */

	private SudokuWindow win;

	/**
	 * Fonction permettant de créer une entité de DeleteListener
	 * 
	 * @param win
	 *            Fenetre de Sudoku sur laquelle le listener est appliqué
	 *
	 */

	public DeleteListener(SudokuWindow win) {
		this.win = win;

	}

	/**
	 * Procédure permettant de réagir à une action sur le bouton delete
	 * associé
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		win.deleteCaseSelectionnee();

	}

}
