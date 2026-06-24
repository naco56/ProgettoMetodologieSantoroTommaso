package it.unicam.cs.mpgc.rpg129078;
import java.util.ArrayList;
import java.util.List;

public class Stanza {



    private String nome;

    private List<Nemico> nemici;
    private List<Oggetto> oggetti;

    public Stanza(String nome) {
        this.nome = nome;
        this.nemici = new ArrayList<>();
        this.oggetti = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public List<Nemico> getNemici() {
        return nemici;
    }

    public List<Oggetto> getOggetti() {
        return oggetti;
    }

    public void aggiungiNemico(Nemico nemico) {
        nemici.add(nemico);
    }

    public void aggiungiOggetto(Oggetto oggetto) {
        oggetti.add(oggetto);
    }
}



