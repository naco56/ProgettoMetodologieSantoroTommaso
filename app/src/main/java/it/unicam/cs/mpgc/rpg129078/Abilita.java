package it.unicam.cs.mpgc.rpg129078;

public interface Abilita {

    String nome();

    int effetto();

    int costoEnergia();

    void usa(Giocatore giocatore, Nemico nemico);

}
