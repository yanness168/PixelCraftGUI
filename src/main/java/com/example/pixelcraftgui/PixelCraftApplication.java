package com.example.pixelcraftgui;

import com.example.pixelcraftgui.controller.PixelCraftController;
import com.example.pixelcraftgui.model.PixelCraftModel;
import com.example.pixelcraftgui.view.PixelCraftView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main application class for PixelCraft GUI.
 * Sets up the MVC architecture and launches the application.
 */
public class PixelCraftApplication extends Application {
    
    @Override
    public void start(Stage stage) {
        // Create MVC components
        PixelCraftModel model = new PixelCraftModel();
        PixelCraftView view = new PixelCraftView(stage);
        PixelCraftController controller = new PixelCraftController(model, view);
        
        // Install event handlers
        controller.installControllers();
        
        // Show the application
        view.show();
    }

    public static void main(String[] args) {
        launch();
    }
}