package it.unicam.cs.mpgc.rpg129078.model;

import java.util.ArrayList;
import java.util.List;
import it.unicam.cs.mpgc.rpg129078.model.arma.Arma;
import it.unicam.cs.mpgc.rpg129078.model.arma.ArmaFactory;
import it.unicam.cs.mpgc.rpg129078.model.abilita.Abilita;
import it.unicam.cs.mpgc.rpg129078.model.abilita.AbilitaFactory;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.OggettoFactory;
import it.unicam.cs.mpgc.rpg129078.persistenza.StatoPartita;

public class GestorePartita {

    private List<Stanza> stanze;
    private int stanzaCorrente;
    private Giocatore giocatore;

    public GestorePartita(Giocatore giocatore) {
        this.giocatore = giocatore;
        this.stanzaCorrente = 0;
        this.stanze = new ArrayList<>();
    }

    public void aggiungiStanza(Stanza stanza) {
        stanze.add(stanza);
    }

    public Stanza getStanzaAttuale() {
        return stanze.get(stanzaCorrente);
    }

    public boolean avanza() {
        if (stanzaCorrente < stanze.size() - 1) {
            stanzaCorrente++;
            return true;
        }
        return false; // era l'ultima stanza
    }

    public boolean isFinale() {
        return stanzaCorrente == stanze.size() - 1;
    }

    public boolean isPartitaVinta() {
        return stanzaCorrente == stanze.size() - 1
                && getStanzaAttuale().getNemici().stream()
                .allMatch(n -> n.getVitaCorrente() <= 0);
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public int getNumeroStanzaCorrente() {
        return stanzaCorrente + 1;
    }

    public int getTotaleStanze() {
        return stanze.size();
    }

    public StatoPartita esportaStato() {
        StatoPartita stato = new StatoPartita();
        stato.nomeGiocatore   = giocatore.getNome();
        stato.vitaCorrente    = giocatore.getVitaCorrente();
        stato.vitaMassima     = giocatore.getVitaMassima();
        stato.energiaCorrente = giocatore.getEnergiaCorrente();
        stato.energiaMassima  = giocatore.getEnergiaMassima();
        stato.armaNome        = giocatore.getArma().getClass().getSimpleName();
        stato.armaLivello     = giocatore.getArma().getLivello();
        stato.abilitaNome     = giocatore.getAbilita().getClass().getSimpleName();
        stato.stanzaCorrente  = stanzaCorrente;
        stato.inventario = giocatore.getInventario().getOggetti()
                .stream()
                .map(o -> o.getClass().getSimpleName())
                .toList();
        return stato;
    }

    public static GestorePartita ripristinaDaStato(StatoPartita stato) {
        Arma    arma    = ArmaFactory.crea(stato.armaNome, stato.armaLivello);
        Abilita abilita = AbilitaFactory.crea(stato.abilitaNome);

        Giocatore giocatore = new Giocatore(
                stato.nomeGiocatore,
                stato.vitaMassima,
                stato.energiaMassima,
                arma,
                abilita,
                new Inventario()
        );
        giocatore.setVitaCorrente(stato.vitaCorrente);
        giocatore.setEnergiaCorrente(stato.energiaCorrente);

        for (String classe : stato.inventario) {
            giocatore.getInventario().aggiungiOggetto(OggettoFactory.crea(classe));
        }

        GestorePartita partita = new GestorePartita(giocatore);
        for (Stanza s : StanzaFactory.creaCampagna()) {
            partita.aggiungiStanza(s);
        }
        partita.stanzaCorrente = stato.stanzaCorrente;
        return partita;
    }

}
