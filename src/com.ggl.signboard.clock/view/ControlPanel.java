package com.ggl.signboard.clock.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import com.ggl.signboard.clock.controller.SetDefaultFontListener;
import com.ggl.signboard.clock.model.SignboardClockModel;
import com.ggl.signboard.clock.model.SignboardFont;

public class ControlPanel {

	private JButton submitButton;

	private JPanel panel;

	private SignboardClockFrame frame;

	private SignboardClockModel model;

	public ControlPanel(SignboardClockFrame frame, SignboardClockModel model) {
		this.frame = frame;
		this.model = model;
		createControlPanel();
	}

	private void createControlPanel() {
		Color backgroundColor = Color.BLACK;
		Color foregroundColor = Color.YELLOW;
		
		panel = new JPanel();
		panel.setBackground(backgroundColor);
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		JPanel fontPanel = new JPanel();
		fontPanel.setBackground(backgroundColor);
		fontPanel.setLayout(new BorderLayout());

		JLabel fontLabel = new JLabel("Fonts");
		fontLabel.setForeground(foregroundColor);
		fontLabel.setFont(fontPanel.getFont().deriveFont(Font.BOLD, 18f));
		fontPanel.add(fontLabel, BorderLayout.NORTH);

		JList<SignboardFont> fontList = new JList<SignboardFont>(
				model.getFontList());
		fontList.setBackground(backgroundColor);
		fontList.setForeground(foregroundColor);
		fontList.setSelectedValue(model.getDefaultFont(), true);
		fontList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fontList.setVisibleRowCount(5);

		JScrollPane fontScrollPane = new JScrollPane(fontList);

		fontPanel.add(fontScrollPane, BorderLayout.CENTER);

		panel.add(fontPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(backgroundColor);

		submitButton = new JButton("Submit");
		submitButton.addActionListener(new SetDefaultFontListener(frame,
				model, fontList));
		buttonPanel.add(submitButton);

		panel.add(buttonPanel);
	}

	public JPanel getPanel() {
		return panel;
	}

}
