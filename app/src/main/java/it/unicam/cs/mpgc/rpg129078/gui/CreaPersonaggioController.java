package it.unicam.cs.mpgc.rpg129078.gui;

import it.unicam.cs.mpgc.rpg129078.model.abilita.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;

/**
  Controller della schermata di creazione personaggio.
  Permette al giocatore di scegliere nome e abilita prima di iniziare.
 */
public class CreaPersonaggioController {

    @FXML private TextField txtNome;
    @FXML private RadioButton rbPausaCaffe;
    @FXML private RadioButton rbPremioProduzione;
    @FXML private RadioButton rbLavoroStraordinario;
    @FXML private Label lblDescrizione;

    private static final String DESC_PAUSA =
            "Pausa Caffe: recupera 15 HP. Ideale per resistere a lungo in battaglia.";
    private static final String DESC_PREMIO =
            "Premio Produzione: infligge 10 danni al nemico e cura 5 HP. Bilanciata.";
    private static final String DESC_LAVORO =
            "Lavoro Straordinario: infligge 20 danni. Massimo attacco, nessuna cura.";

    @FXML
    public void initialize() {
        // crea il ToggleGroup qui per evitare problemi con FXML
        ToggleGroup gruppo = new ToggleGroup();
        rbPausaCaffe.setToggleGroup(gruppo);
        rbPremioProduzione.setToggleGroup(gruppo);
        rbLavoroStraordinario.setToggleGroup(gruppo);
        rbPausaCaffe.setSelected(true);
        lblDescrizione.setText(DESC_PAUSA);
    }

    @FXML
    private void handleSceltaAbilita(ActionEvent event) {
        if (rbPausaCaffe.isSelected()) {
            lblDescrizione.setText(DESC_PAUSA);
        } else if (rbPremioProduzione.isSelected()) {
            lblDescrizione.setText(DESC_PREMIO);
        } else if (rbLavoroStraordinario.isSelected()) {
            lblDescrizione.setText(DESC_LAVORO);
        }
    }

    @FXML
    private void handleIniziaPartita(ActionEvent event) {
        String nome = txtNome.getText().trim();
        if (nome.isEmpty()) nome = "Impiegato";

        Abilita abilita;
        if (rbPremioProduzione.isSelected()) {
            abilita = new PremioProduzione();
        } else if (rbLavoroStraordinario.isSelected()) {
            abilita = new LavoroStraordinario();
        } else {
            abilita = new PausaCaffe();
        }

        try {
            GiocoController controller = SceneUtils.cambiaScena((Node) event.getSource(),
                    "/it/unicam/cs/mpgc/rpg129078/gioco.fxml");
            controller.iniziaNuovaPartita(nome, abilita);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleIndietro(ActionEvent event) {
        try {
            SceneUtils.cambiaScena((Node) event.getSource(),
                    "/it/unicam/cs/mpgc/rpg129078/menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}