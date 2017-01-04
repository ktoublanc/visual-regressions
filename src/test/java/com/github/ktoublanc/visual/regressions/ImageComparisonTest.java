package com.github.ktoublanc.visual.regressions;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.stream.IntStream;

import org.assertj.core.data.Offset;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ImageComparisonTest {

	private final BufferedImage referenceImage = ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"));
	private final BufferedImage referenceImageWithDiffsDummy1px = ImageTools.imageFromPath(Paths.get("src/test/resources/reference-withDifferences-Dummy-1px.png"));
	private final BufferedImage referenceImageWithDiffs3px = ImageTools.imageFromPath(Paths.get("src/test/resources/reference-withDifferences-3px.png"));
	private final BufferedImage comparedImage = ImageTools.imageFromPath(Paths.get("src/test/resources/compared.png"));
	private final BufferedImage comparedImageWithDiffsDummy1px = ImageTools.imageFromPath(Paths.get("src/test/resources/compared-withDifferences-Dummy-1px.png"));
	private final BufferedImage comparedImageWithDiffs3px = ImageTools.imageFromPath(Paths.get("src/test/resources/compared-withDifferences-3px.png"));

	public ImageComparisonTest() throws IOException {
	}

	@Test
	public void compareWithDummyImplementation() throws Exception {
		ImageComparison comparison = new ImageComparisonBuilder(referenceImage, comparedImage)
				.withAnalysingRectangle(1)
				.build();
		final ImageComparison.ComparisonResults results = comparison.compare();
		assertThat(results.isSameImage()).isFalse();
		assertThat(results.getDifferencePercentage()).isCloseTo(2.68, Offset.offset(0.1));
		assertThat(results.getThreshold()).isEqualTo(0);

		final BufferedImage comparedImageWithDifferences = results.getComparedImageWithDifferences();
		assertThat(comparedImageWithDifferences).isNotNull();
		assertThat(comparedImageWithDifferences.getWidth()).isEqualTo(comparedImageWithDiffsDummy1px.getWidth());
		assertThat(comparedImageWithDifferences.getHeight()).isEqualTo(comparedImageWithDiffsDummy1px.getHeight());
		IntStream.range(0, comparedImageWithDifferences.getWidth())
				.forEach(w -> IntStream.range(0, comparedImageWithDifferences.getHeight())
						.forEach(h -> assertThat(comparedImageWithDifferences.getRGB(w, h)).isEqualTo(comparedImageWithDiffsDummy1px.getRGB(w, h))));

		final BufferedImage referenceImageWithDifferences = results.getReferenceImageWithDifferences();
		assertThat(referenceImageWithDifferences).isNotNull();
		assertThat(referenceImageWithDifferences.getWidth()).isEqualTo(referenceImageWithDiffsDummy1px.getWidth());
		assertThat(referenceImageWithDifferences.getHeight()).isEqualTo(referenceImageWithDiffsDummy1px.getHeight());
		IntStream.range(0, referenceImageWithDifferences.getWidth())
				.forEach(w -> IntStream.range(0, referenceImageWithDifferences.getHeight())
						.forEach(h -> assertThat(referenceImageWithDifferences.getRGB(w, h)).isEqualTo(referenceImageWithDiffsDummy1px.getRGB(w, h))));

	}

	@Test
	public void compareWith3pixRectangle() throws Exception {
		ImageComparison comparison = new ImageComparisonBuilder(referenceImage, comparedImage)
				.withAnalysingRectangle(3)
				.build();
		final ImageComparison.ComparisonResults results = comparison.compare();
		assertThat(results.isSameImage()).isFalse();
		assertThat(results.getDifferencePercentage()).isCloseTo(3.13, Offset.offset(0.1));
		assertThat(results.getThreshold()).isEqualTo(0);

		final BufferedImage comparedImageWithDifferences = results.getComparedImageWithDifferences();
		assertThat(comparedImageWithDifferences).isNotNull();
		assertThat(comparedImageWithDifferences.getWidth()).isEqualTo(comparedImageWithDiffs3px.getWidth());
		assertThat(comparedImageWithDifferences.getHeight()).isEqualTo(comparedImageWithDiffs3px.getHeight());
		IntStream.range(0, comparedImageWithDifferences.getWidth())
				.forEach(w -> IntStream.range(0, comparedImageWithDifferences.getHeight())
						.forEach(h -> assertThat(comparedImageWithDifferences.getRGB(w, h)).isEqualTo(comparedImageWithDiffs3px.getRGB(w, h))));

		final BufferedImage referenceImageWithDifferences = results.getReferenceImageWithDifferences();
		assertThat(referenceImageWithDifferences).isNotNull();
		assertThat(referenceImageWithDifferences.getWidth()).isEqualTo(referenceImageWithDiffs3px.getWidth());
		assertThat(referenceImageWithDifferences.getHeight()).isEqualTo(referenceImageWithDiffs3px.getHeight());
		IntStream.range(0, referenceImageWithDifferences.getWidth())
				.forEach(w -> IntStream.range(0, referenceImageWithDifferences.getHeight())
						.forEach(h -> assertThat(referenceImageWithDifferences.getRGB(w, h)).isEqualTo(referenceImageWithDiffs3px.getRGB(w, h))));
	}

	@Test
	public void compareSameImage() throws Exception {
		ImageComparison comparison = new ImageComparisonBuilder(referenceImage, referenceImage)
				.withAnalysingRectangle(3)
				.withThreshold(0)
				.build();
		final ImageComparison.ComparisonResults results = comparison.compare();
		assertThat(results.isSameImage()).isTrue();
		assertThat(results.getDifferencePercentage()).isEqualTo(0);
		assertThat(results.getThreshold()).isEqualTo(0);
		assertThat(results.getComparedImageWithDifferences()).isNull();
		assertThat(results.getReferenceImageWithDifferences()).isNull();
	}

	@Test
	public void compareNoDifferencesDueToThreshold() throws Exception {
		ImageComparison comparison = new ImageComparisonBuilder(referenceImage, comparedImage)
				.withThreshold(5)
				.build();
		final ImageComparison.ComparisonResults results = comparison.compare();
		assertThat(results.isSameImage()).isTrue();
		assertThat(results.getDifferencePercentage()).isCloseTo(2.68, Offset.offset(0.1));
		assertThat(results.getThreshold()).isEqualTo(5);
		assertThat(results.getComparedImageWithDifferences()).isNull();
		assertThat(results.getReferenceImageWithDifferences()).isNull();
	}

}