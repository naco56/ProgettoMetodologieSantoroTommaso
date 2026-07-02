package it.unicam.cs.mpgc.rpg129078.model;

import it.unicam.cs.mpgc.rpg129078.model.oggetto.ManualeAziendale;
import it.unicam.cs.mpgc.rpg129078.model.oggetto.Snack;

import java.util.ArrayList;
import java.util.List;

public class StanzaFactory {

    public static List<Stanza> creaCampagna() {
        List<Stanza> stanze = new ArrayList<>();

        // stanza 1 — tutorial, nemico facile, Snack per energia
        Stanza s1 = new Stanza("Sala d'Attesa");
        s1.aggiungiNemico(new Nemico("Stagista", 30, 5, TipoNemico.NORMALE));
        s1.aggiungiOggetto(new Snack());
        stanze.add(s1);

        // stanza 2 — due nemici, il giocatore sale di livello dopo averla completata
        Stanza s2 = new Stanza("Open Space");
        s2.aggiungiNemico(new Nemico("Collega Invadente", 45, 8, TipoNemico.NORMALE));
        s2.aggiungiNemico(new Nemico("Collega Pettegolo", 35, 7, TipoNemico.NORMALE));
        s2.aggiungiOggetto(new ManualeAziendale()); // +5 HP max permanenti
        stanze.add(s2);

        // stanza 3 — miniboss, cura disponibile
        Stanza s3 = new Stanza("Sala Riunioni");
        s3.aggiungiNemico(new Nemico("Manager", 70, 12, TipoNemico.NORMALE));
        s3.aggiungiOggetto(new Snack());
        s3.aggiungiOggetto(new ManualeAziendale());
        stanze.add(s3);

        // stanza 4 — boss, bilanciato per lv 2 del giocatore
        Stanza boss = new Stanza("Ufficio del Direttore");
        boss.aggiungiNemico(new Nemico("Il Direttore", 120, 18, TipoNemico.BOSS));
        stanze.add(boss);

        return stanze;
    }

}
