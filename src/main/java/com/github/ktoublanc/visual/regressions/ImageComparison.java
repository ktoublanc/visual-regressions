package com.github.ktoublanc.visual.regressions;

import com.github.ktoublanc.visual.regressions.diff.ImageDifferences;
import com.github.ktoublanc.visual.regressions.diff.ImageDifferencesException;
import com.github.ktoublanc.visual.regressions.model.DifferencesMap;
import com.github.ktoublanc.visual.regressions.model.DifferencesResult;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Image Comparision interface definition
 * <p>
 * Provide an interface for image comparison implementation class defining the expected behavior.
 * <p>
 * Created by kevin on 30/10/2016.
 */
public class ImageComparison {

	/**
	 * RED transparent color
	 */
	private static final Color RED_TRANSPARENT_COLOR = new Color(1f, 0f, 0f, .5f);
	private final ImageDifferences imageDifferences;

	ImageComparison(final ImageComparisonBuilder builder) {
		this.imageDifferences = builder.imageDifferences;
	}

	/**
	 * Compare images.
	 *
	 * @return a initialized {@link ComparisonResults} instance representing the results of the image comparison.
	 * @throws ImageComparisonException on non recoverable comparison exceptions
	 */
	public ComparisonResults compare() throws ImageComparisonException {

		final DifferencesResult imageDifferencesResult;
		try {
			imageDifferencesResult = imageDifferences.checkForDifferences();
		} catch (ImageDifferencesException e) {
			throw new ImageComparisonException("Unable to process image differences", e);
		}

		return new ComparisonResults(imageDifferencesResult);
	}

	public class ComparisonResults {
		private final boolean sameImage;
		private final BufferedImage referenceImageWithDifferences;
		private final BufferedImage comparedImageWithDifferences;
		private final Double differencePercentage;
		private final Integer threshold;

		ComparisonResults(final DifferencesResult result) {
			this.sameImage = !result.hasDifferences();
			this.differencePercentage = result.getPercentage();
			this.threshold = result.getThreshold();
			if (result.hasDifferences()) {
				this.referenceImageWithDifferences = this.highlightedDifferences(result.getReferenceImage(), result.getDifferencesMap());
				this.comparedImageWithDifferences = this.highlightedDifferences(result.getComparedImage(), result.getDifferencesMap());
			} else {
				this.referenceImageWithDifferences = null;
				this.comparedImageWithDifferences = null;
			}
		}

		private BufferedImage highlightedDifferences(final BufferedImage image, final DifferencesMap differencesMap) {
			final BufferedImage scaledImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

			// Writing base image
			final Graphics2D g2d = scaledImage.createGraphics();
			g2d.drawImage(image, 0, 0, null);

			differencesMap.differenceStream().forEach(diff -> drawRect(scaledImage, diff));

			return scaledImage;
		}

		private void drawRect(final BufferedImage image, final DifferencesMap.Difference diff) {
			// Writing base image
			final Graphics2D g2d = image.createGraphics();

			// Set color to red with 50% of transparency
			g2d.setColor(RED_TRANSPARENT_COLOR);

			g2d.fillRect(diff.getX(), diff.getY(), 1, 1);
		}

		public boolean isSameImage() {
			return sameImage;
		}

		public BufferedImage getReferenceImageWithDifferences() {
			return referenceImageWithDifferences;
		}

		public BufferedImage getComparedImageWithDifferences() {
			return comparedImageWithDifferences;
		}

		public Double getDifferencePercentage() {
			return differencePercentage;
		}

		public Integer getThreshold() {
			return threshold;
		}
	}
}
