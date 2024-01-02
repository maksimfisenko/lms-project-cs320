module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;


    opens com.example.demo to javafx.fxml;
    opens com.example.demo.control to javafx.fxml;
    opens com.example.demo.model to java.base;
    exports com.example.demo;
    exports com.example.demo.control;
    exports com.example.demo.model;
}