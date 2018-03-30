package com.github.ktoublanc.visual.regressions.model;

import java.awt.image.BufferedImage;

/**
 * Image difference check results
 * POJO class containing the results of the image comparison.
 */
public class DifferencesResult {

	/**
	 * The threshold used for the image difference processing.
	 */
	private final double threshold;
	/**
	 * The difference percentage
	 */
	private final Double percentage;

	/**
	 * The difference map
	 */
	private final DifferencesMap differencesMap;

	/**
	 * The compared image
	 */
	private final BufferedImage comparedImage;

	/**
	 * The reference image
	 */
	private final BufferedImage referenceImage;

	/**
	 * Public constructor of the POJO class taking two parameters.
	 *  @param threshold      The threshold of the image difference process.
	 * @param differencesMap The result difference map of the process.
	 * @param referenceImage The reference image.
	 * @param comparedImage  The compared image.
	 */
	public DifferencesResult(final double threshold, final DifferencesMap differencesMap,
	                         final BufferedImage referenceImage, final BufferedImage comparedImage) {
		this.threshold = threshold;
		this.differencesMap = differencesMap;
		this.percentage = differencesMap.getDifferencePercentage();
		this.referenceImage = referenceImage;
		this.comparedImage = comparedImage;
	}


	/**
	 * Has the difference process found any mismatch between images ?
	 *
	 * @return true if there were differences, false otherwise.
	 */
	public boolean hasDifferences() {
		return threshold < percentage;
	}


	/**
	 * Threshold getter method
	 *
	 * @return the {@link #threshold}
	 */
	public Double getThreshold() {
		return threshold;
	}

	/**
	 * Percentage getter method
	 *
	 * @return the {@link #percentage}
	 */
	public Double getPercentage() {
		return percentage;
	}

	/**
	 * Difference map getter method
	 *
	 * @return the {@link #differencesMap}
	 */
	public DifferencesMap getDifferencesMap() {
		return differencesMap;
	}

	/**
	 * Compared image getter method
	 *
	 * @return the #comparedImage
	 */
	public BufferedImage getComparedImage() {
		return comparedImage;
	}

	/**
	 * Reference image getter method
	 *
	 * @return the {@link #referenceImage}
	 */
	public BufferedImage getReferenceImage() {
		return referenceImage;
	}
}
