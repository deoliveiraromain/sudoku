package fr.eisti.ing1.java.sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe ResolveListener, permettant d'écouter les clics sur le bouton
 * resoudre
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class ResolveListener implements ActionListener {

	/**
	 * La fenetre de Sudoku sur laquelle le listener est appliqué
	 */

	private SudokuWindow win;

	/**
	 * Fonction permettant de créer une entité de ResolveListener
	 * 
	 * @param win
	 *            Fenetre de Sudoku sur laquelle le listener est appliqué
	 * 
	 */

	public ResolveListener(SudokuWindow win) {
		this.win = win;
	}

	/**
	 * Procédure permettant de réagir à une action sur le bouton resoudre
	 * associé
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */

	@Override
	public void actionPerformed(ActionEvent e) {

		win.resolve();

	}

}
