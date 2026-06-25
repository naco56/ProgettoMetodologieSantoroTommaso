package it.unicam.cs.mpgc.rpg129078.model.arma;

public interface Arma {

    int getDannoBase();

    int getLivello();

    default int calcolaDanno() {
        return getDannoBase() + (getLivello() * 5);
    }

}
