package it.unicam.cs.mpgc.rpg129078.model.abilita;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.Nemico;

public interface Abilita {

    String nome();

    int effetto();

    int costoEnergia();

    void usa(Giocatore giocatore, Nemico nemico);

}
