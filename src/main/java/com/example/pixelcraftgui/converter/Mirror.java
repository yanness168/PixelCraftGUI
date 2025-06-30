package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.recursiable.PixelModifier;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Mirror extends Converter{
    /**
     * Mirrors the input image horizontally.
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        
        /*
         * Modifies the pixel at the specified coordinates in the output image by mirroring it
         * horizontally. The pixel from the input is placed at the horizontally mirrored position
         * in the output.
         *
         * @param input  the input image
         * @param output the output image
         * @param x      the x-coordinate of the pixel to modify
         * @param y      the y-coordinate of the pixel to modify
         */
        PixelModifier mirrorModifier = (input, output, x, y) -> {
            /*
             * Mirror pixel horizontally by calculating: (width - 1 - x)
             * - input.getWidth() gives image width in pixels
             * - Subtract 1 to convert to 0-based index
             * - Subtract x to mirror position
             */
            int pixel = input.getRGB(x, y);
            output.setRGB(input.getWidth() - x - 1, y, pixel);  // Set mirrored position
        };
        recursiveConvert(inputImage, outputImage, mirrorModifier);
        writeImage(outputImage, outputFileName);
    }
}
