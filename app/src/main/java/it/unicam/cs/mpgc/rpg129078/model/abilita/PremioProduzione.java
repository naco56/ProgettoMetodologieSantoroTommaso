package it.unicam.cs.mpgc.rpg129078.model.abilita;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.Nemico;

public class PremioProduzione implements Abilita {

    @Override
    public String nome() {
        return "Premio di Produzione";
    }

    @Override
    public int effetto() {
        return 15;
    }

    @Override
    public int costoEnergia() {
        return 25;
    }

    public void usa(Giocatore giocatore, Nemico nemico) {

        int danno = effetto();

        nemico.setVitaCorrente(
                nemico.getVitaCorrente() - danno
        );

        giocatore.setVitaCorrente(
                giocatore.getVitaCorrente() + (danno / 2)
        );

    }


}