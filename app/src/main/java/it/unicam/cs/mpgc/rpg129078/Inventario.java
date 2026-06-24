package it.unicam.cs.mpgc.rpg129078;
import java.util.ArrayList;
import java.util.List;

public class Inventario {


    private List<String> oggetti = new ArrayList<>();

    public void aggiungiOggetto(String oggetto) {
        oggetti.add(oggetto);
        System.out.println("Aggiunto all'inventario: " + oggetto);
    }

    public void mostraInventario() {
        System.out.println("INVENTARIO ");
        if (oggetti.isEmpty()) {
            System.out.println("Vuoto");
        } else {
            for (String o : oggetti) {
                System.out.println("- " + o);
            }
        }
    }


}
