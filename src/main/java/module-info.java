module com.tunahan.cinemateer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    exports com.tunahan.cinemateer.model;
    opens com.tunahan.cinemateer.model to javafx.fxml;

    exports com.tunahan.cinemateer.controller to javafx.fxml;
    opens com.tunahan.cinemateer.controller to javafx.fxml;


}