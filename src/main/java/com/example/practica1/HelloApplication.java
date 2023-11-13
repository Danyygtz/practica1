package com.example.practica1;

import com.example.practica1.components.Hilo;
import com.example.practica1.modelos.Conexion;
import com.example.practica1.vistas.Calculadora;
import com.example.practica1.vistas.Loteria;
import com.example.practica1.vistas.PistaAtletismo;
import com.example.practica1.vistas.Restaurante;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class HelloApplication extends Application {

    private Scene escena;
    private BorderPane borderPane;
    private MenuBar menuBar;
    private Menu menuParcial1, menuParcial2, menuSalir;
    private MenuItem mitCalculadora, mitLoteria, mitSalir, mitRestaurante, mitPista;


    private  void CrearUI(){
        mitCalculadora = new MenuItem("Calculadora");
        mitCalculadora.setOnAction((event)-> new Calculadora()); //expresion lambda, se ejecuta con Metodo SetOnAction
        mitLoteria = new MenuItem("Loteria");
        mitLoteria.setOnAction((event)-> new Loteria());

        menuParcial1 = new Menu("Parcial 1"); //Pestañas q se despliegan del parcial 1
        menuParcial1.getItems().add(mitCalculadora);
        menuParcial1.getItems().add(mitLoteria);
        int b;

        mitRestaurante = new MenuItem("Restaurante");
        mitRestaurante.setOnAction((event)->new Restaurante());
        mitPista = new MenuItem("Pista Atletismo");
        mitPista.setOnAction((event)-> new PistaAtletismo());
        menuParcial2 = new Menu("Parcial 2");
        menuParcial2.getItems().addAll(mitRestaurante,mitPista);

        menuSalir = new Menu("Mas opciones");
        mitSalir = new MenuItem("Salir");
        mitSalir.setOnAction((event)->Salir());
        menuSalir.getItems().add(mitSalir);

        menuBar = new MenuBar(menuParcial1,menuParcial2, menuSalir);

    }

    private void Salir() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensaje del sistema");
        alert.setHeaderText("Confirmar cerrar sistema?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    public void start(Stage stage) throws IOException {

        connectToDB();
        CrearUI();
        borderPane = new BorderPane();
        borderPane.setLeft(menuBar);

        escena = new Scene(borderPane,900,500);
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/estilos.css").toExternalForm());
        stage.setScene(escena);
        stage.show();
    }

    public void connectToDB(){
        Conexion.createConnextion();
        System.out.println("Conexion establecida");
    }

    public static void main(String[] args) {
        launch();
    }
}