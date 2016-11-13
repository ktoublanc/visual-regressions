package com.github.ktoublanc.visual.regressions.diff;

import com.github.ktoublanc.visual.regressions.ImageTools;
import com.github.ktoublanc.visual.regressions.model.DifferencesMap;
import com.github.ktoublanc.visual.regressions.model.DifferencesResult;

import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 * Dummy implemetation of image differences interface
 * <p>
 * Created by ktoublanc on 05/11/2016.
 */
public class DummyImageDifferences implements ImageDifferences {

	/**
	 * The refernce image buffer
	 */
	private final BufferedImage referenceImage;

	/**
	 * The compared image buffer
	 */
	private final BufferedImage comparedImage;

	/**
	 * The threshold percent of this image comparison
	 */
	private final Integer threshold;

	/**
	 * Builds a {@link DummyImageDifferences} class instances.
	 *  @param referenceImage     The reference image (non null)
	 * @param comparedImage      The compared image (non null)
	 * @param threshold          The threshold for this image comparison in percent (0 for no differences allowed at all, 100 for every differences allowed) (non null)
	 */
	public DummyImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage,
	                             final Integer threshold) {
		Objects.requireNonNull(referenceImage, "Need an instantiated reference image parameter to process differences");
		Objects.requireNonNull(comparedImage, "Need an instantiated compared image parameter to process differences");
		Objects.requireNonNull(threshold, "Need an non null and greater or equals to 0 threshold value to process differences");
		if (threshold < 0) {
			throw new IllegalArgumentException("Threshold value must be greater or equal to zero");
		}
		this.referenceImage = referenceImage;
		this.comparedImage = comparedImage;
		this.threshold = threshold;
	}

	@Override
	public DifferencesResult checkForDifferences() throws ImageDifferencesException {

		if (!ImageTools.checkSizes(referenceImage, comparedImage)) {
			throw new ImageDifferencesException("Images dimensions mismatch");
		}

		int referenceImageWidth = referenceImage.getWidth();
		int referenceImageHeight = referenceImage.getHeight();

		final DifferencesMap differencesMap = new DifferencesMap(referenceImageWidth, referenceImageHeight);
		for (int y = 0; y < referenceImageHeight; y++) {
			for (int x = 0; x < referenceImageWidth; x++) {
				int percentage = this.getPercentage(y, x);
				differencesMap.addDifference(x, y, percentage);
			}
		}

		return new DifferencesResult(threshold, differencesMap, referenceImage, comparedImage);
	}

	private int getPercentage(final int y, final int x) {
		int referenceImageRGB = referenceImage.getRGB(x, y);
		int referenceImageR = referenceImageRGB & 0xff0000;
		int referenceImageG = referenceImageRGB & 0xff00;
		int referenceImageB = referenceImageRGB & 0xff;

		int comparedImageRGB = comparedImage.getRGB(x, y);
		int comparedImageR = comparedImageRGB & 0xff0000;
		int comparedImageG = comparedImageRGB & 0xff00;
		int ComparedImageB = comparedImageRGB & 0xff;

		int abs = (Math.abs(referenceImageR - comparedImageR) >> 16)
				+ (Math.abs(referenceImageG - comparedImageG) >> 8)
				+ Math.abs(referenceImageB - ComparedImageB);

		return (int) (abs / 7.65);
	}
}
