package base;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 *
 * @author Daniel Migales Puertas
 *
 */
public class FXMLController implements Initializable {

    @FXML
    private Button buttonCalcularFibonacci;

    @FXML
    private Button buttonCalcularFactorial;

    @FXML
    private Label labelFibonacci;

    @FXML
    private Label labelFactorial;

    @FXML
    private ProgressBar progressBarFactorial;

    @FXML
    private ProgressBar progressBarFibonacci;

    @FXML
    private TextField TexfieldValueFibonacci;

    @FXML
    private TextField TextfieldValueFactorial;

    @FXML
    private TextArea TextareaFibonacci;

    @FXML
    private TextArea TextareaFactorial;

    @FXML
    private Button buttonLimpiarfibonacci;

    @FXML
    private Button buttonLimpiarFactorial;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TextareaFibonacci.setWrapText(true);
        TextareaFactorial.setWrapText(true);

    }

    @FXML
    void calcularFibonacci(ActionEvent event) {

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

    @FXML
    void calcularFactorial(ActionEvent event) {

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

    @FXML
    void cleanTextAreaFibonacci(ActionEvent event) {

        TextareaFibonacci.clear();
    }

    @FXML
    void cleanTextAreaFactorial(ActionEvent event) {

        TextareaFactorial.clear();
    }

}
