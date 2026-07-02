package it.unicam.cs.mpgc.rpg129078.model.abilita;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.Nemico;

public class LavoroStraordinario implements Abilita {

    @Override
    public String nome() {
        return "Lavoro Straordinario";
    }

    @Override
    public int effetto() {
        return 30;
    }

    @Override
    public int costoEnergia() {
        return 30;
    }


    public void usa(Giocatore giocatore, Nemico nemico) {

        nemico.setVitaCorrente(
                nemico.getVitaCorrente() - effetto()
        );

    }

}
