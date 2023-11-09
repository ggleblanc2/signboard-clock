package com.ggl.signboard.clock.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import com.ggl.signboard.clock.model.SignboardClockModel;
import com.ggl.signboard.clock.view.SignboardClockFrame;

public class DisplayAllPixelsListener implements ActionListener {
	
	private boolean firstTimeSwitch;
	
	private final SignboardClockFrame view;
	
	private final SignboardClockModel model;

	public DisplayAllPixelsListener(SignboardClockFrame view,
			SignboardClockModel model) {
		this.view = view;
		this.model = model;
		this.firstTimeSwitch = true;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (firstTimeSwitch) {
			model.setAllPixels();
			firstTimeSwitch = false;
		} else {
			model.resetAllPixels();
			Timer timer = (Timer) event.getSource();
			timer.stop();
			view.getClockTimer().start();
		}
		
		view.repaintSignboardPanel();
	}

}
