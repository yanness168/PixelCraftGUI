package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Grayscale converter implementation using the original GrayScale.java logic.
 * Converts the specified image to grayscale by averaging the RGB values of each pixel
 * and using that average as the new RGB values for the corresponding pixel in the output image.
 */
public class GrayscaleConverter implements ImageConverter {

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
                
                // Convert to grayscale by averaging RGB values
                int avg = (argb.red + argb.green + argb.blue) / 3;
                ARGB gray = new ARGB(avg, avg, avg, avg);
                writer.setArgb(x, y, gray.toInt());
            }
        }
        return outputImage;
    }

    @Override
    public String getName() {
        return "Grayscale";
    }
} 