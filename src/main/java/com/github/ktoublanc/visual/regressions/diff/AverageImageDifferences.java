package com.github.ktoublanc.visual.regressions.diff;

import com.github.ktoublanc.visual.regressions.model.DifferencesMap;
import com.github.ktoublanc.visual.regressions.model.DifferencesResult;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Created by ktoublanc on 06/11/2016.
 */
public class AverageImageDifferences implements ImageDifferences {

	private static final int DEFAULT_RECT_SIZE = 10;
	private final BufferedImage referenceImage;
	private final BufferedImage comparedImage;
	private final int size;
	private final int threshold;

	/**
	 * Builds a {@link ImageDifferences} class instances specifying the compared images and the analysing rectangle size
	 *
	 * @param referenceImage The reference image
	 * @param comparedImage  The compared image
	 * @param size           The analysing rectangle size
	 */
	public AverageImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage, final int threshold, final int size) {
		Objects.requireNonNull(referenceImage, "Need an instantiated reference image parameter to process differences");
		Objects.requireNonNull(comparedImage, "Need an instantiated compared image parameter to process differences");
		if (threshold < 0) {
			throw new IllegalArgumentException("Threshold value must be greater or equal to zero");
		}
		this.referenceImage = referenceImage;
		this.comparedImage = comparedImage;
		this.size = size < 0 ? DEFAULT_RECT_SIZE : size;
		this.threshold = threshold;
	}

	@Override
	public DifferencesResult checkForDifferences() throws ImageDifferencesException {

		int maxImageWidth = Math.max(referenceImage.getWidth(), comparedImage.getWidth());
		int maxImageHeight = Math.max(referenceImage.getHeight(), comparedImage.getHeight());
		int minImageWidth = Math.min(referenceImage.getWidth(), comparedImage.getWidth());
		int minImageHeight = Math.min(referenceImage.getHeight(), comparedImage.getHeight());

		final DifferencesMap differencesMap = new DifferencesMap(maxImageWidth, maxImageHeight);

		for (int x = 0; x < maxImageWidth; x++) {
			for (int y = 0; y < maxImageHeight; y++) {
				if (y >= minImageHeight || x >= minImageWidth) {
					differencesMap.addDifference(x, y, 100d);
				} else {
					double difference = getDifference(minImageWidth, minImageHeight, x, y);
					differencesMap.addDifference(x, y, difference);
				}
			}
		}

		return new DifferencesResult(threshold, differencesMap, referenceImage, comparedImage);
	}

	private double getDifference(final int maxImageWidth, final int maxImageHeight, final int centerX, final int centerY) {
		final int medianSize = size / 2;

		final int minY = Math.max(0, centerY - medianSize);
		final int maxY = Math.min(maxImageHeight - 1, centerY + medianSize);

		final int minX = Math.max(0, centerX - medianSize);
		final int maxX = Math.min(maxImageWidth - 1, centerX + medianSize);

		double distance = 0;
		double pixelCount = 0;
		for (int x = minX; x <= maxX; x += 1) {
			for (int y = minY; y <= maxY; y += 1) {
				distance += distance(x, y);
				pixelCount++;
			}
		}

		return distance / pixelCount / 0xffffff * 100.0;
	}

	private long distance(final int x, final int y) {
		return (referenceImage.getRGB(x, y) - comparedImage.getRGB(x, y)) & 0xffffff;
	}
}
