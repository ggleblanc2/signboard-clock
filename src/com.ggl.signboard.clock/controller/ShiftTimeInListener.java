package com.ggl.signboard.clock.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.ggl.signboard.clock.model.SignboardClockModel;
import com.ggl.signboard.clock.view.SignboardClockFrame;

public class ShiftTimeInListener implements ActionListener {
	
	private static final boolean DEBUG = false;

	private int signboardColumn, startColumn, textColumn;
	private int shiftCount, shiftLimit;

	private final boolean[][] textPixels;

	private final SignboardClockFrame view;

	private final SignboardClockModel model;

	public ShiftTimeInListener(SignboardClockFrame view,
			SignboardClockModel model, boolean[][] textPixels) {
		this.view = view;
		this.model = model;
		this.textPixels = textPixels;
		this.signboardColumn = 1;
		this.textColumn = textPixels.length - 1;
		this.shiftCount = 0;
		int margin = (model.getSignboardSize().width - textPixels.length) / 2;
		this.shiftLimit = textPixels.length + margin;
		this.startColumn = textColumn - signboardColumn + 1;
		
		if (DEBUG) {
			System.out.println("Text Column: " + textColumn);
			System.out.println("Shift limit: " + shiftLimit);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (signboardColumn < textPixels.length) {
			model.resetAllPixels();
			copyTextPixels();
			startColumn--;
		} else {
			model.shiftAllPixels();
		}

		signboardColumn++;

		if (++shiftCount > shiftLimit) {
			Timer timer = (Timer) event.getSource();
			timer.stop();
		}

		if (DEBUG) {
			System.out.println("Shift count: " + shiftCount);
		}

		view.repaintSignboardPanel();
	}

	private void copyTextPixels() {
		for (int column = 0; column < signboardColumn; column++) {
			for (int row = 0; row < textPixels[column].length; row++) {
				int currentColumn = startColumn + column;
				if (currentColumn >= 0) {
					model.setSignboardPixel(column, row,
							textPixels[currentColumn][row]);
				}
			}
			
			if (DEBUG) {
				System.out.println("Start Column: " + startColumn);
			}
		}
	}

}
