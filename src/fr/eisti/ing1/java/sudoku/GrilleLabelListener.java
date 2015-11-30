package fr.eisti.ing1.java.sudoku;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

/**
 * Classe GrilleLabelListener, permettant d'écouter les clics sur les cases de
 * la grille de Sudoku
 * 
 * @author De Oliveira, Méric, Zullo
 */

public class GrilleLabelListener implements MouseListener {

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

	public GrilleLabelListener(SudokuWindow win) {
		this.win = win;

	}

	/**
	 * Procédure permettant de réagir à un sur une case de la grille
	 * 
	 * @param e
	 *            l'action qui a déclenché l'évènement
	 */

	@Override
	public void mouseClicked(MouseEvent e) {

		win.setLabelGrilleSelectionne((LabelGrille) e.getSource());

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
