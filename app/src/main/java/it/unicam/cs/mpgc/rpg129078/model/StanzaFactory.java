package it.unicam.cs.mpgc.rpg129078.model;

import it.unicam.cs.mpgc.rpg129078.model.oggetto.Snack;

import java.util.ArrayList;
import java.util.List;

public class StanzaFactory {

    public static List<Stanza> creaCampagna() {
        List<Stanza> stanze = new ArrayList<>();

        Stanza s1 = new Stanza("Sala d'Attesa");
        s1.aggiungiNemico(new Nemico("Stagista", 30, 5, TipoNemico.NORMALE));
        s1.aggiungiOggetto(new Snack());
        stanze.add(s1);

        Stanza s2 = new Stanza("Open Space");
        s2.aggiungiNemico(new Nemico("Collega Invadente", 45, 8, TipoNemico.NORMALE));
        s2.aggiungiNemico(new Nemico("Collega Pettegolo", 35, 6, TipoNemico.NORMALE));
        stanze.add(s2);

        Stanza s3 = new Stanza("Sala Riunioni");
        s3.aggiungiNemico(new Nemico("Manager", 70, 12, TipoNemico.NORMALE));
        s3.aggiungiOggetto(new Snack());
        stanze.add(s3);

        Stanza boss = new Stanza("Ufficio del Direttore");
        boss.aggiungiNemico(new Nemico("Il Direttore", 150, 25, TipoNemico.BOSS));
        stanze.add(boss);

        return stanze;
    }

}
