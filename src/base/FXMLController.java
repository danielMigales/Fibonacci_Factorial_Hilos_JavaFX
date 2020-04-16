package base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
public class FXMLController implements Initializable {

    @FXML
    private MenuBar MenuBar;

    @FXML
    private TabPane tabPane;

    @FXML
    private TextArea TextareaFibonacci;

    @FXML
    private ProgressBar progressBarFibonacci;

    @FXML
    private Label labelFibonacci;

    @FXML
    private TextField TexfieldValueFibonacci;

    @FXML
    private Button buttonCalculateFibonacci;

    @FXML
    private Button buttonCleanFibonacci;

    @FXML
    private TextArea TextareaFactorial;

    @FXML
    private TextField TextfieldValueFactorial;

    @FXML
    private ProgressBar progressBarFactorial;

    @FXML
    private Label labelFactorial;

    @FXML
    private Button buttonCalculateFactorial;

    @FXML
    private Button buttonCleanFactorial;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //con esto los textarea se adaptan al tamaño del texto
        TextareaFibonacci.setWrapText(true);
        TextareaFactorial.setWrapText(true);

    }

    @FXML
    void saveTxt(ActionEvent event) {
        //guarda los resultados en un archivo txt
        FileChooser fileChooser = new FileChooser();
        Stage stage = null;
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            FileWriter fw = null;
            BufferedWriter bw = null;
            try {
                fw = new FileWriter(file, false);
                bw = new BufferedWriter(fw);
                String textoFibonacci = TextareaFibonacci.getText();
                String textoFactorial = TextareaFactorial.getText();
                bw.write("\nCalculo de la serie fibonacci\n");
                bw.write(textoFibonacci, 0, textoFibonacci.length());
                bw.write("\nCalculo del Factorial\n");
                bw.write(textoFactorial, 0, textoFactorial.length());
            } catch (Exception ex) {
                TextareaFibonacci.appendText(ex.toString());
                TextareaFactorial.appendText(ex.toString());
            } finally {
                try {
                    bw.close();
                } catch (Exception ex2) {
                    TextareaFibonacci.appendText(ex2.toString());
                    TextareaFactorial.appendText(ex2.toString());
                }
            }
        }
    }

    @FXML
    void closeApp(ActionEvent event) {
        //cierra la aplicacion desde el menu cerrar
        Stage stage = (Stage) this.MenuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void info(ActionEvent event) {
        //cuadro de alerta que se muestra en el menu ayuda
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText(null);
        alert.setContentText("Creada por Daniel Migales Puertas");
        alert.showAndWait();
    }

    @FXML
    void buttonCalculateFibonacci(ActionEvent event) {//asociado al boton calcular en la pestaña calculo fibonacci
        //variable que introduce el usuario parseada a integer
        int n = Integer.parseInt(TexfieldValueFibonacci.getText());

        //FALTA VALIDAR QUE NO INTRODUZCAN TEXTO.....
        //si el numero introducido es menor que 10.000 realiza el calculo. En caso contrario salta una alerta.
        if (n < 10000) {
            //hilo que calcula la serie de fibonacci 
            Thread hilo1 = new Thread() {
                @Override
                public void run() {

                    //para que al actualizar el textarea y sobrepasar el limite de lineas en javafx se necesita otro hilo
                    Runnable updater = new Runnable() {
                        @Override
                        public void run() {

                            //variables de inicio para el calculo
                            int a = 0;
                            int b = 1;
                            int c = 1;

                            for (int i = 0; i < n; i++) {
                                //en el bucle se va añadiendo en el textarea el valor de a en cada calculo
                                TextareaFibonacci.appendText(String.valueOf(a + "\t"));
                                c = a + b;
                                a = b;
                                b = c;
                            }
                        }
                    };
                    Platform.runLater(updater);
                }
            };
            hilo1.start();

            //hilo que controla la barra de progreso
            Thread hilo2 = new Thread() {
                @Override
                public void run() {
                    //recupero el numero del textarea para que la barra vaya a la par (seria el 100%)
                    int n = Integer.parseInt(TexfieldValueFibonacci.getText());

                    //mediante el for incremento el avance en 1 hasta llegar al final
                    for (int i = 0; i <= n; i++) {
                        final int position = i;
                        progressBarFibonacci.setProgress(position / n);
                    }
                }
            };
            hilo2.start();

        } else {
            alertFibonacci();
        }

    }

    @FXML
    void buttonCalculateFactorial(ActionEvent event) { //asociado al boton calcular en la pestaña factorial

        //se obtiene valor del textarrea y se parsea a double
        double n = Double.parseDouble(TextfieldValueFactorial.getText());

        //aviso de que si el numero introducido es mayor de 170 el resultado es infinito
        if (n > 170) {
            alertFactorial();
        }

        //hilo que calcula el factorial de un numero
        Thread hilo3 = new Thread() {
            @Override
            public void run() {

                double factorial = 1;

                if (n == 0) {
                    factorial = 1;
                } else {
                    for (int i = 1; i <= n; i++) {
                        factorial *= i;
                    }
                }
                TextareaFactorial.setText(String.valueOf(factorial));
            }
        };
        hilo3.start();

        //hilo que controla la barra de progreso
        Thread hilo4 = new Thread() {
            @Override
            public void run() {
                int n = 100;

                for (int i = 0; i <= n; i++) {
                    final int position = i;
                    progressBarFactorial.setProgress(position / 100.0);
                }
            }
        };
        hilo4.start();
    }

    @FXML
    void cleanTextAreaFibonacci(ActionEvent event) { //boton limpiar pestaña fibonacci
        //limpia todos los areas de texto y pone la barra a 0
        TextareaFibonacci.clear();
        TexfieldValueFibonacci.clear();
        progressBarFibonacci.setProgress(0);
    }

    @FXML
    void cleanTextAreaFactorial(ActionEvent event) {//boton limpiar pestaña factorial
        //limpia todos los areas de texto y pone la barra a 0
        TextareaFactorial.clear();
        TextfieldValueFactorial.clear();
        progressBarFactorial.setProgress(0);
    }

    //alerta por si el usuario quiere calcular una serie fibonacci que sobrepase los limites del textarea. Hasta 10.000 muestra los resultados correctamente con un minimo delay.
    void alertFibonacci() {
        //cuadro de alerta que se muestra 
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Calculo Excesivo");
        alert.setHeaderText("¿De verdad necesita calcular una serie tan grande?");
        alert.setContentText("Por favor intentelo con un numero mas pequeño.");
        alert.showAndWait();
    }

    //alerta por si el usuario quiere calcular un factorial mas grande de 170, ya que a partir de 171 el resultado se muestra como infinito
    void alertFactorial() {
        //cuadro de alerta que se muestra 
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Calculo Excesivo");
        alert.setHeaderText("A partir de 170, el resultado mostrado equivale a infinito.");
        alert.setContentText("Por favor intentelo con un numero mas pequeño.");
        alert.showAndWait();
    }

}
