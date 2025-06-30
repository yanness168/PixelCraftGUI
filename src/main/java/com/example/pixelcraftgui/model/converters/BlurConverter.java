package com.example.pixelcraftgui.model.converters;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.model.ImageConverter;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

/**
 * Blur converter implementation using the original Blur logic.
 * Applies a blur to the image by sampling pixels in a 45-degree angle from each pixel and averaging the color values.
 */
public class BlurConverter implements ImageConverter {

    @Override
    public Image convertImage(Image inputImage) {
        int width = (int) inputImage.getWidth();
        int height = (int) inputImage.getHeight();
        WritableImage blurredImage = new WritableImage(width, height);
        PixelReader reader = inputImage.getPixelReader();
        PixelWriter writer = blurredImage.getPixelWriter();

        int blurLength = 15; // Adjust for the length of the blur
        double angle = Math.PI / 4;  // 45 degrees (adjust as needed)

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int a = 0, r = 0, g = 0, b = 0, count = 0;

                for (int i = 0; i < blurLength; i++) {
                    int dx = (int) (i * Math.cos(angle));
                    int dy = (int) (i * Math.sin(angle));

                    int nx = x + dx;
                    int ny = y + dy;

                    if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                        ARGB argb = new ARGB(reader.getArgb(nx, ny));
                        a += argb.alpha;
                        r += argb.red;
                        g += argb.green;
                        b += argb.blue;
                        count++;
                    }
                }

                if (count > 0) {  
                    // Avoid division by zero if no valid pixels were sampled
                    writer.setArgb(x, y, new ARGB(a / count, r / count, g / count, b / count).toInt());
                } else {
                    // Use the original pixel if no valid samples
                    writer.setArgb(x, y, reader.getArgb(x, y)); 
                }
            }
        }
        return blurredImage;
    }

    @Override
    public String getName() {
        return "Blur";
    }
} 