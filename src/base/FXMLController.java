package base;

import static java.lang.Thread.sleep;
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
    void closeApp(ActionEvent event) {

        Stage stage = (Stage) this.MenuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void info(ActionEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText(null);
        alert.setContentText("Creada por Daniel Migales Puertas");
        alert.showAndWait();
    }

    @FXML
    void buttonCalculateFibonacci(ActionEvent event) {

        calculateFibonacci();
        fillProgress(progressBarFibonacci);
    }

    @FXML
    void buttonCalculateFactorial(ActionEvent event) {

        calculateFactorial();
        fillProgress(progressBarFactorial);
    }

    @FXML
    void cleanTextAreaFibonacci(ActionEvent event) {

        TextareaFibonacci.clear();
        TexfieldValueFibonacci.clear();
        progressBarFibonacci.setProgress(0);
    }

    @FXML
    void cleanTextAreaFactorial(ActionEvent event) {

        TextareaFactorial.clear();
        TextfieldValueFactorial.clear();
        progressBarFactorial.setProgress(0);
    }

    private void calculateFibonacci() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                int n = Integer.parseInt(TexfieldValueFibonacci.getText());
                int a = 0;
                int b = 1;
                int c = 1;

                for (int i = 0; i < n; i++) {

                    try {
                        Thread.sleep(10);
                        TextareaFibonacci.appendText(String.valueOf(a + "\t"));
                    } catch (InterruptedException ex) {
                    }

                    c = a + b;
                    a = b;
                    b = c;
                }
            }
        }).start();
    }

    private void fillProgress(ProgressBar progressBar) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(1000);
                        final int position = i;
                        progressBar.setProgress(position / 100.0);
                    } catch (InterruptedException ex) {
                    }

                }
            }
        }).start();
    }

    private void calculateFactorial() {

        new Thread(new Runnable() {
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
        }).start();
    }
}
