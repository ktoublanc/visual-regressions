package com.github.ktoublanc.visual.regressions.diff;

import java.awt.image.BufferedImage;

/**
 * Created by ktoublanc on 06/11/2016.
 */
public class AverageImageDifferences extends AbstractImageDifferences implements ImageDifferences {

	private static final int DEFAULT_RECT_SIZE = 10;
	private final int size;

	/**
	 * Builds a {@link ImageDifferences} class instances specifying the compared images and the analysing rectangle size
	 *
	 * @param referenceImage The reference image
	 * @param comparedImage  The compared image
	 * @param size           The analysing rectangle size
	 */
	public AverageImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage, final int threshold, final int size) {
		super(referenceImage, comparedImage, threshold);
		this.size = size < 0 ? DEFAULT_RECT_SIZE : size;
	}

	protected double getDifference(final int maxImageWidth, final int maxImageHeight, final int centerX, final int centerY) {
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
