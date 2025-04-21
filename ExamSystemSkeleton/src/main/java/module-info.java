module comp3111.examsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens comp3111.examsystem to javafx.fxml;
    exports comp3111.examsystem;
    opens comp3111.examsystem.controller to javafx.fxml;
    exports comp3111.examsystem.controller;
    exports comp3111.examsystem.entity;
    opens comp3111.examsystem.entity to javafx.fxml;
}