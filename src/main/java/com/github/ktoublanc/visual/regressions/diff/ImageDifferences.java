package com.github.ktoublanc.visual.regressions.diff;

import com.github.ktoublanc.visual.regressions.model.DifferencesResult;

/**
 * Image difference interface.
 * Defines the {@link #checkForDifferences()} methods for image difference processing.
 * Please note that no {@link java.awt.image.BufferedImage} objects are defined in this interface letting the choice
 * of how many images to process to the implementation.
 * <p>
 * The interface also defines the result object for the process: {@link DifferencesResult}.
 * <p>
 * Created by ktoublanc on 05/11/2016.
 */
public interface ImageDifferences {

	/**
	 * Checks for differences between images.
	 *
	 * @return an initialized {@link DifferencesResult} instance containing the results of the comparison.
	 * @throws ImageDifferencesException on not recoverable exceptions
	 */
	DifferencesResult checkForDifferences();
}
