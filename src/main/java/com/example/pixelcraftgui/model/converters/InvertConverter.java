package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Invert converter implementation using the original Invert.java logic.
 * Inverts the color of the specified image by subtracting each of the RGB values
 * from 255 and writing the result to the corresponding pixel in the output image.
 */
public class InvertConverter implements ImageConverter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = reader.getArgb(x, y);
                ARGB argb = new ARGB(rgb);
                
                /*
                 * Invert each color component by subtracting from 255 (max 8-bit value)
                 * - 255 is maximum value for RGB color channels
                 * - Subtraction inverts the color (255 - current value)
                 */
                int newR = 255 - argb.red;
                int newG = 255 - argb.green;
                int newB = 255 - argb.blue;
                ARGB inverted = new ARGB(newR, newG, newB, 255);
                writer.setArgb(x, y, inverted.toInt());
            }
        }
        return outputImage;
    }

    @Override
    public String getName() {
        return "Invert";
    }
} 