package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.helper.ARGB;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Blur extends Converter {
    /**
     * Applies a blur to the specified image by sampling pixels in a
     * 45-degree angle from each pixel and averaging the color values.
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

        int blurLength = 15; // Adjust for the length of the blur
       /*
         45 degrees is commonly used for diagonal blurring
         Reference: Basic image processing techniques often consider 45-degree kernels
         Source: https://en.wikipedia.org/wiki/Kernel_(image_processing)
        */
        double angle = Math.PI / 4;  // 45 degrees (adjust as needed)


        for (int y = 0; y < inputImage.getHeight(); y++) {
            for (int x = 0; x < inputImage.getWidth(); x++) {
                int a = 0, r = 0, g = 0, b = 0, count = 0;

                for (int i = 0; i < blurLength; i++) {
                    int dx = (int) (i * Math.cos(angle));
                    int dy = (int) (i * Math.sin(angle));

                    int nx = x + dx;
                    int ny = y + dy;


                    if (nx >= 0 && nx < inputImage.getWidth() && ny >= 0 && ny < inputImage.getHeight()) {
                        ARGB argb = new ARGB(inputImage.getRGB(nx, ny));
                        a += argb.alpha;
                        r += argb.red;
                        g += argb.green;
                        b += argb.blue;
                        count++;
                    }
                }

                if (count > 0) {  
                    // Avoid division by zero if no valid pixels were sampled
                    outputImage.setRGB(x, y, new ARGB(a / count, r / count, g / count, b / count).toInt());
                } else {
                    // Use the original pixel if no valid samples
                    outputImage.setRGB(x, y, inputImage.getRGB(x,y)); 
                }

            }
        }

        writeImage(outputImage, outputFileName);
    }
}