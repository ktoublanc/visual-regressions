package com.github.ktoublanc.visual.regressions.diff;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.junit.Test;

import com.github.ktoublanc.visual.regressions.ImageTools;
import com.github.ktoublanc.visual.regressions.model.DifferencesResult;
import com.github.ktoublanc.visual.regressions.model.Exclusion;

/**
 * Created by ktoublanc on 06/11/2016.
 */
public class DummyImageDifferencesTest {

	private final BufferedImage referenceImage = ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"));

	private final BufferedImage alteredReferenceImage = this.alterReferenceImage();

	public DummyImageDifferencesTest() throws IOException {}

	private BufferedImage alterReferenceImage() throws IOException {
		final BufferedImage alteredReferenceImage = ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"));
		for (int x = 0; x <= alteredReferenceImage.getWidth() / 2; x++) {
			for (int y = 0; y <= alteredReferenceImage.getHeight() / 2; y++) {
				alteredReferenceImage.setRGB(x, y, 0);
			}
		}
		return alteredReferenceImage;
	}

	@Test
	public void checkForDifferences_withSameImage() throws Exception {
		final DummyImageDifferences diff = new DummyImageDifferences(referenceImage, referenceImage, 0, Collections.emptyList());
		final DifferencesResult results = diff.checkForDifferences();
		Assertions.assertThat(results.hasDifferences()).isFalse();
		Assertions.assertThat(results.getPercentage()).isEqualTo(0);
		Assertions.assertThat(results.getThreshold()).isEqualTo(0);
	}

	@Test
	public void checkForDifferences_withTwoDifferentSizeImage() throws Exception {
		final DummyImageDifferences diff = new DummyImageDifferences(referenceImage, new BufferedImage(12, 12, BufferedImage.TYPE_INT_RGB), 0, Collections.emptyList());
		final DifferencesResult results = diff.checkForDifferences();
		Assertions.assertThat(results.hasDifferences()).isTrue();
		Assertions.assertThat(results.getPercentage()).isEqualTo(100.0);
		Assertions.assertThat(results.getThreshold()).isEqualTo(0);
	}

	@Test
	public void checkForDifferences_withSameImageAltered() throws Exception {
		final DummyImageDifferences diff = new DummyImageDifferences(referenceImage, alteredReferenceImage, 0, Collections.emptyList());
		final DifferencesResult results = diff.checkForDifferences();
		Assertions.assertThat(results.hasDifferences()).isTrue();
		Assertions.assertThat(results.getPercentage()).isCloseTo(25, Offset.offset(0.2));
		Assertions.assertThat(results.getThreshold()).isEqualTo(0);
	}

	@Test
	public void checkForDifferences_withSameImageAltered_NoDifferences() throws Exception {
		final DummyImageDifferences diff = new DummyImageDifferences(referenceImage, alteredReferenceImage, 26, Collections.emptyList());
		final DifferencesResult results = diff.checkForDifferences();
		Assertions.assertThat(results.hasDifferences()).isFalse();
		Assertions.assertThat(results.getPercentage()).isCloseTo(25, Offset.offset(0.2));
		Assertions.assertThat(results.getThreshold()).isEqualTo(26);
	}

	@Test
	public void checkForDifferences_withSameImageAltered_NoDifferences_DueToExclusions() throws Exception {
		final DummyImageDifferences diff = new DummyImageDifferences(referenceImage, alteredReferenceImage, 0,
				Collections.singletonList(new Exclusion(0, 0, referenceImage.getWidth() / 2, referenceImage.getHeight() / 2)));
		final DifferencesResult results = diff.checkForDifferences();
		Assertions.assertThat(results.hasDifferences()).isFalse();
		Assertions.assertThat(results.getPercentage()).isCloseTo(0, Offset.offset(0.1));
		Assertions.assertThat(results.getThreshold()).isEqualTo(0);
	}

	@Test(expected = NullPointerException.class)
	public void create_withNullReferenceImage() {
		new DummyImageDifferences(null, alteredReferenceImage, 0, Collections.emptyList());
	}

	@Test(expected = NullPointerException.class)
	public void create_withNullAlteredImage() {
		new DummyImageDifferences(referenceImage, null, 0, Collections.emptyList());
	}

	@Test(expected = IllegalArgumentException.class)
	public void create_withNegativeThreshold() {
		new DummyImageDifferences(referenceImage, alteredReferenceImage, -1, Collections.emptyList());
	}
}