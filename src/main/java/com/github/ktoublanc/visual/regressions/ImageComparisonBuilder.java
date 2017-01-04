package com.github.ktoublanc.visual.regressions;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.github.ktoublanc.visual.regressions.diff.AverageImageDifferences;
import com.github.ktoublanc.visual.regressions.diff.DummyImageDifferences;
import com.github.ktoublanc.visual.regressions.diff.ImageDifferences;
import com.github.ktoublanc.visual.regressions.model.Exclusion;

/**
 * {@link ImageComparison} builder class.
 * Created by ktoublanc on 11/11/2016.
 */
public class ImageComparisonBuilder {

	/**
	 * Algorithm for image difference processing
	 */
	ImageDifferences imageDifferences;

	/**
	 * The reference image
	 */
	final BufferedImage referenceImage;

	/**
	 * The compared imaged
	 */
	final BufferedImage comparedImage;

	/**
	 * Size of the analysing rectangle
	 */
	int analysingRectangle = 1;

	/**
	 * The difference percent threshold
	 */
	int threshold = 0;

	/**
	 * Exclusion list for image difference processing
	 */
	final List<Exclusion> exclusions = new ArrayList<>();

	/**
	 * Default constructor
	 * @param referenceImage The {@link #referenceImage}
	 * @param comparedImage The {@link #comparedImage}
	 */
	public ImageComparisonBuilder(final BufferedImage referenceImage, final BufferedImage comparedImage) {
		this.referenceImage = referenceImage;
		this.comparedImage = comparedImage;
	}

	public ImageComparisonBuilder withThreshold(final int threshold) {
		this.threshold = threshold;
		return this;
	}

	public ImageComparisonBuilder withAnalysingRectangle(final int analysingRectangle) {
		this.analysingRectangle = analysingRectangle;
		return this;
	}

	public ImageComparisonBuilder withExclusions(final List<Exclusion> exclusions) {
		this.exclusions.addAll(exclusions);
		return this;
	}

	public ImageComparison build() {
		if (analysingRectangle == 1) {
			this.imageDifferences = new DummyImageDifferences(referenceImage, comparedImage, threshold, exclusions);
		} else {
			this.imageDifferences = new AverageImageDifferences(referenceImage, comparedImage, threshold, analysingRectangle, exclusions);
		}
		return new ImageComparison(this);
	}
}
