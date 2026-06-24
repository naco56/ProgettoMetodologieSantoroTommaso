package it.unicam.cs.mpgc.rpg129078.model;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Oggetto;

import java.util.ArrayList;
import java.util.List;

public class Inventario {


    private List<Oggetto> oggetti = new ArrayList<>();

    public void aggiungiOggetto(Oggetto oggetto) {
        oggetti.add(oggetto);
        System.out.println("Aggiunto all'inventario: " + oggetto);
    }

    public void usaOggetto(int indice, Giocatore giocatore) {
        if (indice >= 0 && indice < oggetti.size()) {
            oggetti.get(indice).usa(giocatore);
            oggetti.remove(indice);
        }
    }


}
