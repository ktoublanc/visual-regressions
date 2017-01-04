package com.github.ktoublanc.visual.regressions.diff;

import java.awt.image.BufferedImage;
import java.util.List;

import com.github.ktoublanc.visual.regressions.model.Exclusion;

/**
 * Dummy implemetation of image differences interface
 * <p>
 * Created by ktoublanc on 05/11/2016.
 */
public class DummyImageDifferences extends AbstractImageDifferences implements ImageDifferences {


	/**
	 * Builds a {@link DummyImageDifferences} class instances.
	 * @param referenceImage     The reference image (non null)
	 * @param comparedImage      The compared image (non null)
	 * @param threshold          The threshold for this image comparison in percent (0 for no differences allowed at all, 100 for every differences allowed) (non null)
	 * @param exclusions         The excluded part of the image
	 */
	public DummyImageDifferences(final BufferedImage referenceImage, final BufferedImage comparedImage,
	                             final Integer threshold, final List<Exclusion> exclusions) {
		super(referenceImage, comparedImage, threshold, exclusions);
	}

	@Override
	protected double getDifference(final int maxWidth, final int maxHeight, final int x, final int y) {
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
