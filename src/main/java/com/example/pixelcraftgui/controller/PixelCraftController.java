package com.example.pixelcraftgui.controller;

import com.example.pixelcraftgui.model.ConverterFactory;
import com.example.pixelcraftgui.model.ImageConverter;
import com.example.pixelcraftgui.model.PixelCraftModel;
import com.example.pixelcraftgui.view.PixelCraftView;
import javafx.scene.control.Alert;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * Controller class for the PixelCraft GUI application.
 * Handles user interactions and coordinates between Model and View following the MVC pattern.
 */
public class PixelCraftController implements PropertyChangeListener {
    
    private PixelCraftModel model;
    private PixelCraftView view;
    
    public PixelCraftController(PixelCraftModel model, PixelCraftView view) {
        this.model = model;
        this.view = view;
        view.setModel(model);
        
        // Register as listener for model changes
        model.addPropertyChangeListener(this);
    }
    
    /**
     * Installs event handlers for all UI components.
     */
    public void installControllers() {
        // File operations
        view.getLoadButton().setOnAction(_ -> handleLoadImage());
        view.getSaveButton().setOnAction(_ -> handleSaveImage());
        
        // History operations
        view.getUndoButton().setOnAction(_ -> handleUndo());
        view.getRedoButton().setOnAction(_ -> handleRedo());
        view.getResetButton().setOnAction(_ -> handleReset());
        
        // Converter operations
        view.getApplyButton().setOnAction(_ -> handleApplyConverter());
        
        // Install global keyboard shortcuts
        installGlobalKeyboardShortcuts();
    }
    
    /**
     * Installs global keyboard shortcuts for common operations.
     */
    private void installGlobalKeyboardShortcuts() {
        javafx.scene.Scene scene = view.getScene();
        
        // Install all keyboard shortcuts in a single handler
        scene.setOnKeyPressed(e -> {
            if (e.isControlDown()) {
                switch (e.getCode()) {
                    case Z:
                        handleUndo();
                        e.consume();
                        break;
                    case Y:
                        handleRedo();
                        e.consume();
                        break;
                    case S:
                        handleSaveImage();
                        e.consume();
                        break;
                    default:
                        // Ignore other keys
                        break;
                }
            }
        });
    }
    
    /**
     * Handles loading an image from file.
     */
    private void handleLoadImage() {
        File selectedFile = view.showLoadDialog();
        if (selectedFile != null) {
            try {
                model.loadImage(selectedFile.getAbsolutePath());
                view.updateStatus("Image loaded: " + selectedFile.getName());
            } catch (IOException e) {
                view.showAlert("Error", "Failed to load image: " + e.getMessage(), Alert.AlertType.ERROR);
                view.updateStatus("Failed to load image");
            }
        }
    }
    
    /**
     * Handles saving the current image to file.
     */
    private void handleSaveImage() {
        if (!model.hasImage()) {
            view.showAlert("Warning", "No image to save", Alert.AlertType.WARNING);
            return;
        }
        
        File selectedFile = view.showSaveDialog();

        if (selectedFile != null) {
            try {
                // Ensure file has .png extension
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".png")) {
                    filePath += ".png";
                }
                
                model.saveImage(filePath);
                view.updateStatus("Image saved: " + new File(filePath).getName());
            } catch (IOException e) {
                view.showAlert("Error", "Failed to save image: " + e.getMessage(), Alert.AlertType.ERROR);
                view.updateStatus("Failed to save image");
            }
        }
    }
    
    /**
     * Handles undoing the last operation.
     */
    private void handleUndo() {
        if (model.canUndo()) {
            model.undo();
            view.updateStatus("Undo completed");
        }
    }
    
    /**
     * Handles redoing the last undone operation.
     */
    private void handleRedo() {
        if (model.canRedo()) {
            model.redo();
            view.updateStatus("Redo completed");
        }
    }
    
    /**
     * Handles resetting the image to its original state.
     */
    private void handleReset() {
        if (model.getOriginalImage() != null) {
            model.resetToOriginal();
            view.updateStatus("Image reset to original");
        }
    }
    
    /**
     * Handles applying the selected converter to the current image.
     */
    private void handleApplyConverter() {
        if (!model.hasImage()) {
            view.showAlert("Warning", "No image loaded", Alert.AlertType.WARNING);
            return;
        }
        
        String converterName = view.getSelectedConverter();
        if (converterName == null || converterName.isEmpty()) {
            view.showAlert("Warning", "Please select an effect", Alert.AlertType.WARNING);
            return;
        }
        
        ImageConverter converter = ConverterFactory.createConverter(converterName);
        if (converter == null) {
            view.showAlert("Error", "Unknown converter: " + converterName, Alert.AlertType.ERROR);
            return;
        }
        
        try {
            model.applyConverter(converter);
            view.updateStatus("Applied " + converterName + " effect");
        } catch (Exception e) {
            view.showAlert("Error", "Failed to apply effect: " + e.getMessage(), Alert.AlertType.ERROR);
            view.updateStatus("Failed to apply effect");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("image".equals(evt.getPropertyName())) {
            view.getUndoButton().setDisable(!model.canUndo());
            view.getRedoButton().setDisable(!model.canRedo());
        }
    }
} 