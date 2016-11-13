package com.github.ktoublanc.visual.regressions.diff;

/**
 * Image Differences Exception subclass.
 * <p>
 * Created by ktoublanc on 05/11/2016.
 */
public class ImageDifferencesException extends Exception {

	/**
	 * Only authorized constructor
	 *
	 * @param message the reason of exception.
	 */
	public ImageDifferencesException(final String message) {
		super(message);
	}
}
