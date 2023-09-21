package com.example.practica1.vistas;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Loteria extends Stage {

    CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();
    private static boolean juegoIniciado = false;
    private static boolean ganador = false;
    private ArrayList<String> todasLasCartas = new ArrayList<>();
    private ArrayList<String> cartasMostradas = new ArrayList<>();
    private String cartaActual = "";
    private Boolean iniciado = false;
    private Scene escena;
    private HBox hPrincipal, hBtnSeleccion;
    private VBox vTablilla, vMazo;
    private ImageView imvCarta;
    private Button[][] arBtnTablilla = new Button[4][4];
    private String[][] winin = new String[4][4];
    private Button btnAnterior, btnSiguiente, btnIniciar, btnRaro;
    private GridPane grdTablilla;
    private int indiceImagenActual = 0;
    private String[][] template =
            {
                    {"barril.jpg", "alacran.jpg", "apache.jpg", "araña.jpg", "arbol.jpg", "arpa.jpg", "bandera.jpg", "bandolon.jpg", "borracho.jpg", "bota.jpg", "botella.jpg", "calavera.jpg", "camaron.jpg", "campana.jpg", "cantarito.jpg", "catrin.jpg"},
                    {"violoncello.jpg", "venado.jpg", "pescado.jpg", "pino.jpg", "pescado.jpg", "arpa.jpg", "bandera.jpg", "bandolon.jpg", "borracho.jpg", "bota.jpg", "botella.jpg", "calavera.jpg", "camaron.jpg", "campana.jpg", "cantarito.jpg", "catrin.jpg"},
                    {"rana.jpg", "diablo.jpg", "musico.jpg", "pino.jpg", "pescado.jpg", "arpa.jpg", "bandera.jpg", "rosa.jpg", "borracho.jpg", "paraguas.jpg", "palma.jpg", "pajaro.jpg", "camaron.jpg", "gallo.jpg", "cantarito.jpg", "estrella.jpg"},
                    {"sol.jpg", "sirena.jpg", "pescado.jpg", "pera.jpg", "pescado.jpg", "arpa.jpg", "bandera.jpg", "catrin.jpg", "chalupa.jpg", "maceta.jpg", "luna.jpg", "melon.jpg", "camaron.jpg", "pera.jpg", "garza.jpg", "muerte.jpg"},
                    {"corazon.jpg", "mano.jpg", "corona.jpg", "dama.jpg", "diablo.jpg", "pescado.jpg", "arpa.jpg", "bandera.jpg", "mundo.jpg", "negrito.jpg", "jaras.jpg", "pescado.jpg", "pino.jpg", "sierna.jpg", "pera.jpg", "sol.jpg", "rana.jpg"},
            };

    String[] arrCartas = {"alacran.jpg", "apache.jpg", "araña.jpg", "arbol.jpg", "arpa.jpg", "bandera.jpg",
            "bandolon.jpg", "barril.jpg", "borracho.jpg", "bota.jpg", "botella.jpg", "calavera.jpg", "camaron.jpg",
            "campana.jpg", "cantarito.jpg", "catrin.jpg", "cazo.jpg", "chalupa.jpg", "corazon.jpg", "corona.jpg",
            "cotorro.jpg", "dama.jpg", "diablo.jpg", "escalera.jpg", "estrella.jpg", "gallo.jpg", "garza.jpg",
            "gorrito.jpg", "jaras.jpg", "luna.jpg", "maceta.jpg", "mano.jpg", "melon.jpg", "muerte.jpg", "mundo.jpg",
            "musico.jpg", "negrito.jpg", "nopal.jpg", "pajaro.jpg", "palma.jpg", "paraguas.jpg", "pera.jpg", "pescado.jpg",
            "pino.jpg", "rana.jpg", "rosa.jpg", "sandia.jpg", "sirena.jpg", "sol.jpg", "soldado.jpg", "tambor.jpg", "valiente.jpg",
            "venado.jpg", "violoncello.jpg"};


    public Loteria() {
        todasLasCartas.addAll(Arrays.asList(arrCartas));
        CrearUI();
        escena = new Scene(hPrincipal, 800, 600);
        this.setTitle("Loteria");
        this.setScene(escena);
        this.show();
        limpiarMatriz();
    }

    private void mensajeGanador() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("GANADOR");
        alert.setHeaderText(null);
        alert.setContentText("Has ganadooooooo!");
        alert.showAndWait();
    }

    private void CrearUI() {
        grdTablilla = new GridPane();
        CrearTablilla(template[0]);
        CrearMazo();
        btnAnterior = new Button("<");
        btnAnterior.setPrefSize(200, 40);
        btnSiguiente = new Button(">");
        btnSiguiente.setPrefSize(200, 40);

        btnAnterior.setOnAction(event -> clickBtnAnterior());
        btnSiguiente.setOnAction(event -> clickBtnSiguiente());

        hBtnSeleccion = new HBox(btnAnterior, btnSiguiente);
        vTablilla = new VBox(grdTablilla, hBtnSeleccion);
        vTablilla.setSpacing(20);

        hPrincipal = new HBox(vTablilla, vMazo);
        hPrincipal.setPadding(new Insets(20));

    }

    private void CrearMazo() {

        // Image imgDorso = new Image(new File("src\\main\\java\\com\\example\\practica1\\imagenes\\dorso.png").toURI().toString());
        Image imgDorso = new Image(new File("src/main/java/com/example/practica1/imagenes/dorso.png").toURI().toString());
        imvCarta = new ImageView(imgDorso);
        imvCarta.setFitHeight(200);
        imvCarta.setFitWidth(300);

        btnIniciar = new Button("Iniciar");
        btnIniciar.setOnAction(event -> iniciarJuego());

        btnRaro = new Button("Puedes darme clic");
        btnRaro.setPrefSize(200,40);
        btnRaro.setDisable(true);
        btnRaro.setOnAction(event -> {
            mostrarMensaje();
        });
        vMazo = new VBox(imvCarta,btnIniciar, btnRaro);
    }

    private void CrearTablilla(String[] arImagenes) {

        int pos=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {

                    Image imgCartaP = new Image(new File("src/main/java/com/example/practica1/imagenes/" + arImagenes[pos]).toURI().toString());
                    ImageView imv = new ImageView(imgCartaP);

                    imv.setFitHeight(120);
                    imv.setFitWidth(100);
                    int x= i;
                    int y = j;
                    String nameImg = arImagenes[pos];
                    arBtnTablilla[i][j] = new Button();
                    Button c = arBtnTablilla[i][j];
                    arBtnTablilla[i][j].setOnAction(actionEvent -> cambiarImagen(c, x, y, nameImg));
                    arBtnTablilla[i][j].setGraphic(imv);
                    arBtnTablilla[i][j].setPrefSize(100, 140);
                    grdTablilla.add(arBtnTablilla[i][j], i, j);
                    pos++;
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        }
    }

    public void clickBtnAnterior(){
        try {
            if (indiceImagenActual > 0) {
                indiceImagenActual--;

                CrearTablilla(template[indiceImagenActual]);
            } else {
                CrearTablilla(template[template.length - 1]);
            }
        }catch (Exception e){

        }
    }
    public void clickBtnSiguiente(){
        try {
            if (indiceImagenActual < template.length - 1) {
                indiceImagenActual++;
                CrearTablilla(template[indiceImagenActual]);
            } else {
                CrearTablilla(template[0]);
            }
        } catch (Exception e){

        }
    }

    public void iniciarJuego() {
        CrearTablilla(template[indiceImagenActual]);
        limpiarMatriz();
        btnRaro.setDisable(true);
        if(iniciado) {
            btnAnterior.setDisable(false);
            btnSiguiente.setDisable(false);
        } else {
            btnAnterior.setDisable(true);
            btnSiguiente.setDisable(true);
        }
        iniciado = !iniciado;
        juegoIniciado = true;
        iniciarThread();
        esperandoGanador();
        mostrarMensaje();
    }

    void mostrarMensaje() {
        Thread thread = new Thread(() -> {
            while(true) {
                if(ganador)
                    try{
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("GANADOR");
                            alert.setHeaderText(null);
                            alert.setContentText("Has ganadooooooo!");
                            alert.showAndWait();
                        });
                        Thread.currentThread().interrupt();
                        break;
                    }catch (Exception e){
                        System.out.println(e);
                    }
                if(!ganador && !juegoIniciado)
                    try{
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("NO GANADOR");
                            alert.setHeaderText(null);
                            alert.setContentText("NO Has ganadooooooo!");
                            alert.showAndWait();
                        });
                        Thread.currentThread().interrupt();
                        break;
                    }catch (Exception e){
                        System.out.println(e);
                    }
            }

        });
        thread.start();
    }

    // Método para iniciar el hilo
    private void iniciarThread() {
        cartasMostradas.clear();
        btnIniciar.setDisable(true);
        mezclarArrayList(todasLasCartas);
        //String[] cartas = {"barril.jpg","alacran.jpg","apache.jpg","araña.jpg"};
        Thread thread = new Thread(() -> {
            for(String s: todasLasCartas) {
                try {
                    cartaActual = s;
                    cartasMostradas.add(s);
                    Image imgDorso = new Image(new File("src/main/java/com/example/practica1/imagenes/" + s).toURI().toString());
                    imvCarta.setImage(imgDorso);
                    imvCarta.setFitHeight(200);
                    imvCarta.setFitWidth(300);
                    Thread.sleep(2000);
                    if (ganador) {
                        break;
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            btnIniciar.setDisable(false);
            btnRaro.setDisable(false);
            try {
                Thread.sleep(2000);
                juegoIniciado = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Thread.currentThread().interrupt();
        });
        // Iniciar el hilo
        thread.start();
    }

    private void limpiarMatriz() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                winin[i][j] = "";
    }

    private void esperandoGanador() {
        Thread thread = new Thread(() -> {
            int contador = 0;
            while(juegoIniciado) {
                for(int i = 0; i < 4; i++) {
                    contador = 0;
                    ganador = false;
                    for(int j = 0; j < 4; j++) {
                        contador += winin[i][j].isEmpty() ? 0 : 1;
                    }
                    if (contador == 4) {
                        ganador = true;
                        juegoIniciado = false;
                        break;
                    }
                }
            }
            for(int i = 0; i < 4; i++) {
                contador = 0;
                ganador = false;
                for(int j = 0; j < 4; j++) {
                    contador += winin[i][j].isEmpty() ? 0 : 1;

                }
                if (contador == 4) {
                    ganador = true;
                    break;
                }
            }
            btnIniciar.setDisable(false);
            Thread.currentThread().interrupt();
        });
        // Iniciar el hilo
        thread.start();
    }

    private void cambiarImagen(Button btn, int i, int j, String nameImg) {
        if (!nameImg.equals(cartaActual))
            return ;
        String newNameImage = "dorso.png";
        if (winin[i][j].isEmpty()) {
            winin[i][j] = i +","+ j +" "+nameImg;
        } else {
            newNameImage = winin[i][j].split(" ")[1];
            winin[i][j] = "";
        }
        // System.out.println(i+" -- "+j);
        Image imgCartaP = new Image(new File("src/main/java/com/example/practica1/imagenes/"+newNameImage).toURI().toString());
        ImageView imv = new ImageView(imgCartaP);
        imv.setFitHeight(120);
        imv.setFitWidth(100);
        btn.setPrefSize(100, 140);
        btn.setGraphic(imv);
    }

    public static void mezclarArrayList(ArrayList<String> lista) {
        Collections.shuffle(lista);
    }

}