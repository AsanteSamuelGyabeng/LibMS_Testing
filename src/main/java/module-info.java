module com.example.libms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.libms to javafx.fxml;
    exports com.example.libms;
    opens com.example.libms.controllers to javafx.fxml;
    opens com.example.libms.model to javafx.base;
    opens com.example.libms.services to javafx.base;


}