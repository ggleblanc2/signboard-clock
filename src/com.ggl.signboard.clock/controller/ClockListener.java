package com.ggl.signboard.clock.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;

import javax.swing.Timer;

import com.ggl.signboard.clock.model.SignboardClockModel;
import com.ggl.signboard.clock.view.SignboardClockFrame;

public class ClockListener implements ActionListener {

	private boolean firstTimeSwitch, shiftOffSwitch;

	private boolean[][] textPixels;

	private int delay;

	private LocalDateTime currentTime;

	private final SignboardClockFrame view;

	private final SignboardClockModel model;

	public ClockListener(SignboardClockFrame view, SignboardClockModel model) {
		this.view = view;
		this.model = model;
		this.currentTime = model.getCurrentTime().minusMinutes(1L);
		this.firstTimeSwitch = true;
		this.shiftOffSwitch = true;
		this.delay = 100;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		model.setCurrentTime();

		if (firstTimeSwitch) {
			firstTimeSwitch = false;
		} else {
			if (shiftOffSwitch && isShiftTime()) {
				shiftTimeOff();
			}
		}

		if (isNextMinute()) {
			this.textPixels = model.getTextPixels();
			Timer timer = new Timer(delay,
					new ShiftTimeInListener(view, model, textPixels));
			timer.start();
			this.currentTime = model.getCurrentTime();
		}
	}

	private void shiftTimeOff() {
		int margin = (model.getSignboardSize().width - textPixels.length)
				/ 2;
		Timer timer = new Timer(delay, new ActionListener() {
			private int shiftLength = textPixels.length + margin;

			@Override
			public void actionPerformed(ActionEvent event) {
				model.shiftAllPixels();
				view.repaintSignboardPanel();
				if (--shiftLength < 0) {
					Timer timer = (Timer) event.getSource();
					timer.stop();
					firstTimeSwitch = true;
					shiftOffSwitch = true;
				}
			}
		});
		timer.start();
		shiftOffSwitch = false;
	}

	private boolean isShiftTime() {
		return model.getCurrentTime().getSecond() > 40;
	}

	private boolean isNextMinute() {
		return model.getCurrentTime().getMinute() != currentTime.getMinute();
	}

}
