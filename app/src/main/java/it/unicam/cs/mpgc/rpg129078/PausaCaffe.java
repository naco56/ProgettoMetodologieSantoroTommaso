package it.unicam.cs.mpgc.rpg129078;

public class PausaCaffe implements Abilita{

    @Override
    public String nome() {
        return "Pausa Caffè";
    }

    @Override
    public int effetto() {
        return 15;
    }

    @Override
    public int costoEnergia() {
        return 25;
    }

}
