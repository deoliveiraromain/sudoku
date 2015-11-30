package fr.eisti.ing1.java.sudoku;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Classe ConfirmationListener, permettant d'écouter les clics sur la fenetre
 * de confirmation de sortie de l'application
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class ConfirmationListener implements WindowListener {

	/**
	 * La fenetre de Sudoku sur laquelle le listener est appliqué
	 */

	private SudokuWindow win;

	/**
	 * Fonction permettant de créer une entité de ConfirmationListener
	 * 
	 * @param win
	 *            Fenetre de Sudoku sur laquelle le listener est appliqué
	 * 
	 */

	public ConfirmationListener(SudokuWindow win) {
		this.win = win;
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	/**
	 * Procédure permettant de réagir à une action sur le bouton associé
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */

	@Override
	public void windowClosing(WindowEvent e) {

		this.win.confirmation();

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

}
