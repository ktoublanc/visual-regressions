package com.github.ktoublanc.visual.regressions;

import com.github.ktoublanc.visual.regressions.diff.AverageImageDifferences;
import com.github.ktoublanc.visual.regressions.diff.DummyImageDifferences;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.awt.image.BufferedImage;

/**
 * Created by ktoublanc on 12/11/2016.
 */
public class ImageComparisonBuilderTest {

	@Test
	public void withThreshold() throws Exception {
		final ImageComparisonBuilder builder = new ImageComparisonBuilder(null, null).withThreshold(12);
		Assertions.assertThat(builder.threshold).isEqualTo(12);
	}

	@Test
	public void withAnalysingRectangle() throws Exception {
		final ImageComparisonBuilder builder = new ImageComparisonBuilder(null, null).withAnalysingRectangle(10);
		Assertions.assertThat(builder.analysingRectangle).isEqualTo(10);
	}

	@Test
	public void buildWithAverageComparison() throws Exception {
		final BufferedImage referenceImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		final BufferedImage comparedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		final ImageComparisonBuilder builder = new ImageComparisonBuilder(referenceImage, comparedImage).withAnalysingRectangle(2);
		Assertions.assertThat(builder.build()).isNotNull();
		Assertions.assertThat(builder.threshold).isEqualTo(0);
		Assertions.assertThat(builder.analysingRectangle).isEqualTo(2);
		Assertions.assertThat(builder.referenceImage).isEqualTo(referenceImage);
		Assertions.assertThat(builder.comparedImage).isEqualTo(comparedImage);
		Assertions.assertThat(builder.imageDifferences).isInstanceOf(AverageImageDifferences.class);
	}

	@Test(expected = NullPointerException.class)
	public void buildWithAverageComparison_NPE() throws Exception {
		new ImageComparisonBuilder(null, null).build();
	}

	@Test
	public void buildWithDummyComparison() throws Exception {
		final BufferedImage referenceImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		final BufferedImage comparedImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_RGB);
		final ImageComparisonBuilder builder = new ImageComparisonBuilder(referenceImage, comparedImage);
		Assertions.assertThat(builder.build()).isNotNull();
		Assertions.assertThat(builder.referenceImage).isEqualTo(referenceImage);
		Assertions.assertThat(builder.comparedImage).isEqualTo(comparedImage);
		Assertions.assertThat(builder.imageDifferences).isInstanceOf(DummyImageDifferences.class);
	}

}