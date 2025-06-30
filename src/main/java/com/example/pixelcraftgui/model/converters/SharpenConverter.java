package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Sharpen converter implementation using the original Sharpen.java logic.
 * Applies a sharpening effect to the specified image by convolving it with the
 * following kernel:
 *
 * 0 -1  0
 * -1  5 -1
 *  0 -1  0
 */
public class SharpenConverter implements ImageConverter {
    
    private static final int[][] kernel = {
            {0, -1, 0},
            {-1, 5,-1},
            {0, -1, 0}
    };

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage outputImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = outputImage.getPixelWriter();

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sumR = 0, sumG = 0, sumB = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = reader.getArgb(x + kx, y + ky);
                        ARGB argb = new ARGB(pixel);
                        sumR += argb.red * kernel[ky + 1][kx + 1];
                        sumG += argb.green * kernel[ky + 1][kx + 1];
                        sumB += argb.blue * kernel[ky + 1][kx + 1];
                    }
                }

                sumR = Math.min(255, Math.max(0, sumR));
                sumG = Math.min(255, Math.max(0, sumG));
                sumB = Math.min(255, Math.max(0, sumB));

                writer.setArgb(x, y, new ARGB(sumR, sumG, sumB, 255).toInt());
            }
        }
        return outputImage;
    }

    @Override
    public String getName() {
        return "Sharpen";
    }
} 