package com.example.pixelcraftgui.converter;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Rotate extends Converter {
    /**
     * Rotate the image by 90 degrees clockwise.
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getHeight(), inputImage.getWidth(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int pixel = inputImage.getRGB(x, y);
                outputImage.setRGB(y, inputImage.getWidth() - 1 - x, pixel);
            }
        }
        writeImage(outputImage, outputFileName);
    }
}