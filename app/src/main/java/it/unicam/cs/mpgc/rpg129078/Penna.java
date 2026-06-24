package it.unicam.cs.mpgc.rpg129078;

public class Penna implements Arma{


    private int livello = 1;

    private final int dannoBase = 5;

    @Override
    public int getDannoBase() {
        return dannoBase;
    }

    @Override
    public int getLivello() {
        return livello;
    }

    @Override
    public void setLivello(int livello) {
        this.livello = livello;
    }

}
