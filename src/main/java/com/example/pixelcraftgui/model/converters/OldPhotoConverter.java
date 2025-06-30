package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import java.util.Random;

/**
 * Old Photo converter implementation using the original OldPhoto.java logic.
 * Converts the specified image to an "old photo" style by applying a sepia tone and adding a small amount of random noise.
 * The noise is intended to simulate the grain of old film. The resulting image is written to the specified output file.
 */
public class OldPhotoConverter implements ImageConverter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        Random random = new Random();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = reader.getArgb(x, y);
                ARGB argb = new ARGB(rgb);

                // Sepia Tone
                int sepiaR = (int)(0.393 * argb.red + 0.769 * argb.green + 0.189 * argb.blue);
                int sepiaG = (int)(0.349 * argb.red + 0.686 * argb.green + 0.168 * argb.blue);
                int sepiaB = (int)(0.272 * argb.red + 0.534 * argb.green + 0.131 * argb.blue);

                sepiaR = clamp(sepiaR);
                sepiaG = clamp(sepiaG);
                sepiaB = clamp(sepiaB);

                // Noise
                int noise = (random.nextInt(32) - 16); // +/- 16
                sepiaR = clamp(sepiaR + noise);
                sepiaG = clamp(sepiaG + noise);
                sepiaB = clamp(sepiaB + noise);

                // Slight Darkening (Optional)
                sepiaR = (int)(sepiaR * 0.9);
                sepiaG = (int)(sepiaG * 0.9);
                sepiaB = (int)(sepiaB * 0.9);
                sepiaR = clamp(sepiaR);
                sepiaG = clamp(sepiaG);
                sepiaB = clamp(sepiaB);

                writer.setArgb(x, y, new ARGB(sepiaR, sepiaG, sepiaB, 255).toInt());
            }
        }
        return outputImage;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    @Override
    public String getName() {
        return "Old Photo";
    }
} 