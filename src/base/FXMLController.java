package base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;
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

        TextareaFibonacci.setWrapText(true);
        TextareaFactorial.setWrapText(true);

    }

    @FXML
    void saveTxt(ActionEvent event) { //guarda los resultados en un archivo txt
        
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
    void closeApp(ActionEvent event
    ) {

        Stage stage = (Stage) this.MenuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void info(ActionEvent event
    ) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText(null);
        alert.setContentText("Creada por Daniel Migales Puertas");
        alert.showAndWait();
    }

    @FXML
    void buttonCalculateFibonacci(ActionEvent event
    ) {

        //hilo que calcula la serie de fibonacci
        Thread hilo1 = new Thread() {
            @Override
            public void run() {
                int n = Integer.parseInt(TexfieldValueFibonacci.getText());
                int a = 0;
                int b = 1;
                int c = 1;

                for (int i = 0; i < n; i++) {
                    TextareaFibonacci.appendText(String.valueOf(a + "\t"));
                    c = a + b;
                    a = b;
                    b = c;
                }
            }
        };

        //hilo que contrala la barra de progreso
        Thread hilo2 = new Thread() {
            @Override
            public void run() {
                int n = Integer.parseInt(TexfieldValueFibonacci.getText());
                for (int i = 0; i <= n; i++) {
                    final int position = i;
                    progressBarFibonacci.setProgress(position / n);
                }
            }
        };

        hilo1.start();
        hilo2.start();
    }

    @FXML
    void buttonCalculateFactorial(ActionEvent event
    ) {

        //hilo que calcula el factorial de un numero
        Thread hilo3 = new Thread() {
            @Override
            public void run() {

                double n = Double.parseDouble(TextfieldValueFactorial.getText());
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

        //hilo que contrala la barra de progreso
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

        hilo3.start();
        hilo4.start();
    }

    @FXML
    void cleanTextAreaFibonacci(ActionEvent event
    ) {

        TextareaFibonacci.clear();
        TexfieldValueFibonacci.clear();
        progressBarFibonacci.setProgress(0);
    }

    @FXML
    void cleanTextAreaFactorial(ActionEvent event
    ) {

        TextareaFactorial.clear();
        TextfieldValueFactorial.clear();
        progressBarFactorial.setProgress(0);
    }

}
