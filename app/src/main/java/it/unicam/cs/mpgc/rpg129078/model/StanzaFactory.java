package it.unicam.cs.mpgc.rpg129078.model;

import it.unicam.cs.mpgc.rpg129078.model.oggetto.*;

import java.util.ArrayList;
import java.util.List;

public class StanzaFactory {

    public static List<Stanza> creaCampagna() {
        List<Stanza> stanze = new ArrayList<>();

        // stanza 1 — tutorial, nemico facile
        // drop: Badge Aziendale (prima upgrade arma) + Snack
        Stanza s1 = new Stanza("Sala d'Attesa");
        s1.aggiungiNemico(new Nemico("Stagista", 25, 5, TipoNemico.NORMALE));
        s1.aggiungiOggetto(new RaccogliBadgeAziendale());
        s1.aggiungiOggetto(new Snack());
        stanze.add(s1);

        // stanza 2 — mini-boss, nemico piu tosto
        // drop: Manuale Aziendale per +HP permanenti
        Stanza s2 = new Stanza("Open Space");
        s2.aggiungiNemico(new Nemico("Coordinatore", 70, 12, TipoNemico.NORMALE));
        s2.aggiungiOggetto(new ManualeAziendale());
        stanze.add(s2);

        // stanza 3 — due nemici medi
        // drop: Laptop Aziendale (arma migliore) + Snack
        Stanza s3 = new Stanza("Sala Riunioni");
        s3.aggiungiNemico(new Nemico("Manager", 50, 10, TipoNemico.NORMALE));
        s3.aggiungiNemico(new Nemico("Assistente", 30, 6, TipoNemico.NORMALE));
        s3.aggiungiOggetto(new RaccogliLaptopAziendale());
        s3.aggiungiOggetto(new Snack());
        stanze.add(s3);

        // stanza 4 — due nemici difficili
        // drop: Manuale Aziendale
        Stanza s4 = new Stanza("Corridoio");
        s4.aggiungiNemico(new Nemico("Guardia Aziendale", 45, 9, TipoNemico.NORMALE));
        s4.aggiungiNemico(new Nemico("Collega Arrabbiato", 40, 7, TipoNemico.NORMALE));
        s4.aggiungiOggetto(new ManualeAziendale());
        stanze.add(s4);

        // stanza 5 — nemico singolo forte
        // drop: unica ChiavettaUsb del gioco per potenziare l'arma
        Stanza s5 = new Stanza("Sala Server");
        s5.aggiungiNemico(new Nemico("Tecnico IT", 65, 13, TipoNemico.NORMALE));
        s5.aggiungiOggetto(new ChiavettaUsb());
        stanze.add(s5);

        // stanza 6 — boss finale
        Stanza boss = new Stanza("Ufficio del Direttore");
        boss.aggiungiNemico(new Nemico("Il Direttore", 120, 18, TipoNemico.BOSS));
        stanze.add(boss);

        return stanze;
    }

}
