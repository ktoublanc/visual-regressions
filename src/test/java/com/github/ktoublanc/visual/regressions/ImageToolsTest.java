package com.github.ktoublanc.visual.regressions;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ktoublanc on 05/11/2016.
 */
public class ImageToolsTest {

	@Test
	public void imageFromFile() throws Exception {
		assertThat(ImageTools.imageFromFile(Paths.get("src/test/resources/reference.png").toFile())).isNotNull();
	}

	@Test
	public void imageFromPath() throws Exception {
		assertThat(ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"))).isNotNull();
	}

	@Test(expected = NullPointerException.class)
	public void imageFromPath_NullPath() throws Exception {
		ImageTools.imageFromPath(null);
	}

	@Test
	public void checkSizes_ImagesAreTheSameSize() throws Exception {
		assertThat(ImageTools.checkSizes(
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR_PRE),
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR)
		));
	}

	@Test
	public void checkSizes_ImagesAreNotTheSameSize() throws Exception {
		assertThat(ImageTools.checkSizes(
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(20, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR_PRE),
				new BufferedImage(10, 10, BufferedImage.TYPE_3BYTE_BGR),
				new BufferedImage(10, 10, BufferedImage.TYPE_4BYTE_ABGR)
		));
	}

	@Test
	public void pngToByteArray() throws Exception {
		final BufferedImage image = ImageTools.imageFromPath(Paths.get("src/test/resources/reference.png"));
		assertThat(ImageTools.pngToByteArray(image)).isNotNull();
	}

	@Test
	public void constructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		final Constructor<?>[] constructors = ImageTools.class.getDeclaredConstructors();
		Assertions.assertThat(constructors.length).isEqualTo(1);
		final Constructor<?> constructor = constructors[0];
		constructor.setAccessible(true);
		ImageTools imageTools = (ImageTools) constructor.newInstance((Object[]) null);
		Assertions.assertThat(imageTools).isNotNull();
	}

}