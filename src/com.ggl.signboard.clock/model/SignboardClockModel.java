package com.ggl.signboard.clock.model;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultListModel;

public class SignboardClockModel {

	private boolean[][] signboardPixels;

	private final Dimension signboardSize;

	private final DateTimeFormatter formatter;

	private final DefaultListModel<SignboardFont> fontList;

	private LocalDateTime currentTime;

	private SignboardFont defaultFont;

	public SignboardClockModel() {
		this.fontList = createFontList();
		this.signboardSize = getSignboardSize();
		this.formatter = DateTimeFormatter.ofPattern("h:mm a");
		this.signboardPixels = 
				new boolean[signboardSize.width][signboardSize.height];
		resetAllPixels();
		setCurrentTime();
	}

	private DefaultListModel<SignboardFont> createFontList() {
		DefaultListModel<SignboardFont> fontList = new DefaultListModel<>();
		int pointSize = 24;
		GraphicsEnvironment g = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		String[] availableFonts = g.getAvailableFontFamilyNames();
		String[] possibleFonts = { "Arial", "Bookman Old Style", "Cambria",
				"Comic Sans MS", "Dialog", "Georgia", "Tahoma",
				"Times New Roman", "Verdana" };

		for (String possibleFont : possibleFonts) {
			for (String availableFont : availableFonts) {
				if (availableFont.equals(possibleFont)) {
					Font font = new Font(possibleFont, Font.BOLD, pointSize);
					SignboardFont sf = new SignboardFont(font);
					if (possibleFont.equals("Dialog")) {
						this.defaultFont = sf;
					}
					fontList.addElement(sf);
					break;
				}
			}
		}

		return fontList;
	}

	public void setCurrentTime() {
		this.currentTime = LocalDateTime.now();
	}

	public LocalDateTime getCurrentTime() {
		return currentTime;
	}

	public DefaultListModel<SignboardFont> getFontList() {
		return fontList;
	}

	public void setDefaultFont(SignboardFont defaultFont) {
		this.defaultFont = defaultFont;
	}

	public SignboardFont getDefaultFont() {
		return defaultFont;
	}

	public Dimension getSignboardSize() {
		return getMaxStringSize();
	}

	private Dimension getMaxStringSize() {
		int maxHeight = 0;
		int maxWidth = 0;
		for (int i = 0; i < fontList.getSize(); i++) {
			SignboardFont font = fontList.get(i);
			int height = font.getFontHeight();
			maxHeight = Math.max(height, maxHeight);
			int width = font.getFontWidth();
			maxWidth = Math.max(width, maxWidth);
		}
		return new Dimension(maxWidth, maxHeight);
	}

	public void setSignboardPixel(int column, int row, boolean b) {
		this.signboardPixels[column][row] = b;
	}

	public boolean getSignboardPixel(int column, int row) {
		return signboardPixels[column][row];
	}

	public boolean[][] getTextPixels() {
		return defaultFont.getTextPixels(getCurrentDisplayTime());
	}

	private String getCurrentDisplayTime() {
		return currentTime.format(formatter);
	}

	public void resetAllPixels() {
		for (int i = 0; i < signboardPixels.length; i++) {
			for (int j = 0; j < signboardPixels[i].length; j++) {
				signboardPixels[i][j] = false;
			}
		}
	}

	public void setAllPixels() {
		for (int i = 0; i < signboardPixels.length; i++) {
			for (int j = 0; j < signboardPixels[i].length; j++) {
				signboardPixels[i][j] = true;
			}
		}
	}

	public void shiftAllPixels() {
		for (int i = signboardPixels.length - 1; i > 0; i--) {
			for (int j = 0; j < signboardPixels[i].length; j++) {
				signboardPixels[i][j] = signboardPixels[i - 1][j];
			}
		}

		for (int j = 0; j < signboardPixels[0].length; j++) {
			signboardPixels[0][j] = false;
		}
	}

}
