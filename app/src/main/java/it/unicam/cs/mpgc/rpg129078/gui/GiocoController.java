package it.unicam.cs.mpgc.rpg129078.gui;

import it.unicam.cs.mpgc.rpg129078.model.*;
import it.unicam.cs.mpgc.rpg129078.model.abilita.Abilita;
import it.unicam.cs.mpgc.rpg129078.model.abilita.PausaCaffe;
import it.unicam.cs.mpgc.rpg129078.model.arma.LaptopAziendale;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.ChiavettaUsb;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Oggetto;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Snack;
import it.unicam.cs.mpgc.rpg129078.persistenza.GsonSalvataggioService;
import it.unicam.cs.mpgc.rpg129078.persistenza.SalvataggioService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;

/**
 * Controller della schermata di gioco.
 * Gestisce il combattimento turno per turno e la progressione tra le stanze.
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

    private GestorePartita partita;
    private Nemico nemicoCorrente;
    private Iterator<Nemico> nemiciIterator;
    private final SalvataggioService salvataggioService = new GsonSalvataggioService();


    public void iniziaNuovaPartita() {
        Giocatore giocatore = new Giocatore(
                "Impiegato", 100, 50,
                new LaptopAziendale(), new PausaCaffe(), new Inventario()
        );
        giocatore.getInventario().aggiungiOggetto(new Snack());
        giocatore.getInventario().aggiungiOggetto(new ChiavettaUsb());

        partita = new GestorePartita(giocatore);
        for (Stanza s : StanzaFactory.creaCampagna()) {
            partita.aggiungiStanza(s);
        }
        iniziaStanza();
    }


    public void caricaPartita() {
        partita = GestorePartita.ripristinaDaStato(salvataggioService.carica());
        iniziaStanza();
    }


    private void iniziaStanza() {
        Stanza stanza = partita.getStanzaAttuale();
        lblStanza.setText("Stanza " + partita.getNumeroStanzaCorrente()
                + "/" + partita.getTotaleStanze() + ": " + stanza.getNome()
                + (partita.isFinale() ? "  *** BOSS ***" : ""));

        nemiciIterator = stanza.getNemici().iterator();
        log("Sei entrato in: " + stanza.getNome());
        prossimoNemico();
    }


    private void prossimoNemico() {
        while (nemiciIterator.hasNext()) {
            Nemico n = nemiciIterator.next();
            if (n.getVitaCorrente() > 0) {
                nemicoCorrente = n;
                log("\nAppare: " + n.getNome() + " | HP: " + n.getVitaCorrente());
                aggiornaUI();
                return;
            }
        }
        stanzaCompletata();
    }


    private void stanzaCompletata() {
        log("\nStanza completata!");

        for (Oggetto o : partita.getStanzaAttuale().getOggetti()) {
            partita.getGiocatore().getInventario().aggiungiOggetto(o);
            log("Hai trovato: " + o.nome());
        }

        if (partita.isFinale()) {
            mostraFinePartita(true);
            return;
        }

        partita.avanza();
        iniziaStanza();
    }


    private void eseguiTurno(Runnable azioneGiocatore) {
        if (nemicoCorrente == null || partita.getGiocatore().getVitaCorrente() <= 0) return;

        azioneGiocatore.run();
        aggiornaUI();

        if (nemicoCorrente.getVitaCorrente() <= 0) {
            log(">> " + nemicoCorrente.getNome() + " sconfitto!");
            prossimoNemico();
            return;
        }

        nemicoCorrente.attacca(partita.getGiocatore());
        log(">> " + nemicoCorrente.getNome() + " ti attacca! HP rimasti: "
                + partita.getGiocatore().getVitaCorrente());
        aggiornaUI();

        if (partita.getGiocatore().getVitaCorrente() <= 0) {
            mostraFinePartita(false);
        }
    }

    @FXML
    private void handleAttacca(ActionEvent event) {
        eseguiTurno(() -> {
            partita.getGiocatore().attacca(nemicoCorrente);
            log(">> Attacchi " + nemicoCorrente.getNome()
                    + "! HP rimasti: " + nemicoCorrente.getVitaCorrente());
        });
    }

    @FXML
    private void handleAbilita(ActionEvent event) {
        eseguiTurno(() -> {
            Giocatore g = partita.getGiocatore();
            Abilita abilita = g.getAbilita();
            if (g.getEnergiaCorrente() >= abilita.costoEnergia()) {
                g.setEnergiaCorrente(g.getEnergiaCorrente() - abilita.costoEnergia());
                abilita.usa(g, nemicoCorrente);
                log(">> Usi " + abilita.nome() + "! HP nemico: "
                        + nemicoCorrente.getVitaCorrente());
            } else {
                log(">> Energia insufficiente! Turno perso.");
            }
        });
    }

    @FXML
    private void handleInventario(ActionEvent event) {
        List<Oggetto> oggetti = partita.getGiocatore().getInventario().getOggetti();

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
                eseguiTurno(() -> {
                    partita.getGiocatore().getInventario().usaOggetto(idx, partita.getGiocatore());
                    log(">> Usi " + nomeOggetto + "!");
                });
            }
        });
    }

    @FXML
    private void handleSalvaEsci(ActionEvent event) {
        salvataggioService.salva(partita.esportaStato());
        log("Partita salvata!");
        tornaAlMenu(event);
    }

    /** Mostra il dialogo di fine partita e torna al menu */
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
            Parent root = FXMLLoader.load(
                    getClass().getResource("/it/unicam/cs/mpgc/rpg129078/menu.fxml")
            );
            Stage stage = (Stage) logArea.getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void tornaAlMenu(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/it/unicam/cs/mpgc/rpg129078/menu.fxml")
            );
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 400, 300));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aggiornaUI() {
        Giocatore g = partita.getGiocatore();
        lblGiocatore.setText("[" + g.getNome() + "] HP: " + g.getVitaCorrente()
                + "/" + g.getVitaMassima() + " | Energia: " + g.getEnergiaCorrente()
                + "/" + g.getEnergiaMassima()
                + " | " + g.getArma().getClass().getSimpleName()
                + " (danno: " + g.getArma().calcolaDanno() + ")");

        if (nemicoCorrente != null && nemicoCorrente.getVitaCorrente() > 0) {
            lblNemico.setText("[" + nemicoCorrente.getNome() + "] HP: "
                    + nemicoCorrente.getVitaCorrente() + "/" + nemicoCorrente.getVitaMassima());
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