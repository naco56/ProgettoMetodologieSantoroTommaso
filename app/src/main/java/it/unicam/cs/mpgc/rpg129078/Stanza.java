package it.unicam.cs.mpgc.rpg129078;
import java.util.ArrayList;
import java.util.List;

public class Stanza {



        private String nome;

        private List<Object> nemici;

        private List<Object> oggetti;


        public Stanza(String nome) {
            this.nome = nome;
            this.nemici = new ArrayList<>();
            this.oggetti = new ArrayList<>();

        }

        public String getNome() {
            return nome;
        }

        public List<Object> getNemici() {
            return nemici;
        }

        public List<Object> getOggetti() {
            return oggetti;
        }
        

        public void aggiungiNemico(Object nemico) {
            nemici.add(nemico);
        }

        public void aggiungiOggetto(Object oggetto) {
            oggetti.add(oggetto);
        }
}



