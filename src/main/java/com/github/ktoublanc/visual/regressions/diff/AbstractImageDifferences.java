package com.github.ktoublanc.visual.regressions.diff;

import com.github.ktoublanc.visual.regressions.model.DifferencesMap;
import com.github.ktoublanc.visual.regressions.model.DifferencesResult;
import com.github.ktoublanc.visual.regressions.model.Exclusion;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;

/**
 * Created by ktoublanc on 13/11/2016.
 */
public abstract class AbstractImageDifferences implements ImageDifferences {

	/**
	 * The refernce image buffer
	 */
	protected final BufferedImage referenceImage;

	/**
	 * The compared image buffer
	 */
	protected final BufferedImage comparedImage;

	/**
	 * The threshold percent of this image comparison
	 */
	protected final double threshold;
	private final List<Exclusion> exclusions;

	/**
	 * Builds a {@link ImageDifferences} class instances specifying the compared images and the analysing rectangle size
	 *  @param referenceImage The reference image
	 * @param comparedImage  The compared image
	 * @param threshold      The threshold
	 * @param exclusions     The excluded part of the image
	 */
	public AbstractImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage, final double threshold, final List<Exclusion> exclusions) {
		Objects.requireNonNull(referenceImage, "Need an instantiated reference image parameter to process differences");
		Objects.requireNonNull(comparedImage, "Need an instantiated compared image parameter to process differences");
		if (threshold < 0) {
			throw new IllegalArgumentException("Threshold value must be greater or equal to zero");
		}
		this.referenceImage = referenceImage;
		this.comparedImage = comparedImage;
		this.threshold = threshold;
		this.exclusions = exclusions;
	}

	@Override
	public DifferencesResult checkForDifferences() {

		final int maxImageWidth = Math.max(referenceImage.getWidth(), comparedImage.getWidth());
		final int maxImageHeight = Math.max(referenceImage.getHeight(), comparedImage.getHeight());
		final int minImageWidth = Math.min(referenceImage.getWidth(), comparedImage.getWidth());
		final int minImageHeight = Math.min(referenceImage.getHeight(), comparedImage.getHeight());

		final DifferencesMap differencesMap = new DifferencesMap(maxImageWidth, maxImageHeight);

		for (int x = 0; x < maxImageWidth; x++) {
			for (int y = 0; y < maxImageHeight; y++) {

				if (this.checkForExclusions(x, y)) {
					continue;
				}

				if (y >= minImageHeight || x >= minImageWidth) {
					differencesMap.addDifference(x, y, 100d);
				} else {
					final double difference = getDifference(minImageWidth, minImageHeight, x, y);
					differencesMap.addDifference(x, y, difference);
				}
			}
		}

		return new DifferencesResult(threshold, differencesMap, referenceImage, comparedImage);
	}

	protected boolean checkForExclusions(final int x, final int y) {
		return exclusions.stream().anyMatch(exclusion -> exclusion.isExcluded(x, y));
	}

	protected abstract double getDifference(final int maxWidth, final int maxHeight, final int x, final int y);
}
