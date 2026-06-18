package it.unicam.cs.mpgc.rpg129078;
import java.util.ArrayList;
import java.util.List;

public class Inventario {


    private List<Object> oggetti;

    public Inventario() {
        this.oggetti = new ArrayList<>();
    }

    public void aggiungiOggetto(Object oggetto) {
        oggetti.add(oggetto);
    }

    public void rimuoviOggetto(Object oggetto) {
        oggetti.remove(oggetto);
    }

    public List<Object> getOggetti() {
        return oggetti;
    }


}
