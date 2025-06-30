# PixelCraft GUI - Image Editor

A JavaFX-based image editing application that allows users to apply various visual effects to images using an intuitive graphical user interface.

## Features

### Core Functionality
- **Image Loading & Saving**: Load images from file and save processed images as PNG
- **Multiple Image Effects**: Apply various visual effects to images
  - Grayscale conversion
  - Image inversion
  - Blur effect
  - Sharpen effect
  - Mirror/Flip
  - Rotation
  - Posterize effect
  - Old photo effect

### Advanced Features
- **Undo/Redo System**: Complete history tracking with unlimited undo/redo operations
- **Side-by-Side Comparison**: View original and modified images simultaneously
- **Keyboard Shortcuts**: 
  - `Ctrl+Z`: Undo
  - `Ctrl+Y`: Redo
  - `Ctrl+S`: Save image
- **Real-time Preview**: See changes applied immediately
- **Reset to Original**: Restore image to its original state

## Technical Architecture

### Design Patterns
- **MVC (Model-View-Controller)**: Clean separation of concerns
- **Observer Pattern**: PropertyChangeListener for model-view communication
- **Factory Pattern**: ConverterFactory for creating image converters
- **Strategy Pattern**: Different image conversion algorithms

### Project Structure
```
src/main/java/com/example/pixelcraftgui/
├── controller/
│   └── PixelCraftController.java    # Handles user interactions
├── model/
│   ├── PixelCraftModel.java         # Application state and data
│   ├── ImageConverter.java          # Interface for converters
│   ├── ConverterFactory.java        # Factory for creating converters
│   └── converters/                  # Image effect implementations
│       ├── BlurConverter.java
│       ├── GrayscaleConverter.java
│       ├── InvertConverter.java
│       ├── MirrorConverter.java
│       ├── OldPhotoConverter.java
│       ├── PosterizeConverter.java
│       ├── RotateConverter.java
│       └── SharpenConverter.java
├── view/
│   └── PixelCraftView.java          # User interface
├── helper/
│   └── ARGB.java                    # Color manipulation utilities
└── PixelCraftApplication.java       # Main application entry point
```

## Requirements

- **Java**: JDK 8 or higher (Oracle JDK 8 recommended for JavaFX support)
- **Maven**: 3.6+ for dependency management
- **JavaFX**: Included with Oracle JDK 8, or as separate modules for newer JDKs

## Setup & Installation

### Prerequisites
1. Install Oracle JDK 8 (recommended) or OpenJDK 11+ with JavaFX
2. Install Maven 3.6+

### Building the Project
```bash
# Clone the repository
git clone <repository-url>
cd PixelCraftGUI

# Compile the project
mvn clean compile

# Run the application
mvn javafx:run
```

### Alternative: Using IDE
1. Import the project as a Maven project in your IDE
2. Ensure JavaFX is properly configured
3. Run `PixelCraftApplication.java`

## Usage

### Basic Workflow
1. **Load Image**: Click "Load Image" or use File menu to select an image file
2. **Apply Effects**: 
   - Select an effect from the dropdown menu
   - Click "Apply Effect" to process the image
3. **Compare Results**: View original and modified images side by side
4. **Undo/Redo**: Use buttons or keyboard shortcuts to navigate through changes
5. **Save**: Save the final result using "Save Image" or Ctrl+S

### Available Effects
- **Grayscale**: Convert image to black and white
- **Invert**: Invert all colors in the image
- **Blur**: Apply a blur effect for soft edges
- **Sharpen**: Enhance image details and edges
- **Mirror**: Flip the image horizontally
- **Rotate**: Rotate the image 90 degrees clockwise
- **Posterize**: Reduce color palette for artistic effect
- **Old Photo**: Apply vintage/sepia tone effect

### Keyboard Shortcuts
- `Ctrl+Z`: Undo last operation
- `Ctrl+Y`: Redo last undone operation
- `Ctrl+S`: Save current image

## Development

### Adding New Effects
1. Create a new converter class implementing `ImageConverter`
2. Add the converter to `ConverterFactory`
3. Update the UI to include the new effect option

### Example Converter Implementation
```java
public class CustomConverter implements ImageConverter {
    @Override
    public Image convertImage(Image inputImage) {
        // Implement your conversion logic here
        return processedImage;
    }
    
    @Override
    public String getName() {
        return "Custom Effect";
    }
}
```

### Building for Distribution
```bash
# Create executable JAR
mvn clean package

# Run the JAR
java -jar target/pixelcraftgui-1.0.jar
```

## Troubleshooting

### Common Issues
1. **JavaFX not found**: Ensure you're using Oracle JDK 8 or have JavaFX modules installed
2. **Module errors**: Check that `module-info.java` is properly configured
3. **Image loading fails**: Verify the image file format is supported (PNG, JPG, etc.)

### Performance Tips
- Large images may take longer to process
- Use undo/redo sparingly with very large images
- Consider resizing images before processing for better performance

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## Acknowledgments

- Built with JavaFX for the user interface
- Uses Maven for dependency management
- Implements standard design patterns for maintainable code 