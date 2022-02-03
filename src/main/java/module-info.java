module com.example.openskyproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;


    opens com.example.openskyproject to javafx.fxml;
    exports com.example.openskyproject;
}