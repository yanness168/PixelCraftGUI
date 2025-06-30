package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.helper.ARGB;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class OldPhoto extends Converter{
    /**
     * Converts the specified image to an "old photo" style by applying a sepia tone and adding a small amount of random noise.
     * The noise is intended to simulate the grain of old film. The resulting image is written to the specified output file.
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        Random random = new Random();

        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int rgb = inputImage.getRGB(x, y);
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


                outputImage.setRGB(x, y, new ARGB(sepiaR, sepiaG, sepiaB, 255).toInt());
            }
        }

        writeImage(outputImage, outputFileName);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }
}
