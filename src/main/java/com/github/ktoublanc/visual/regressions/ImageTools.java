package com.github.ktoublanc.visual.regressions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Image tools utility class
 * Created by kevin on 08/05/2016.
 */
public final class ImageTools {

    /**
     * Utility class hidden construcor
     */
    private ImageTools() {}

    /**
     * Create image from file
     * @param file the image file
     * @return an initialized {@link BufferedImage}
     * @throws IOException if image is not found
     */
    public static BufferedImage imageFromFile(final File file) throws IOException {
        return ImageIO.read(file);
    }

    /**
     * Create image from path
     * @param path the image path
     * @return an initialized {@link BufferedImage}
     * @throws IOException if image is not found
     */
    public static BufferedImage imageFromPath(final Path path) throws IOException {
        Objects.requireNonNull(path, "Path parameter is needed");
        return ImageIO.read(path.toFile());
    }

    /**
     * Check image dimensions
     * @param reference the reference image
     * @param images the compared images
     * @return true if images are the same size
     */
    public static boolean checkSizes(final BufferedImage reference, final BufferedImage... images) {
        Objects.requireNonNull(reference, "Image reference is required to be compared to others");
        Objects.requireNonNull(images, "Images to compare are required");
        int referenceImageWidth = reference.getWidth(null);
        int referenceImageHeight = reference.getHeight(null);

        for (BufferedImage image : images) {
            int comparedImageWidth = image.getWidth(null);
            int comparedImageHeight = image.getHeight(null);

            if ((referenceImageWidth != comparedImageWidth) || (referenceImageHeight != comparedImageHeight)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convert PNG image to byte array
     * @param image the image to convert
     * @return the image in byte array form
     * @throws IOException on stream exception
     */
    public static byte [] pngToByteArray(final BufferedImage image) throws IOException {
        try (final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", byteArrayOutputStream );
            byteArrayOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        }
    }
}
