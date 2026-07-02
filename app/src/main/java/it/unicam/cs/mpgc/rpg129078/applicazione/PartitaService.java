package it.unicam.cs.mpgc.rpg129078.applicazione;

import it.unicam.cs.mpgc.rpg129078.model.*;
import it.unicam.cs.mpgc.rpg129078.model.abilita.Abilita;
import it.unicam.cs.mpgc.rpg129078.model.arma.LaptopAziendale;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.ChiavettaUsb;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Oggetto;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Snack;
import it.unicam.cs.mpgc.rpg129078.persistenza.SalvataggioService;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Servizio applicativo che coordina la logica di gioco,
 * indipendentemente dall'interfaccia che lo utilizza (GUI, CLI, ...).
 * Comunica gli eventi tramite un logger iniettabile invece di
 * dipendere direttamente da System.out o da componenti grafici.
 */
public class PartitaService {

    private final SalvataggioService salvataggioService;
    private Consumer<String> logger = msg -> {}; // no-op di default

    private GestorePartita partita;
    private Nemico nemicoCorrente;
    private Iterator<Nemico> nemiciIterator;

    private boolean aumentoLivelloAvvenuto = false;
    private String messaggioAumentoLivello = "";

    public PartitaService(SalvataggioService salvataggioService) {
        this.salvataggioService = salvataggioService;
    }

    /** Permette a chi usa il service di ricevere i messaggi di gioco */
    public void setLogger(Consumer<String> logger) {
        this.logger = logger;
    }

    private void log(String messaggio) {
        logger.accept(messaggio);
    }

    public void nuovaPartita(String nome, Abilita abilita) {
        Giocatore giocatore = new Giocatore(
                nome, 100, 50,
                new LaptopAziendale(), abilita, new Inventario()
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
        log("Sei entrato in: " + stanza.getNome());
        nemiciIterator = stanza.getNemici().iterator();
        prossimoNemico();
    }

    private void prossimoNemico() {
        while (nemiciIterator.hasNext()) {
            Nemico n = nemiciIterator.next();
            if (n.getVitaCorrente() > 0) {
                nemicoCorrente = n;
                log("\nAppare: " + n.getNome() + " | HP: " + n.getVitaCorrente());
                return;
            }
        }
        nemicoCorrente = null;
        stanzaCompletata();
    }

    private void stanzaCompletata() {
        log("\nStanza completata!");
        for (Oggetto o : partita.getStanzaAttuale().getOggetti()) {
            partita.getGiocatore().getInventario().aggiungiOggetto(o);
            log("Hai trovato: " + o.nome());
        }
        if (!partita.isFinale()) {
            partita.avanza();
            iniziaStanza();
        }
        // se è la stanza finale, isPartitaVinta() lo segnalerà a chi chiama
    }

    public void attacca() {
        eseguiTurno(() -> {
            partita.getGiocatore().attacca(nemicoCorrente);
            log(">> Attacchi " + nemicoCorrente.getNome()
                    + "! HP rimasti: " + nemicoCorrente.getVitaCorrente());
        });
    }

    public void usaAbilita() {
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

    public void usaOggetto(int indice) {
        eseguiTurno(() -> {
            Oggetto oggetto = partita.getGiocatore().getInventario().getOggetti().get(indice);
            partita.getGiocatore().getInventario().usaOggetto(indice, partita.getGiocatore());
            log(">> Usi " + oggetto.nome() + "!");
        });
    }

    private void eseguiTurno(Runnable azioneGiocatore) {
        if (nemicoCorrente == null || partita.getGiocatore().getVitaCorrente() <= 0) return;

        azioneGiocatore.run();

        if (nemicoCorrente.getVitaCorrente() <= 0) {
            log(">> " + nemicoCorrente.getNome() + " sconfitto!");

            // EXP in base al tipo di nemico
            int exp = nemicoCorrente.getTipo() == TipoNemico.BOSS ? 50 : 15;
            boolean levelUp = partita.getGiocatore().guadagnaEsperienza(exp);

            if (levelUp) {
                aumentoLivelloAvvenuto = true;
                messaggioAumentoLivello = "LIVELLO AUMENTATO! Sei al livello "
                        + partita.getGiocatore().getLivello()
                        + " — HP e Energia aumentati e ripristinati!";
                log("\n★ " + messaggioAumentoLivello);
            }

            prossimoNemico();
            return;
        }

        nemicoCorrente.attacca(partita.getGiocatore());
        log(">> " + nemicoCorrente.getNome() + " ti attacca! HP rimasti: "
                + partita.getGiocatore().getVitaCorrente());
    }

    public boolean aumentoAvvenuto() {
        boolean val = aumentoLivelloAvvenuto;
        aumentoLivelloAvvenuto = false; // reset dopo la lettura
        return val;
    }

    public String getMessaggioAumento() { return messaggioAumentoLivello; }

    public int getLivelloGiocatore() { return partita.getGiocatore().getLivello(); }
    public int getEspCorrente() { return partita.getGiocatore().getEsperienzaCorrente(); }
    public int getEspPerLivello() { return partita.getGiocatore().getEsperienzaPerLivello(); }

    public void salva() {
        salvataggioService.salva(partita.esportaStato());
    }



    public Giocatore getGiocatore() {
        return partita.getGiocatore();
    }

    public Nemico getNemicoCorrente() {
        return nemicoCorrente;
    }

    public String getNomeStanzaCorrente() {
        return partita.getStanzaAttuale().getNome();
    }

    public int getNumeroStanzaCorrente() {
        return partita.getNumeroStanzaCorrente();
    }

    public int getTotaleStanze() {
        return partita.getTotaleStanze();
    }

    public boolean isStanzaBoss() {
        return partita.isFinale();
    }

    public List<Oggetto> getInventario() {
        return partita.getGiocatore().getInventario().getOggetti();
    }

    public boolean isPartitaVinta() {
        return partita.isFinale() && nemicoCorrente == null;
    }

    public boolean isPartitaPersa() {
        return partita.getGiocatore().getVitaCorrente() <= 0;
    }
}