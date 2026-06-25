package it.unicam.cs.mpgc.rpg129078.model;

import java.util.ArrayList;
import java.util.List;

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


}
