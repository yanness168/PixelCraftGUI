module com.example.pixelcraftgui {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive java.desktop;

    opens com.example.pixelcraftgui to javafx.fxml;
    opens com.example.pixelcraftgui.view to javafx.fxml;
    opens com.example.pixelcraftgui.controller to javafx.fxml;
    opens com.example.pixelcraftgui.model to javafx.fxml;
    
    exports com.example.pixelcraftgui;
    exports com.example.pixelcraftgui.view;
    exports com.example.pixelcraftgui.controller;
    exports com.example.pixelcraftgui.model;
    exports com.example.pixelcraftgui.helper;
} 