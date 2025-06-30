package com.example.pixelcraftgui.model;

import javafx.scene.image.Image;

/**
 * Interface for image converters.
 * Defines the contract that all image converters must implement.
 */
public interface ImageConverter {
    
    /**
     * Converts the input image according to the converter's algorithm.
     * @param inputImage the image to convert
     * @return the converted image
     */
    Image convertImage(Image inputImage);
    
    /**
     * Gets the name of this converter.
     * @return the converter name
     */
    String getName();
} 