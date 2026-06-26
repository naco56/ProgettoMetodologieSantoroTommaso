package it.unicam.cs.mpgc.rpg129078.persistenza;

public interface SalvataggioService {

    void salva(StatoPartita stato);
    StatoPartita carica();
    boolean esisteSalvataggio();

}
