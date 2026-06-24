package it.unicam.cs.mpgc.rpg129078.model.oggetto;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;

public class Snack implements Oggetto {

    public String nome() {
        return "Snack";
    }

    public void usa(Giocatore giocatore) {

        int recupero = 25;

        giocatore.setEnergiaCorrente(
                Math.min(
                        giocatore.getEnergiaMassima(),
                        giocatore.getEnergiaCorrente() + recupero
                )
        );
    }

}
