package com.example.pixelcraftgui.model;

import com.example.pixelcraftgui.model.converters.*;

/**
 * Factory class for creating image converters.
 * Implements the Factory pattern to manage converter creation.
 * Uses the original converter logic from the user's existing code.
 */
public class ConverterFactory {
    
    // Converter name constants
    public static final String GRAYSCALE = "Grayscale";
    public static final String ROTATE_90 = "Rotate 90°";
    public static final String BLUR = "Blur";
    public static final String INVERT = "Invert";
    public static final String MIRROR = "Mirror";
    public static final String SHARPEN = "Sharpen";
    public static final String POSTERIZE = "Posterize";
    public static final String OLD_PHOTO = "Old Photo";
    
    /**
     * Creates a converter based on the specified type.
     * @param type the type of converter to create
     * @return the created converter, or null if type is not recognized
     */
    public static ImageConverter createConverter(String type) {
        switch (type) {
            case GRAYSCALE:
                return new GrayscaleConverter();
            case ROTATE_90:
                return new RotateConverter();
            case BLUR:
                return new BlurConverter();
            case INVERT:
                return new InvertConverter();
            case MIRROR:
                return new MirrorConverter();
            case SHARPEN:
                return new SharpenConverter();
            case POSTERIZE:
                return new PosterizeConverter();
            case OLD_PHOTO:
                return new OldPhotoConverter();
            default:
                return null;
        }
    }
    
    /**
     * Gets all available converter types.
     * @return array of available converter names
     */
    public static String[] getAvailableConverters() {
        return new String[]{
            GRAYSCALE,
            ROTATE_90,
            BLUR,
            INVERT,
            MIRROR,
            SHARPEN,
            POSTERIZE,
            OLD_PHOTO
        };
    }
} 