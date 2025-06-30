package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.helper.ARGB;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Posterize extends Converter{
    /**
     * Converts the specified image to a posterized image with the specified number of color levels.
     * The posterization is done by dividing the color range into the specified number of levels and
     * rounding each color value to the closest level.
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        int numLevels = 4; // Number of color levels (adjust as needed)

        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int rgb = inputImage.getRGB(x, y);
                ARGB argb = new ARGB(rgb);

                int posterizedRed = posterizeColor(argb.red, numLevels);
                int posterizedGreen = posterizeColor(argb.green, numLevels);
                int posterizedBlue = posterizeColor(argb.blue, numLevels);


                outputImage.setRGB(x, y, new ARGB(posterizedRed, posterizedGreen, posterizedBlue, 255).toInt());
            }
        }

        writeImage(outputImage, outputFileName);
    }

    private int posterizeColor(int colorValue, int numLevels) {
        int interval = 256 / numLevels;
        return (colorValue / interval) * interval;
    }
}
