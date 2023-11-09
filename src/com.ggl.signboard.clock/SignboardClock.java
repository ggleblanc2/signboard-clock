package com.ggl.signboard.clock;

import javax.swing.SwingUtilities;

import com.ggl.signboard.clock.model.SignboardClockModel;
import com.ggl.signboard.clock.view.SignboardClockFrame;

public class SignboardClock implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new SignboardClock());
	}

	@Override
	public void run() {
		new SignboardClockFrame(new SignboardClockModel());
	}

}
