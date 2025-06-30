package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.recursiable.PixelModifier;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class Converter {
    public abstract void convert(String inputFileName, String outputFileName) throws IOException;
    /**
     * Reads an image from the specified file.
     *
     * @param inputFileName the input image filename
     * @return the read image
     * @throws IOException if the image could not be read
     */
    protected BufferedImage readImage(String inputFileName) throws IOException {
        return ImageIO.read(new File(inputFileName));
    }
    /**
     * Writes the specified image to the specified file.
     *
     * @param image the image to be written
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be written
     */
    protected void writeImage(BufferedImage image, String outputFileName) throws IOException {
        ImageIO.write(image, "png", new File(outputFileName));
    }

    /**
     * Recursively converts an input image to an output image using a specified pixel modifier - Invert & Mirror.
     *
     * @param inputImage the input image to be processed
     * @param outputImage the output image to store the processed pixels
     * @param x the current x-coordinate being processed
     * @param y the current y-coordinate being processed
     * @param modifier the pixel modifier to apply to each pixel
     *
     * Note: A stack overflow error will be triggered when image sizes are greater than x=130 y=5.
     */
    protected void recursiveConvert(BufferedImage inputImage, BufferedImage outputImage, PixelModifier modifier) {
        /*
         * 10000 pixels threshold (100x100 image) determines when to switch from recursion to iteration.
         * This prevents stack overflow while maintaining the recursive requirement for small images.
         * The value was chosen because:
         * 1. Java's default stack size can typically handle ~10000 recursive calls
         * 2. Provides a good balance between demonstrating recursion and preventing crashes
         */
        if (inputImage.getWidth() * inputImage.getHeight() > 10000) {
            System.out.println("Image too large for recursion, using iteration");
            iterativeConvert(inputImage, outputImage, modifier);
            return;
        }
        recursiveConvertHelper(inputImage, outputImage, modifier, 0, 0, 0);
    }

    private void recursiveConvertHelper(BufferedImage inputImage, BufferedImage outputImage,
                                      PixelModifier modifier, int x, int y, int depth) {
        // Base case: reached end of image
        if (y >= inputImage.getHeight()) {
            return;
        }

        try {
            modifier.modifyPixel(inputImage, outputImage, x, y);
        } catch (Exception e) {
            System.err.println("Error during pixel modification: " + e.getMessage());
        }

        // Move to next pixel
        int nextX = x + 1;
        int nextY = y;
        if (nextX >= inputImage.getWidth()) {
            nextX = 0;
            nextY = y + 1;
        }

        // Recursive case
        recursiveConvertHelper(inputImage, outputImage, modifier, nextX, nextY, depth + 1);
    }

    /**
     * Fallback to use Loop iteration to convert the image.
     *
     * @param inputImage the input image to be processed
     * @param outputImage the output image to store the processed pixels
     * @param modifier the pixel modifier to apply to each pixel
     */
    protected void iterativeConvert(BufferedImage inputImage, BufferedImage outputImage, PixelModifier modifier) {
        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                try {
                    modifier.modifyPixel(inputImage, outputImage, x, y);
                } catch (Exception e) {
                    System.err.println("Error during pixel modification: " + e.getMessage());
                }
            }
        }
    }
}