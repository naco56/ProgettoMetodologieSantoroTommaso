package it.unicam.cs.mpgc.rpg129078;

public interface Arma {

    int getDannoBase();

    int getLivello();

    void setLivello(int livello);

    default int calcolaDanno() {
        return getDannoBase() + (getLivello() * 5);
    }

}
