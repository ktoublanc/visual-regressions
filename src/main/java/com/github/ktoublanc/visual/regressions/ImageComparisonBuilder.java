package com.github.ktoublanc.visual.regressions;

import com.github.ktoublanc.visual.regressions.diff.AverageImageDifferences;
import com.github.ktoublanc.visual.regressions.diff.DummyImageDifferences;
import com.github.ktoublanc.visual.regressions.diff.ImageDifferences;

import java.awt.image.BufferedImage;

/**
 * Created by ktoublanc on 11/11/2016.
 */
public class ImageComparisonBuilder {
	ImageDifferences imageDifferences;
	final BufferedImage referenceImage;
	final BufferedImage comparedImage;
	int analysingRectangle = 1;
	int threshold = 0;

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

	public ImageComparison build() {
		if (analysingRectangle == 1) {
			this.imageDifferences = new DummyImageDifferences(referenceImage, comparedImage, threshold);
		} else {
			this.imageDifferences = new AverageImageDifferences(referenceImage, comparedImage, threshold, analysingRectangle);
		}
		return new ImageComparison(this);
	}
}
