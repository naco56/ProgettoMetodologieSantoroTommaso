package it.unicam.cs.mpgc.rpg129078;

public class ChiavettaUsb implements Oggetto{

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

        arma.setLivello(arma.getLivello() + 1);

        System.out.println("🔌 USB installata!");
        System.out.println("Arma potenziata a livello: " + arma.getLivello());
        System.out.println("Danno attuale: " + arma.calcolaDanno());


    }
}
