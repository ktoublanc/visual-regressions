package com.github.ktoublanc.visual.regressions.model;

/**
 * Exclusion model class
 * Created by a536121 on 03/01/2017.
 */
public class Exclusion {

	private int x;
	private int y;
	private int width;
	private int height;

	public Exclusion(final int x, final int y, final int width, final int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public boolean isExcluded(final int x, final int y) {
		return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + height;
	}
}
