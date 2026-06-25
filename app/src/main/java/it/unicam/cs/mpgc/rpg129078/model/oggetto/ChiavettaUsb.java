package it.unicam.cs.mpgc.rpg129078.model.oggetto;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.arma.Arma;
import it.unicam.cs.mpgc.rpg129078.model.arma.Potenziabile;

public class ChiavettaUsb implements Oggetto {

    public String nome() {
        return "Chiavetta usb";
    }

    @Override
    public void usa(Giocatore giocatore) {

        Arma arma = giocatore.getArma();

        if (arma == null) {
            System.out.println("Nessuna arma equipaggiata!");
            return;
        }

        if (arma instanceof Potenziabile potenziabile) {
            potenziabile.setLivello(arma.getLivello() + 1);
            System.out.println("🔌 USB installata!");
            System.out.println("Arma potenziata a livello: " + arma.getLivello());
            System.out.println("Danno attuale: " + arma.calcolaDanno());
        } else {
            System.out.println("Questa arma non può essere potenziata!");
        }


    }
}
