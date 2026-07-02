package it.unicam.cs.mpgc.rpg129078.model.oggetto;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.arma.LaptopAziendale;


public class RaccogliLaptopAziendale implements Oggetto {

    @Override
    public String nome() { return "Laptop Aziendale (arma)"; }

    @Override
    public void usa(Giocatore giocatore) {
        giocatore.setArma(new LaptopAziendale());
    }
}