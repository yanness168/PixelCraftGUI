package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.helper.ARGB;
import com.example.pixelcraftgui.recursiable.PixelModifier;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Invert extends Converter{
    /**
     * Inverts the color of the specified image by subtracting each of the RGB values
     * from 255 and writing the result to the corresponding pixel in the output image.
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
         * Modifies the pixel at the specified coordinates in the output image by subtracting each of the RGB values
         * from 255.
         * 255 is the maximum value for an 8-bit color channel (standard in RGB).
         * Source: https://en.wikipedia.org/wiki/RGB_color_model
         *
         * @param input  the input image
         * @param output the output image
         * @param x      the x-coordinate of the pixel to modify
         * @param y      the y-coordinate of the pixel to modify
         */
        PixelModifier invertModifier = (input, output, x, y) -> {
            int rgb = 0;
            try {
                rgb = input.getRGB(x, y);
            } catch (StackOverflowError e) {
                System.err.println("Stack overflow error occurred in Invert.java.  Exiting pixel modification.");
                return;
            }
            ARGB argb = new ARGB(rgb);
            /*
             * Invert each color component by subtracting from 255 (max 8-bit value)
             * - 255 is maximum value for RGB color channels
             * - Subtraction inverts the color (255 - current value)
             */
            int newR = 255 - argb.red;
            int newG = 255 - argb.green;
            int newB = 255 - argb.blue;
            output.setRGB(x, y, new ARGB(newR, newG, newB, 255).toInt());
        };
        recursiveConvert(inputImage, outputImage, invertModifier);
        writeImage(outputImage, outputFileName);
    }
}
