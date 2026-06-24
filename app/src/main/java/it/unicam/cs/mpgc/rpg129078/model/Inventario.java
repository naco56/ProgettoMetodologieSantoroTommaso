package it.unicam.cs.mpgc.rpg129078.model;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Oggetto;

import java.util.ArrayList;
import java.util.List;

public class Inventario {


    private List<Oggetto> oggetti = new ArrayList<>();

    public void aggiungiOggetto(Oggetto oggetto) {
        if (isPieno()){
            System.out.println("Inventario pieno!");
            return;
        }

        oggetti.add(oggetto);
        System.out.println("Aggiunto all'inventario: " + oggetto.nome());
    }

    public void usaOggetto(int indice, Giocatore giocatore) {


        if (indice < 0 || indice >= oggetti.size()) {
            System.out.println("Indice non valido!");
            return;
        }

        Oggetto oggetto = oggetti.get(indice);

        oggetto.usa(giocatore);

        oggetti.remove(indice);
    }


    public void rimuoviOggetto(int indice) {

        if (indice < 0 || indice >= oggetti.size()) {
            System.out.println("Indice non valido!");
            return;
        }

        Oggetto removed = oggetti.remove(indice);

        System.out.println("Rimosso: " + removed.nome());
    }

    public void mostraInventario() {

        System.out.println("\nINVENTARIO" );

        if (oggetti.isEmpty()) {
            System.out.println("Inventario vuoto");
            return;
        }

        for (int i = 0; i < oggetti.size(); i++) {
            System.out.println(i + " - " + oggetti.get(i).nome());
        }
    }


    public boolean isPieno() {
        return oggetti.size() >= 6;
    }

}
