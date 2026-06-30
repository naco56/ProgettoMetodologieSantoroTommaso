package it.unicam.cs.mpgc.rpg129078.gui;

import it.unicam.cs.mpgc.rpg129078.persistenza.GsonSalvataggioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller della schermata menu principale.
 * Gestisce nuova partita, carica partita ed uscita.
 */
public class MenuController {

    @FXML
    private Button btnCaricaPartita;

    @FXML
    public void initialize() {
        // disabilita "Carica Partita" se non esiste un salvataggio
        btnCaricaPartita.setDisable(
                !new GsonSalvataggioService().esisteSalvataggio()
        );
    }

    @FXML
    private void handleNuovaPartita(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/it/unicam/cs/mpgc/rpg129078/creaPersonaggio.fxml")
            );
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(new Scene(root, 400, 350));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCaricaPartita(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/it/unicam/cs/mpgc/rpg129078/gioco.fxml")
            );
            Parent root = loader.load();
            GiocoController controller = loader.getController();
            controller.caricaPartita();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                    .getScene().getWindow();
            stage.setScene(new Scene(root, 600, 400));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEsci(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                .getScene().getWindow();
        stage.close();
    }
}