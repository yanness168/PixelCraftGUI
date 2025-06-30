module com.example.pixelcraftgui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example.pixelcraftgui to javafx.fxml;
    exports com.example.pixelcraftgui;
}