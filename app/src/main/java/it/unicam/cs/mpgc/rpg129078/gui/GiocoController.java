package it.unicam.cs.mpgc.rpg129078.gui;

import it.unicam.cs.mpgc.rpg129078.applicazione.PartitaService;
import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.Nemico;
import it.unicam.cs.mpgc.rpg129078.model.abilita.Abilita;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Oggetto;
import it.unicam.cs.mpgc.rpg129078.persistenza.GsonSalvataggioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.util.List;

/**
 * Controller della schermata di gioco (solo presentazione).
 * Tutta la logica di gioco è delegata a PartitaService, in modo che
 * non sia legata a JavaFX e sia riusabile da altre interfacce future.
 */
public class GiocoController {

    @FXML private Label lblStanza;
    @FXML private Label lblGiocatore;
    @FXML private Label lblNemico;
    @FXML private TextArea logArea;
    @FXML private Button btnAttacca;
    @FXML private Button btnAbilita;
    @FXML private Button btnInventario;
    @FXML private Button btnSalvaEsci;

    private final PartitaService service = new PartitaService(new GsonSalvataggioService());

    @FXML
    public void initialize() {
        service.setLogger(this::log);
    }

    public void iniziaNuovaPartita(String nome, Abilita abilita) {
        service.nuovaPartita(nome, abilita);
        aggiornaUI();
    }

    public void caricaPartita() {
        service.caricaPartita();
        aggiornaUI();
    }

    @FXML
    private void handleAttacca(ActionEvent event) {
        service.attacca();
        dopoAzione();
    }

    @FXML
    private void handleAbilita(ActionEvent event) {
        service.usaAbilita();
        dopoAzione();
    }

    @FXML
    private void handleInventario(ActionEvent event) {
        List<Oggetto> oggetti = service.getInventario();

        if (oggetti.isEmpty()) {
            log(">> Inventario vuoto!");
            return;
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(
                oggetti.get(0).nome(),
                oggetti.stream().map(Oggetto::nome).toList()
        );
        dialog.setTitle("Inventario");
        dialog.setHeaderText("Scegli un oggetto da usare:");
        dialog.setContentText("Oggetto:");

        dialog.showAndWait().ifPresent(nomeOggetto -> {
            List<String> nomi = oggetti.stream().map(Oggetto::nome).toList();
            int idx = nomi.indexOf(nomeOggetto);
            if (idx >= 0) {
                service.usaOggetto(idx);
                dopoAzione();
            }
        });
    }

    @FXML
    private void handleSalvaEsci(ActionEvent event) {
        service.salva();
        log("Partita salvata!");
        tornaAlMenu(event);
    }

    private void dopoAzione() {
        aggiornaUI();
        if (service.isPartitaPersa()) {
            mostraFinePartita(false);
        } else if (service.isPartitaVinta()) {
            mostraFinePartita(true);
        }
    }

    private void mostraFinePartita(boolean vittoria) {
        abilitaPulsanti(false);
        log(vittoria ? "\n=== HAI VINTO! ===" : "\n=== HAI PERSO! Sei stato licenziato. ===");

        Alert alert = new Alert(vittoria ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(vittoria ? "Vittoria!" : "Game Over");
        alert.setHeaderText(vittoria ? "Complimenti!" : "Hai perso!");
        alert.setContentText(vittoria ? "Hai sconfitto il boss e sopravvissuto all'ufficio!"
                : "Sei stato licenziato. Riprova!");
        alert.showAndWait();

        try {
            SceneUtils.cambiaScena(logArea, "/it/unicam/cs/mpgc/rpg129078/menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void tornaAlMenu(ActionEvent event) {
        try {
            SceneUtils.cambiaScena((Node) event.getSource(),
                    "/it/unicam/cs/mpgc/rpg129078/menu.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aggiornaUI() {
        Giocatore g = service.getGiocatore();
        lblStanza.setText("Stanza " + service.getNumeroStanzaCorrente()
                + "/" + service.getTotaleStanze() + ": " + service.getNomeStanzaCorrente()
                + (service.isStanzaBoss() ? "  *** BOSS ***" : ""));

        lblGiocatore.setText("[" + g.getNome() + "] HP: " + g.getVitaCorrente()
                + "/" + g.getVitaMassima() + " | Energia: " + g.getEnergiaCorrente()
                + "/" + g.getEnergiaMassima()
                + " | " + g.getArma().getClass().getSimpleName()
                + " (danno: " + g.getArma().calcolaDanno() + ")");

        Nemico n = service.getNemicoCorrente();
        if (n != null && n.getVitaCorrente() > 0) {
            lblNemico.setText("[" + n.getNome() + "] HP: "
                    + n.getVitaCorrente() + "/" + n.getVitaMassima());
        } else {
            lblNemico.setText("");
        }
    }

    private void abilitaPulsanti(boolean abilita) {
        btnAttacca.setDisable(!abilita);
        btnAbilita.setDisable(!abilita);
        btnInventario.setDisable(!abilita);
        btnSalvaEsci.setDisable(!abilita);
    }

    private void log(String messaggio) {
        logArea.appendText(messaggio + "\n");
    }
}