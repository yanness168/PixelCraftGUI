package com.example.pixelcraftgui.model;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Model class for the PixelCraft GUI application.
 * Manages the application state and image data following the MVC pattern.
 */
public class PixelCraftModel {
    
    private Image currentImage;
    private Image originalImage;
    private List<Image> imageHistory;
    private int currentHistoryIndex;
    private String currentImagePath;
    private PropertyChangeSupport propertyChangeSupport;
    
    public PixelCraftModel() {
        this.imageHistory = new ArrayList<>();
        this.currentHistoryIndex = -1;
        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.propertyChangeSupport.removePropertyChangeListener(listener);
    }
    
    private void notifyImageChanged() {
        this.propertyChangeSupport.firePropertyChange("image", null, currentImage);
    }
    
    /**
     * Loads an image from the specified file path.
     * @param filePath the path to the image file
     * @throws IOException if the image cannot be loaded
     */
    public void loadImage(String filePath) throws IOException {
        FileInputStream inputFile = new FileInputStream(filePath);
        Image newImage = new Image(inputFile);
        
        this.originalImage = newImage;
        this.currentImage = newImage;
        this.currentImagePath = filePath;
        
        // Clear history and add the new image
        this.imageHistory.clear();
        this.imageHistory.add(newImage);
        this.currentHistoryIndex = 0;
        
        notifyImageChanged();
    }
    
    /**
     * Applies a converter to the current image.
     * @param converter the converter to apply
     */
    public void applyConverter(ImageConverter converter) {
        if (currentImage == null) {
            return;
        }

        // Apply the converter to the current image
        Image convertedImage = converter.convertImage(currentImage);
        this.currentImage = convertedImage;
        
        // Add to history (remove any future history if we're not at the end)
        while (currentHistoryIndex < imageHistory.size() - 1) {
            imageHistory.remove(imageHistory.size() - 1);
        }
        imageHistory.add(convertedImage);
        currentHistoryIndex++;
        
        notifyImageChanged();
    }
    
    /**
     * Saves the current image to the specified file path.
     * @param filePath the path where to save the image
     * @throws IOException if the image cannot be saved
     */
    public void saveImage(String filePath) throws IOException {
        if (currentImage == null) {
            return;
        }

        // Get the width and height of the current image
        int width = (int) currentImage.getWidth();
        int height = (int) currentImage.getHeight();
        
        // Get the pixel reader from the original image
        PixelReader pixelReader = currentImage.getPixelReader();
        
        // Create a BufferedImage
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        // Copy the pixels to the BufferedImage
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                bufferedImage.setRGB(x, y, pixelReader.getArgb(x, y));
            }
        }
        
        // Save the BufferedImage as PNG
        ImageIO.write(bufferedImage, "png", new File(filePath));
    }
    
    /**
     * Undoes the last applied converter.
     */
    public void undo() {
        if (currentHistoryIndex > 0) {
            currentHistoryIndex--;
            currentImage = imageHistory.get(currentHistoryIndex);
            notifyImageChanged();
        }
    }
    
    /**
     * Redoes the last undone converter.
     */
    public void redo() {
        if (currentHistoryIndex < imageHistory.size() - 1) {
            currentHistoryIndex++;
            currentImage = imageHistory.get(currentHistoryIndex);
            notifyImageChanged();
        }
    }
    
    /**
     * Resets the image to its original state.
     */
    public void resetToOriginal() {
        if (originalImage != null) {
            currentImage = originalImage;
            notifyImageChanged();
        }
    }
    
    public Image getCurrentImage() {
        return currentImage;
    }
    
    public Image getOriginalImage() {
        return originalImage;
    }
    
    public String getCurrentImagePath() {
        return currentImagePath;
    }
    
    public boolean canUndo() {
        return currentHistoryIndex > 0;
    }
    
    public boolean canRedo() {
        return currentHistoryIndex < imageHistory.size() - 1;
    }
    
    public boolean hasImage() {
        return currentImage != null;
    }
} 