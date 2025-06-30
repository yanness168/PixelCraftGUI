package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Mirror converter implementation using the original Mirror.java logic.
 * Mirrors the input image horizontally.
 */
public class MirrorConverter implements ImageConverter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                /*
                 * Mirror pixel horizontally by calculating: (width - 1 - x)
                 * - width gives image width in pixels
                 * - Subtract 1 to convert to 0-based index
                 * - Subtract x to mirror position
                 */
                int pixel = reader.getArgb(x, y);
                writer.setArgb(width - x - 1, y, pixel);  // Set mirrored position
            }
        }
        return outputImage;
    }

    @Override
    public String getName() {
        return "Mirror";
    }
} 