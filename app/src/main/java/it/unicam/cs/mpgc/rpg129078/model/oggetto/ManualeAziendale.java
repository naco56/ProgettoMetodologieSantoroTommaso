package it.unicam.cs.mpgc.rpg129078.model.oggetto;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;

public class ManualeAziendale implements Oggetto {

    public String nome() {
        return "Manuale Aziendale";
    }

    public void usa(Giocatore giocatore) {

        int bonus = 5;

        giocatore.setVitaMassima(
                giocatore.getVitaMassima() + bonus
        );

        giocatore.setVitaCorrente(
                giocatore.getVitaCorrente() + bonus
        );
    }
}