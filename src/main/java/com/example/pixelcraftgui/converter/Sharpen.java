package com.example.pixelcraftgui.converter;

import com.example.pixelcraftgui.helper.ARGB;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Sharpen extends Converter{
    private static final int[][] kernel = {
            {0, -1, 0},
            {-1, 5,-1},
            {0, -1, 0}
    };

    /**
     * Applies a sharpening effect to the specified image by convolving it with the
     * following kernel:
     *
     * 0 -1  0
     * -1  5 -1
     *  0 -1  0
     *
     * @param inputFileName the input image filename
     * @param outputFileName the output image filename
     * @throws IOException if the image could not be read or written
     */
    @Override
    public void convert(String inputFileName, String outputFileName) throws IOException {
        BufferedImage inputImage = readImage(inputFileName);
        BufferedImage outputImage = new BufferedImage(inputImage.getWidth(), inputImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < inputImage.getHeight() - 1; y++) {
            for (int x = 1; x < inputImage.getWidth() - 1; x++) {
                int sumR = 0, sumG = 0, sumB = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        int pixel = inputImage.getRGB(x + kx, y + ky);
                        ARGB argb = new ARGB(pixel);
                        sumR += argb.red * kernel[ky + 1][kx + 1];
                        sumG += argb.green * kernel[ky + 1][kx + 1];
                        sumB += argb.blue * kernel[ky + 1][kx + 1];
                    }
                }

                sumR = Math.min(255, Math.max(0, sumR));
                sumG = Math.min(255, Math.max(0, sumG));
                sumB = Math.min(255, Math.max(0, sumB));

                outputImage.setRGB(x, y, new ARGB(sumR, sumG, sumB, 255).toInt());
            }
        }
        writeImage(outputImage, outputFileName);
    }
}
