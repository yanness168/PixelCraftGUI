package com.example.pixelcraftgui.helper;

/**
 * Represents an ARGB (Alpha, Red, Green, Blue) color.
 * Provides methods to extract ARGB components from a packed integer and vice versa.
 */
public class ARGB {

    private static final int ALPHA_SHIFT = 24;
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;
    private static final int BLUE_MASK = 0xff;

    public int alpha, red, green, blue;

    /**
     * Constructs an ARGB object from a packed integer representation.
     * @param pixel the packed ARGB integer
     */
    public ARGB(int pixel) {
        // Extract ARGB components from the pixel integer
        this.alpha = (pixel >> ALPHA_SHIFT) & BLUE_MASK;
        this.red = (pixel >> RED_SHIFT) & BLUE_MASK;
        this.green = (pixel >> GREEN_SHIFT) & BLUE_MASK;
        this.blue = pixel & BLUE_MASK;
    }

    /**
     * Constructs an ARGB object from individual ARGB component values.
     * @param a alpha component (0-255)
     * @param r red component (0-255)
     * @param g green component (0-255)
     * @param b blue component (0-255)
     */
    public ARGB(int a, int r, int g, int b) {
        this.alpha = a;
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    /**
     * Packs the ARGB components into a single integer.
     * @return the packed ARGB integer
     */
    public int toInt() {
        // Encode ARGB components into a single integer
        return (this.alpha << ALPHA_SHIFT) | (this.red << RED_SHIFT) | (this.green << GREEN_SHIFT) | this.blue;
    }
}