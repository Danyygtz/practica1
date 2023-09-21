package com.example.practica1.vistas;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage { //1er paso genera el escenario con extends

    private Scene escena;
    private VBox vBox;
    private GridPane grdTeclado;
    private TextField txtResultado;
    private Button[][] arTeclado =new Button[4][4];
    private char arDigitos[] = {'7','8','9','/','4','5','6','*','1','2','3','-','0','.','=','+'};
    private double primerNumero = 0;
    private double segundoNumero = 0;
    private String operador = "";

    public Calculadora(){

        CrearUI();
        escena = new Scene(vBox,200,200);
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/calculadora.css").toExternalForm());
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        int pos = 0;
        grdTeclado = new GridPane();
        vBox = new VBox();
        txtResultado = new TextField("0");
        txtResultado.setAlignment(Pos.BASELINE_RIGHT);
        txtResultado.setEditable(false);
        for (int i = 0; i < 4; i++){
            for (int j=0;j<4; j++){
                arTeclado[i][j] = new Button(arDigitos[pos]+"");
                arTeclado[i][j].setPrefSize(50,50);
                int finalPos = pos;
                arTeclado[i][j].setOnAction(
                        (event)->GenerarExpresion(arDigitos[finalPos]+""));
                grdTeclado.add(arTeclado[i][j],i,j);
                pos++;

            }
        }
        vBox=new VBox(txtResultado,grdTeclado);
    }

    private void GenerarExpresion(String simbolo){

        if (Character.isDigit(simbolo.charAt(0)) || simbolo.equals(".")) {
            txtResultado.appendText(simbolo);

        } else if ("+-*/".contains(simbolo)) {
            if (!operador.isEmpty() && !txtResultado.getText().isEmpty()) {
                segundoNumero = Double.parseDouble(txtResultado.getText());
                double resultado = calcular();
                txtResultado.setText(String.valueOf(resultado));
                primerNumero = resultado;

            } else {
                primerNumero = Double.parseDouble(txtResultado.getText());
            }

            operador = simbolo;
            txtResultado.clear();

        } else if (simbolo.equals("=")) {
            if (!operador.isEmpty() && !txtResultado.getText().isEmpty()) {
                segundoNumero = Double.parseDouble(txtResultado.getText());
                double resultado = calcular();
                txtResultado.setText(String.valueOf(resultado));
                primerNumero = resultado;
                operador = "";
            }
        }
    }

    private double calcular() {
        double resultado = 0;
        switch (operador) {
            case "+":
                resultado = primerNumero + segundoNumero;
                break;
            case "-":
                resultado = primerNumero - segundoNumero;
                break;
            case "*":
                resultado = primerNumero * segundoNumero;
                break;
            case "/":
                if (segundoNumero != 0) {
                    resultado = primerNumero / segundoNumero;
                } else {
                    txtResultado.setText("Error: División por cero");
                }
                break;
        }
        return resultado;
    }
}

