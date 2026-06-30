package it.unicam.cs.mpgc.rpg129078.gui;

import it.unicam.cs.mpgc.rpg129078.persistenza.GsonSalvataggioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Node;

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
            SceneUtils.cambiaScena((Node) event.getSource(),
                    "/it/unicam/cs/mpgc/rpg129078/creaPersonaggio.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCaricaPartita(ActionEvent event) {
        try {
            GiocoController controller = SceneUtils.cambiaScena((Node) event.getSource(),
                    "/it/unicam/cs/mpgc/rpg129078/gioco.fxml");
            controller.caricaPartita();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEsci(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}