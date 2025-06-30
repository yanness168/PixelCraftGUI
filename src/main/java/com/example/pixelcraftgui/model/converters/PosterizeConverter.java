package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Posterize converter implementation using the original Posterize.java logic.
 * Converts the specified image to a posterized image with the specified number of color levels.
 * The posterization is done by dividing the color range into the specified number of levels and
 * rounding each color value to the closest level.
 */
public class PosterizeConverter implements ImageConverter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        int numLevels = 4; // Number of color levels (adjust as needed)

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = reader.getArgb(x, y);
                ARGB argb = new ARGB(rgb);

                int posterizedRed = posterizeColor(argb.red, numLevels);
                int posterizedGreen = posterizeColor(argb.green, numLevels);
                int posterizedBlue = posterizeColor(argb.blue, numLevels);

                writer.setArgb(x, y, new ARGB(posterizedRed, posterizedGreen, posterizedBlue, 255).toInt());
            }
        }
        return outputImage;
    }

    private int posterizeColor(int colorValue, int numLevels) {
        int interval = 256 / numLevels;
        return (colorValue / interval) * interval;
    }

    @Override
    public String getName() {
        return "Posterize";
    }
} 