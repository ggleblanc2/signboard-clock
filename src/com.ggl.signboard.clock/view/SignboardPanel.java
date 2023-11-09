package com.ggl.signboard.clock.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.ggl.signboard.clock.model.SignboardClockModel;

public class SignboardPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int pixelWidth = 4;
	private static final int gapWidth = 2;
	private static final int totalWidth = pixelWidth + gapWidth;
	private static final int yStart = gapWidth + totalWidth + totalWidth;

	private SignboardClockModel model;

	public SignboardPanel(SignboardClockModel model) {
		this.model = model;

		Dimension d = model.getSignboardSize();
		int width = d.width * totalWidth + yStart + yStart;
		int height = d.height * totalWidth + yStart + yStart;
		setPreferredSize(new Dimension(width, height));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = yStart;
		int y = yStart;

		Dimension d = model.getSignboardSize();
		for (int column = 0; column < d.width; column++) {
			for (int row = 0; row < d.height; row++) {
				if (model.getSignboardPixel(column, row)) {
					g.setColor(Color.YELLOW);
				} else {
					g.setColor(Color.BLACK);
				}

				g.fillRect(x, y, pixelWidth, pixelWidth);
				y += totalWidth;
			}
			y = yStart;
			x += totalWidth;
		}
	}

}
