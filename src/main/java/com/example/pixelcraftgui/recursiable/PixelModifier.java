package com.example.pixelcraftgui.recursiable;
import java.awt.image.BufferedImage;

/**
 * Interface for Invert & Mirror that modify individual pixels in a BufferedImage.
 * 
 * This interface provides a contract for classes that need to perform pixel-level
 * modifications on an image. The modifyPixel method is called for each pixel in
 * the image, and the implementation should modify the corresponding pixel in the
 * output image.
 * 
 * @param inputImage the original image
 * @param outputImage the image to modify
 * @param x the x-coordinate of the pixel to modify
 * @param y the y-coordinate of the pixel to modify
 */
public interface PixelModifier {
    void modifyPixel(BufferedImage inputImage, BufferedImage outputImage, int x, int y);
}
