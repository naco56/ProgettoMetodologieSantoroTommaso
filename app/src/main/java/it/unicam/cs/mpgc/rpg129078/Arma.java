package it.unicam.cs.mpgc.rpg129078;

public interface Arma {

    int getDannoBase();

    int getLivello();

    void setLivello(int livello) {
        if (livello < 1) throw new IllegalArgumentException("Livello minimo: 1");
    }

    default int calcolaDanno() {
        return getDannoBase() + (getLivello() * 5);
    }

}
