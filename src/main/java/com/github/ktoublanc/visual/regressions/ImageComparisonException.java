package com.github.ktoublanc.visual.regressions;

import com.github.ktoublanc.visual.regressions.diff.ImageDifferencesException;

/**
 * Image comparison exception class used to wrap exception raised within this class
 * Created by kevin on 08/05/2016.
 */
public class ImageComparisonException extends Exception {

    /**
     * Builds an {@link ImageComparisonException} instance initialized with the custom message
     *
     * @param message The custom message
     * @param cause The root exception
     */
    public ImageComparisonException(final String message, final ImageDifferencesException cause) {
        super(message, cause);
    }
}
