package it.unicam.cs.mpgc.rpg129078.model.abilita;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.Nemico;

public class PausaCaffe implements Abilita {

    @Override
    public String nome() {
        return "Pausa Caffè";
    }

    @Override
    public int effetto() {
        return 25;
    }

    @Override
    public int costoEnergia() {
        return 20;
    }


    public void usa(Giocatore giocatore, Nemico nemico) {

        giocatore.setVitaCorrente(
                giocatore.getVitaCorrente() + effetto()
        );

    }


}
