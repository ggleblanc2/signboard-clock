package com.ggl.signboard.clock.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class SignboardFont {

	private static final boolean DEBUG = false;

	private int fontHeight, fontWidth;

	private Font font;

	public SignboardFont(Font font) {
		this.font = font;

		FontRenderContext frc = new FontRenderContext(null, true, true);
		Rectangle2D r2D = font.getStringBounds("12:58 AM", frc);
		this.fontHeight = (int) Math.round(r2D.getHeight());
		this.fontWidth = (int) Math.round(r2D.getWidth());
		
		if (DEBUG) {
			System.out.println(font.getFamily() + " " + fontHeight + " pixels");
		}
	}

	public boolean[][] getTextPixels(String s) {
		FontRenderContext frc = new FontRenderContext(null, true, true);

		Rectangle2D r2D = font.getStringBounds(s, frc);
		int rWidth = (int) Math.round(r2D.getWidth());
		int rHeight = (int) Math.round(r2D.getHeight());
		int rX = (int) Math.round(r2D.getX());
		int rY = (int) Math.round(r2D.getY());

		if (DEBUG) {
			System.out.print(s);
			System.out.print(", rWidth = " + rWidth);
			System.out.print(", rHeight = " + rHeight);
			System.out.println(", rX = " + rX + ", rY = " + rY);
		}

		BufferedImage bi = generateCharacterImage(rX, -rY, rWidth, rHeight, s);
		int[][] pixels = convertTo2D(bi);

		if (DEBUG) {
			displayPixels(pixels);
		}

		return createTextPixels(pixels);
	}

	private BufferedImage generateCharacterImage(int x, int y, int width,
			int height, String string) {
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.setFont(font);

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.BLACK);
		g.drawString(string, x, y);

		return bi;
	}

	private int[][] convertTo2D(BufferedImage image) {
		final int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer())
				.getData();
		final int width = image.getWidth();
		final int height = image.getHeight();

		int[][] result = new int[height][width];

		int row = 0;
		int col = 0;
		for (int pixel = 0; pixel < pixels.length; pixel++) {
			result[row][col] = pixels[pixel];
			col++;
			if (col == width) {
				col = 0;
				row++;
			}
		}

		return result;
	}

	private void displayPixels(int[][] pixels) {
		for (int i = 0; i < pixels.length; i++) {
			String s = String.format("%03d", (i + 1));
			System.out.print(s + ". ");
			for (int j = 0; j < pixels[i].length; j++) {
				if (pixels[i][j] == -1) {
					System.out.print("  ");
				} else {
					System.out.print("X ");
				}
			}
			System.out.println("");
		}
	}

	private boolean[][] createTextPixels(int[][] pixels) {
		// The int array pixels is in column, row order.
		// We have to flip the array and produce the output
		// in row, column order.
		if (DEBUG) {
			System.out.println(pixels[0].length + "x" + pixels.length);
		}
		boolean[][] textPixels = new boolean[pixels[0].length][pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			for (int j = 0; j < pixels[i].length; j++) {
				if (pixels[i][j] == -1) {
					textPixels[j][i] = false;
				} else {
					textPixels[j][i] = true;
				}
			}
		}

		return textPixels;
	}

	public Font getFont() {
		return font;
	}

	public int getFontHeight() {
		return fontHeight;
	}

	public int getFontWidth() {
		return fontWidth;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(font.getFamily());
		builder.append(", ");
		builder.append(getStyleText());
		builder.append(", ");
		builder.append(font.getSize());
		builder.append(" point");

		return builder.toString();
	}

	private StringBuilder getStyleText() {
		StringBuilder builder = new StringBuilder();
		int style = font.getStyle();
		if (style == Font.PLAIN) {
			builder.append("normal");
		} else if (style == Font.BOLD) {
			builder.append("bold");
		} else if (style == Font.ITALIC) {
			builder.append("italic");
		} else if (style == (Font.BOLD + Font.ITALIC)) {
			builder.append("bold italic");
		} else {
			builder.append("unknown style");
		}
		return builder;
	}

}
