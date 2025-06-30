package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.helper.ARGB;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class GrayScale extends Converter {
    /**
     * Converts the specified image to grayscale by averaging the RGB values of each pixel
     * and using that average as the new RGB values for the corresponding pixel in the output image.
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int rgb = inputImage.getRGB(x, y);
                ARGB argb = new ARGB(rgb);
                int avg = (argb.red + argb.green + argb.blue) / 3;
                outputImage.setRGB(x, y, new ARGB(avg, avg, avg, avg).toInt());
            }
        }
        writeImage(outputImage, outputFileName);
    }
}