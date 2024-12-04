module com.example.aplicacao {
    requires javafx.fxml;
    requires javafx.controls;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens com.example.aplicacao to javafx.fxml;
    exports com.example.aplicacao;
    exports com.example.aplicacao.controller;
    opens com.example.aplicacao.controller to javafx.fxml;
}