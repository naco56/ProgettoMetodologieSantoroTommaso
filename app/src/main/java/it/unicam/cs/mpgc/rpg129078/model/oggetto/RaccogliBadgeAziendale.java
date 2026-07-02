package it.unicam.cs.mpgc.rpg129078.model.oggetto;

import it.unicam.cs.mpgc.rpg129078.model.Giocatore;
import it.unicam.cs.mpgc.rpg129078.model.arma.BadgeAziendale;


public class RaccogliBadgeAziendale implements Oggetto {

    @Override
    public String nome() { return "Badge Aziendale (arma)"; }

    @Override
    public void usa(Giocatore giocatore) {
        giocatore.setArma(new BadgeAziendale());
    }
}