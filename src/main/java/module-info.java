module com.example.practica1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.practica1 to javafx.fxml;
    exports com.example.practica1;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires mysql.connector.j;
}