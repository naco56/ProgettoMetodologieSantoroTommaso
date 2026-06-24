package it.unicam.cs.mpgc.rpg129078;

public class LavoroStraordinario implements Abilita{

    @Override
    public String nome() {
        return "Lavoro Straordinario";
    }

    @Override
    public int effetto() {
        return 20;
    }

    @Override
    public int costoEnergia() {
        return 40;
    }

}
