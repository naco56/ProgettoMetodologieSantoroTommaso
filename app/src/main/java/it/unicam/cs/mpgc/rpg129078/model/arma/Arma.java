package it.unicam.cs.mpgc.rpg129078.model.arma;

public interface Arma {

    int getDannoBase();

    int getLivello();

    default void setLivello(int livello) {
        if (livello < 1) throw new IllegalArgumentException("Livello minimo: 1");
    }

    default int calcolaDanno() {
        return getDannoBase() + (getLivello() * 5);
    }

}
