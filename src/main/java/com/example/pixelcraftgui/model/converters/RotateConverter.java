package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Rotate converter implementation using the original Rotate logic.
 * Rotates an image by 90 degrees clockwise.
 */
public class RotateConverter implements ImageConverter {

    @Override
    public Image convertImage(Image inputImage) {
        int originalWidth = (int) inputImage.getWidth();
        int originalHeight = (int) inputImage.getHeight();
        
        // For 90-degree rotation, swap width and height
        int newWidth = originalHeight;
        int newHeight = originalWidth;
        
        WritableImage rotatedImage = new WritableImage(newWidth, newHeight);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = rotatedImage.getPixelWriter();

        for (int y = 0; y < originalHeight; y++) {
            for (int x = 0; x < originalWidth; x++) {
                int pixel = reader.getArgb(x, y);
                writer.setArgb(y, originalWidth - 1 - x, pixel);
            }
        }
        return rotatedImage;
    }

    @Override
    public String getName() {
        return "Rotate 90°";
    }
} 