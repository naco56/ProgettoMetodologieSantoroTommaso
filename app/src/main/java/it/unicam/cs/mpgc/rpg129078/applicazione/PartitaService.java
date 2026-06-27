package it.unicam.cs.mpgc.rpg129078.applicazione;

import it.unicam.cs.mpgc.rpg129078.model.*;
import it.unicam.cs.mpgc.rpg129078.model.abilita.Abilita;
import it.unicam.cs.mpgc.rpg129078.model.arma.LaptopAziendale;
import it.unicam.cs.mpgc.rpg129078.model.abilita.PausaCaffe;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.ChiavettaUsb;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Oggetto;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Snack;
import it.unicam.cs.mpgc.rpg129078.persistenza.GsonSalvataggioService;
import it.unicam.cs.mpgc.rpg129078.persistenza.SalvataggioService;

import java.util.Scanner;

/**
 * Servizio che coordina la logica di gioco.
 * Gestisce il flusso della partita, i turni di combattimento
 * e il salvataggio/caricamento tramite SalvataggioService.
 */
public class PartitaService {

    private GestorePartita partita;
    private final SalvataggioService salvataggioService;
    private final Scanner scanner;

    public PartitaService(Scanner scanner) {
        this.scanner = scanner;
        this.salvataggioService = new GsonSalvataggioService();
    }

    /** Avvia una nuova partita con giocatore di default */
    public void nuovaPartita() {
        Giocatore giocatore = new Giocatore(
                "Impiegato",
                100,
                50,
                new LaptopAziendale(),
                new PausaCaffe(),
                new Inventario()
        );
        giocatore.getInventario().aggiungiOggetto(new Snack());
        giocatore.getInventario().aggiungiOggetto(new ChiavettaUsb());

        partita = new GestorePartita(giocatore);
        for (Stanza s : StanzaFactory.creaCampagna()) {
            partita.aggiungiStanza(s);
        }

        System.out.println("\nNuova partita avviata!");
        eseguiPartita();
    }

    /** Carica una partita salvata e la riprende */
    public void caricaPartita() {
        if (!salvataggioService.esisteSalvataggio()) {
            System.out.println("Nessun salvataggio trovato!");
            return;
        }
        partita = GestorePartita.ripristinaDaStato(salvataggioService.carica());
        System.out.println("\nPartita caricata!");
        eseguiPartita();
    }

    /** Salva lo stato corrente della partita */
    public void salva() {
        salvataggioService.salva(partita.esportaStato());
    }

    /** Loop principale della partita */
    private void eseguiPartita() {
        Giocatore giocatore = partita.getGiocatore();

        System.out.println("Benvenuto, " + giocatore.getNome() + "!");
        System.out.println("Sopravvivi all'ufficio e sconfiggi il Direttore!");

        while (giocatore.getVitaCorrente() > 0) {

            Stanza stanza = partita.getStanzaAttuale();
            System.out.println("\n>>> Stanza " + partita.getNumeroStanzaCorrente()
                    + "/" + partita.getTotaleStanze() + ": " + stanza.getNome());

            if (partita.isFinale()) {
                System.out.println("*** STANZA BOSS! ***");
            }

            // combatti ogni nemico della stanza
            for (Nemico nemico : stanza.getNemici()) {

                System.out.println("\nAppare: " + nemico.getNome()
                        + " | HP: " + nemico.getVitaCorrente());

                while (giocatore.getVitaCorrente() > 0 && nemico.getVitaCorrente() > 0) {
                    eseguiTurno(giocatore, nemico);
                }

                if (giocatore.getVitaCorrente() <= 0) break;
                System.out.println(">> " + nemico.getNome() + " sconfitto!");
            }

            if (giocatore.getVitaCorrente() <= 0) {
                System.out.println("\n=== HAI PERSO! Sei stato licenziato. ===");
                return;
            }

            // raccogli oggetti della stanza
            for (Oggetto o : stanza.getOggetti()) {
                System.out.println("Hai trovato: " + o.nome());
                giocatore.getInventario().aggiungiOggetto(o);
            }

            if (partita.isFinale()) {
                System.out.println("\n=== HAI VINTO! Il Direttore è stato sconfitto! ===");
                return;
            }

            // versione corretta: avanza PRIMA di salvare
            System.out.println("\nStanza completata!");
            System.out.println("1 - Avanza alla prossima stanza");
            System.out.println("2 - Salva ed avanza");
            System.out.println("3 - Salva ed esci");

            int sceltaStanza = scanner.nextInt();
            scanner.nextLine();

            partita.avanza(); // avanza sempre, la stanza è completata

            switch (sceltaStanza) {
                case 2 -> salva();             // salva dopo aver avanzato
                case 3 -> {
                    salva();
                    return;
                } // salva dopo aver avanzato, poi esci
            }
        }
    }

    /** Gestisce un singolo turno di combattimento */
    private void eseguiTurno(Giocatore giocatore, Nemico nemico) {

        System.out.println("\n---------------------------");
        System.out.println("[" + giocatore.getNome() + "]"
                + " HP: " + giocatore.getVitaCorrente() + "/" + giocatore.getVitaMassima()
                + " | Energia: " + giocatore.getEnergiaCorrente() + "/" + giocatore.getEnergiaMassima()
                + " | Arma: " + giocatore.getArma().getClass().getSimpleName()
                + " (danno: " + giocatore.getArma().calcolaDanno() + ")");
        System.out.println("[" + nemico.getNome() + "]"
                + " HP: " + nemico.getVitaCorrente() + "/" + nemico.getVitaMassima());
        System.out.println("---------------------------");

        Abilita abilita = giocatore.getAbilita();

        System.out.println("Cosa fai?");
        System.out.println("1 - Attacca (" + giocatore.getArma().calcolaDanno() + " danno)");
        System.out.println("2 - Abilita: " + abilita.nome()
                + " (costo: " + abilita.costoEnergia() + " energia)");
        System.out.println("3 - Usa oggetto inventario");

        int scelta = scanner.nextInt();

        switch (scelta) {

            case 1 -> {
                giocatore.attacca(nemico);
                System.out.println(">> Attacchi " + nemico.getNome()
                        + "! HP rimasti: " + nemico.getVitaCorrente());
            }

            case 2 -> {
                if (giocatore.getEnergiaCorrente() >= abilita.costoEnergia()) {
                    giocatore.setEnergiaCorrente(
                            giocatore.getEnergiaCorrente() - abilita.costoEnergia()
                    );
                    abilita.usa(giocatore, nemico);
                    System.out.println(">> Usi " + abilita.nome() + "!");
                    System.out.println("   HP giocatore: " + giocatore.getVitaCorrente()
                            + " | HP nemico: " + nemico.getVitaCorrente());
                } else {
                    System.out.println(">> Energia insufficiente! Turno perso.");
                }
            }

            case 3 -> {
                giocatore.getInventario().mostraInventario();
                System.out.print("Indice oggetto (o -1 per annullare): ");
                int idx = scanner.nextInt();
                if (idx >= 0) {
                    giocatore.getInventario().usaOggetto(idx, giocatore);
                }
            }

            default -> System.out.println(">> Scelta non valida, turno perso!");
        }

        // risposta del nemico
        if (nemico.getVitaCorrente() > 0) {
            nemico.attacca(giocatore);
            System.out.println(">> " + nemico.getNome() + " ti attacca! "
                    + "HP rimasti: " + giocatore.getVitaCorrente());
        }
    }
}