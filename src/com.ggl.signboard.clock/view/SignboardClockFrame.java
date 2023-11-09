package com.ggl.signboard.clock.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

import com.ggl.signboard.clock.controller.ClockListener;
import com.ggl.signboard.clock.controller.DisplayAllPixelsListener;
import com.ggl.signboard.clock.model.SignboardClockModel;

public class SignboardClockFrame {

	private final ControlPanel controlPanel;

	private final JFrame frame;

//	private final SignboardClockModel model;

	private final SignboardPanel signboardPanel;
	
	private final Timer clockTimer;

	public SignboardClockFrame(SignboardClockModel model) {
//		this.model = model;
		this.signboardPanel = new SignboardPanel(model);
		this.controlPanel = new ControlPanel(this, model);
		this.frame = createAndShowGUI();
		
		Timer timer = new Timer(3_000,
				new DisplayAllPixelsListener(this, model));
		timer.setInitialDelay(2_000);
		timer.start();
		
		this.clockTimer = new Timer(5_000, new ClockListener(this, model));
		clockTimer.setInitialDelay(1_000);
	}

	private JFrame createAndShowGUI() {
		JFrame frame = new JFrame("Signboard Clock");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				clockTimer.stop();
				frame.dispose();
				System.exit(0);
			}
		});

		frame.add(signboardPanel, BorderLayout.CENTER);
		frame.add(controlPanel.getPanel(), BorderLayout.SOUTH);

		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);

		return frame;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void repaintSignboardPanel() {
		signboardPanel.repaint();
	}

	public Timer getClockTimer() {
		return clockTimer;
	}

}
