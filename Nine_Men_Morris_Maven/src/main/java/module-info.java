module com.example.nine_men_morris {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    opens com.example.nine_men_morris to javafx.fxml;
    exports com.example.nine_men_morris;
}