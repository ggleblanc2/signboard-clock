package com.ggl.signboard.clock.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import com.ggl.signboard.clock.model.SignboardClockModel;
import com.ggl.signboard.clock.model.SignboardFont;
import com.ggl.signboard.clock.view.SignboardClockFrame;

public class SetDefaultFontListener implements ActionListener {
	
	private final JList<SignboardFont> fontList;
	
	private final SignboardClockFrame view;
	
	private final SignboardClockModel model;

	public SetDefaultFontListener(SignboardClockFrame view,
			SignboardClockModel model, JList<SignboardFont> fontList) {
		this.view = view;
		this.model = model;
		this.fontList = fontList;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		model.setDefaultFont(fontList.getSelectedValue());
		view.repaintSignboardPanel();
	}

}
