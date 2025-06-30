package com.example.pixelcraftgui.view;

import com.example.pixelcraftgui.model.PixelCraftModel;
import com.example.pixelcraftgui.model.ConverterFactory;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * View class for the PixelCraft GUI application.
 * Handles the user interface and display following the MVC pattern.
 */
public class PixelCraftView implements PropertyChangeListener {
    
    private Stage stage;
    private PixelCraftModel model;
    
    // UI Components
    private ImageView imageView;
    private ImageView originalImageView;
    private Button loadButton;
    private Button saveButton;
    private Button undoButton;
    private Button redoButton;
    private Button resetButton;
    private ComboBox<String> converterComboBox;
    private Button applyButton;
    private Label statusLabel;
    private ScrollPane imageScrollPane;
    private ScrollPane originalScrollPane;
    private SplitPane splitPane;
    
    public PixelCraftView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }
    
    /**
     * Initializes the user interface components.
     */
    private void initializeUI() {
        // Create main layout
        BorderPane mainLayout = new BorderPane();
        
        // Create toolbar
        ToolBar toolbar = createToolbar();
        mainLayout.setTop(toolbar);
        
        // Create image display area
        splitPane = createImageDisplayArea();
        mainLayout.setCenter(splitPane);
        
        // Create status bar
        statusLabel = new Label("Ready");
        statusLabel.setPadding(new Insets(5));
        mainLayout.setBottom(statusLabel);
        
        // Set up the scene
        Scene scene = new Scene(mainLayout, 1200, 800);
        stage.setTitle("PixelCraft GUI - Image Editor");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
    }
    
    /**
     * Creates the toolbar with control buttons.
     */
    private ToolBar createToolbar() {
        ToolBar toolbar = new ToolBar();
        
        // File operations
        loadButton = new Button("Load Image");
        loadButton.setTooltip(new Tooltip("Load an image file"));
        
        saveButton = new Button("Save Image");
        saveButton.setTooltip(new Tooltip("Save the current image"));
        saveButton.setDisable(true);
        
        toolbar.getItems().addAll(loadButton, saveButton, new Separator());
        
        // History operations
        undoButton = new Button("Undo");
        undoButton.setTooltip(new Tooltip("Undo last operation (Ctrl+Z)"));
        undoButton.setDisable(true);
        
        redoButton = new Button("Redo");
        redoButton.setTooltip(new Tooltip("Redo last undone operation (Ctrl+Y)"));
        redoButton.setDisable(true);
        
        resetButton = new Button("Reset");
        resetButton.setTooltip(new Tooltip("Reset to original image"));
        resetButton.setDisable(true);
        
        toolbar.getItems().addAll(undoButton, redoButton, resetButton, new Separator());
        
        // Converter selection
        Label converterLabel = new Label("Effect:");
        converterComboBox = new ComboBox<>();
        converterComboBox.getItems().addAll(ConverterFactory.getAvailableConverters());
        converterComboBox.setValue(ConverterFactory.GRAYSCALE);
        
        applyButton = new Button("Apply Effect");
        applyButton.setTooltip(new Tooltip("Apply selected effect to image"));
        applyButton.setDisable(true);
        
        toolbar.getItems().addAll(converterLabel, converterComboBox, applyButton);
        
        return toolbar;
    }
    
    /**
     * Creates the image display area with side-by-side comparison.
     */
    private SplitPane createImageDisplayArea() {
        // Original image view
        originalImageView = new ImageView();
        originalImageView.setPreserveRatio(true);
        originalImageView.setSmooth(true);
        originalScrollPane = new ScrollPane(originalImageView);
        originalScrollPane.setFitToWidth(true);
        originalScrollPane.setFitToHeight(true);
        
        VBox originalBox = new VBox(5);
        originalBox.setAlignment(Pos.CENTER);
        originalBox.getChildren().addAll(
            new Label("Original Image"),
            originalScrollPane
        );
        originalBox.setPadding(new Insets(10));
        
        // Current image view
        imageView = new ImageView();
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageScrollPane = new ScrollPane(imageView);
        imageScrollPane.setFitToWidth(true);
        imageScrollPane.setFitToHeight(true);
        
        VBox currentBox = new VBox(5);
        currentBox.setAlignment(Pos.CENTER);
        currentBox.getChildren().addAll(
            new Label("Modified Image"),
            imageScrollPane
        );
        currentBox.setPadding(new Insets(10));
        
        // Create split pane
        SplitPane splitPane = new SplitPane(originalBox, currentBox);
        splitPane.setDividerPositions(0.5);
        
        return splitPane;
    }
    
    /**
     * Updates the display when the model changes.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (model != null) {
            updateImageDisplay();
            updateButtonStates();
        }
    }
    
    /**
     * Updates the image display with current and original images.
     */
    private void updateImageDisplay() {
        Image currentImage = model.getCurrentImage();
        Image originalImage = model.getOriginalImage();
        
        if (currentImage != null) {
            imageView.setImage(currentImage);
            saveButton.setDisable(false);
            applyButton.setDisable(false);
        } else {
            imageView.setImage(null);
            saveButton.setDisable(true);
            applyButton.setDisable(true);
        }
        
        if (originalImage != null) {
            originalImageView.setImage(originalImage);
            resetButton.setDisable(false);
        } else {
            originalImageView.setImage(null);
            resetButton.setDisable(true);
        }
    }
    
    /**
     * Updates the enabled/disabled state of buttons based on model state.
     */
    private void updateButtonStates() {
        undoButton.setDisable(!model.canUndo());
        redoButton.setDisable(!model.canRedo());
    }
    
    /**
     * Shows a file chooser dialog for loading images.
     * @return the selected file, or null if cancelled
     */
    public File showLoadDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        return fileChooser.showOpenDialog(stage);
    }
    
    /**
     * Shows a file chooser dialog for saving images.
     * @return the selected file, or null if cancelled
     */
    public File showSaveDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("PNG Files", "*.png"),
            new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        return fileChooser.showSaveDialog(stage);
    }
    
    /**
     * Shows an alert dialog with the specified message.
     * @param title the alert title
     * @param message the alert message
     * @param alertType the type of alert
     */
    public void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    /**
     * Updates the status label with the specified message.
     * @param message the status message
     */
    public void updateStatus(String message) {
        statusLabel.setText(message);
    }
    
    /**
     * Gets the selected converter name from the combo box.
     * @return the selected converter name
     */
    public String getSelectedConverter() {
        return converterComboBox.getValue();
    }
    
    // Getters for controller access
    public Button getLoadButton() { return loadButton; }
    public Button getSaveButton() { return saveButton; }
    public Button getUndoButton() { return undoButton; }
    public Button getRedoButton() { return redoButton; }
    public Button getResetButton() { return resetButton; }
    public Button getApplyButton() { return applyButton; }
    public ComboBox<String> getConverterComboBox() { return converterComboBox; }
    
    /**
     * Sets the model reference.
     * @param model the model to observe
     */
    public void setModel(PixelCraftModel model) {
        this.model = model;
        if (model != null) {
            model.addPropertyChangeListener(this);
        }
    }
    
    public javafx.scene.Scene getScene() {
        return stage.getScene();
    }
    
    /**
     * Shows the main window.
     */
    public void show() {
        stage.show();
    }
} 